package Background;

import Characters.Character;
import Characters.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The Background class represents the game panel where the game logic and rendering occur.
 * It handles game updates, rendering, and switching between maps.
 */
public class Background extends JPanel implements Runnable {

    // Frames per second for the game loop
    private static final int FPS = 60;

    // Tile and screen dimensions
    private final int tileSize = 46;
    private final int screenCols = 18;
    private final int screenRows = 14;
    private final int screenWidth = screenCols * tileSize;
    private final int screenHeight = screenRows * tileSize;


    // Game management objects
    private final CheckCollision checkCollision = new CheckCollision(this);
    private final EnemyCollision eslugCollision = new EnemyCollision(this);
    private final TileManager tileManager = new TileManager(this);
    private final KeyHandler keyHandler = new KeyHandler();
    private final ArrayList<Character> characters = new ArrayList<>();


    // Player and enemies
    private final JackBomber player = new JackBomber(this, keyHandler, new Bomb(this));
    private final EnemyRock enemy2 = new EnemyRock(this, this.player);
    private final EnemyMush enemy3 = new EnemyMush(this, this.player);
    private final EnemySlug enemy1 = new EnemySlug(this, this.player);
    private final EnemySlug2 enemy4 = new EnemySlug2(this, this.player);
    // Indicates whether the game is over
    public boolean gameOver = false;
    private Thread gameThread;
    /**
     * Constructs the Background panel, initializing its size, background color, and key listeners.
     */
    public Background() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
        setLayout(null);
        characters.add(player);
        characters.add(enemy1);
        characters.add(enemy2);
        characters.add(enemy4);
    }

    public EnemyRock getEnemy2() {
        return enemy2;
    }

    public EnemyMush getEnemy3() {
        return enemy3;
    }

    public EnemySlug getEnemy1() {
        return enemy1;
    }

    public EnemySlug2 getEnemy4() {
        return enemy4;
    }

    /**
     * Switches the game map to the next in sequence.
     */
    private void toggleMap() {
        tileManager.loadMap((tileManager.currentMap % 5) + 1);
        repaint();
    }

    /**
     * Starts the game thread, which continuously updates and repaints the game screen.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Runs the game loop, updating and rendering the game at a fixed frame rate.
     */
    @Override
    public void run() {
        double drawInterval = 1_000_000_000.0 / FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            repaint();
            try {
                double remainingTime = (nextDrawTime - System.nanoTime()) / 1_000_000;
                Thread.sleep(Math.max(0, (long) remainingTime));
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Updates the game state, including the player and enemy characters.
     */
    public void update() {
        if (player.isAlive()) {
            player.update();
        }
        if (enemy1.isAlive()) {
            enemy1.update();
        }
        if (enemy2.isAlive()) {
            enemy2.update();
        }
        if (enemy3.isAlive()) {
            enemy3.update();
        }
        if (enemy4.isAlive()) {
            enemy4.update();
        }
    }

    /**
     * Gets the tile size.
     *
     * @return tile size in pixels.
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Gets the tile manager.
     *
     * @return tile manager of the background.
     */
    public TileManager getTileManager() {
        return tileManager;
    }

    /**
     * Gets the screen width.
     *
     * @return screen width in pixels.
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Gets the screen height.
     *
     * @return screen height in pixels.
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Gets the number of screen rows.
     *
     * @return screen row count.
     */
    public int getScreenRows() {
        return screenRows;
    }

    /**
     * Gets the number of screen columns.
     *
     * @return screen column count.
     */
    public int getScreenCols() {
        return screenCols;
    }

    /**
     * Paints the game components onto the screen, including the player, enemies, and tiles.
     *
     * @param g The graphics context used for drawing.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        if (player.isAlive()) {
            player.draw(g2);
        }
        if (enemy1.isAlive()) {
            enemy1.draw(g2);
        }
        if (enemy2.isAlive()) {
            enemy2.draw(g2);
        }
        if (enemy3.isAlive()) {
            enemy3.draw(g2);
        }
        if (enemy4.isAlive()) {
            enemy4.draw(g2);
        }

        if (gameOver) {
            String message = "GAME OVER";
            g2.setFont(new Font("Arial", Font.BOLD, 60));
            g2.setColor(Color.RED);
            g2.drawString(message, screenWidth / 4, screenHeight / 2);
        }
        g2.dispose();
    }


    public JackBomber getPlayer() {
        return player;
    }

    public CheckCollision getCheckCollision() {
        return checkCollision;
    }

    public EnemyCollision getEslugCollision() {
        return eslugCollision;
    }
}
