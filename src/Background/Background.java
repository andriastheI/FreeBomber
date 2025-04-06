package Background;


import Characters.Character;
import Characters.*;
import FreeBomber.FreeBomber;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
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


    // Player and enemies
    private JackBomber player = new JackBomber(this, keyHandler, new Bomb(this));
    private EnemyRock enemy2 = new EnemyRock(this, this.player);
    private EnemyMush enemy3 = new EnemyMush(this, this.player);
    private EnemySlug enemy1 = new EnemySlug(this, this.player);
    private EnemySlug2 enemy4 = new EnemySlug2(this, this.player);
    // Indicates whether the game is over
    public boolean gameOver = false;
    private Thread gameThread;
    private BufferedImage heartImage;
    private FreeBomber frame;

    /**
     * Constructs the Background panel, initializing its size, background color, and key listeners.
     */
    public Background(FreeBomber frame) {
        this.frame = frame;
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
        setLayout(null);
        try {
            heartImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Background() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
        setLayout(null);
        try {
            heartImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
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


        if(player.isLevelUp()){
            removeCharacters();
            tileManager.loadMap(tileManager.getCurrentMap()+1);
            initializeCharacters();
            player.setLevelUp(false);
        }
        tileManager.draw(g2);
        keepDrawing(g2);

        int heartTileSize = tileSize;
        int heartStartX = screenWidth - (player.getPlayerHealth() * heartTileSize) - 10;
        int heartY = 10;

        for (int i = 0; i < player.getPlayerHealth(); i++) {
            int x = heartStartX + i * heartTileSize;
            g2.drawImage(heartImage, x, heartY, heartTileSize, heartTileSize, null);
        }

        if (gameOver) {
            endGame();
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
    private void keepDrawing(Graphics2D g2){
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
    }
    private void removeCharacters() {
        player.setAlive(false);
        enemy1.setAlive(false);
        enemy2.setAlive(false);
        enemy3.setAlive(false);
        enemy4.setAlive(false);
    }

    /**
     * Re-initializes characters for the new level.
     */
    private void initializeCharacters() {
        player = new JackBomber(this, keyHandler, new Bomb(this));
        enemy1 = new EnemySlug(this, player);
        enemy2 = new EnemyRock(this, player);
        enemy3 = new EnemyMush(this, player);
        enemy4 = new EnemySlug2(this, player);
    }
    private void endGame() {
        gameThread = null; // stop the game loop
        SwingUtilities.invokeLater(() -> {
            frame.showGameOver(); // switch to GameOverPanel
        });
    }

}
