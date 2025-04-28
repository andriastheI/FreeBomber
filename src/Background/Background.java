package Background;

import Characters.*;
import FreeBomber.FreeBomber;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The Background class represents the game panel where the game logic and rendering occur.
 * It handles game updates, and switching between maps.
 * <p>
 * Algorithm idea adapted from:
 * https://www.youtube.com/@RyiSnow
 * </p>
 * @see <a href = "https://www.youtube.com/@RyiSnow">RyiSnow Youtube Channel</a>
 */
public class Background extends JPanel implements Runnable {

    /** Target frames per second for the game loop. */
    private static final int FPS = 60;

    /** Size of a single tile in pixels. */
    private final int tileSize = 46;

    /** Number of horizontal tiles on the screen. */
    private final int screenCols = 18;

    /** Number of vertical tiles on the screen. */
    private final int screenRows = 14;

    /** Total screen width in pixels, calculated from tile size and columns. */
    private final int screenWidth = screenCols * tileSize;

    /** Total screen height in pixels, calculated from tile size and rows. */
    private final int screenHeight = screenRows * tileSize;

    /** Handles collision detection for walls and objects. */
    private final CheckCollision checkCollision = new CheckCollision(this);

    /** Handles collision detection specifically for enemies. */
    private final EnemyCollision eslugCollision = new EnemyCollision(this);

    /** Captures and processes keyboard input. */
    private final KeyHandler keyHandler = new KeyHandler();

    /** Flag indicating whether the game is over. */
    public boolean gameOver = false;

    /** Manages and renders tile maps. */
    private TileManager tileManager;

    /** The main player character (JackBomber). */
    private JackBomber player;

    /** An enemy of type EnemyRock with random movement. */
    private EnemyRock enemy2;

    /** An enemy of type EnemyMush with different behavior. */
    private EnemyMush enemy3;

    /** An enemy of type EnemySlug that actively chases the player. */
    private EnemySlug enemy1;

    /** A more powerful enemy called EnemyDragon. */
    private EnemyDragon enemy4;

    /** Thread used to run the main game loop. */
    private Thread gameThread;

    /** a Boolean that is used to stop the game and display victory */
    private boolean finished = false;

    /** Image used to display player's health (hearts). */
    private BufferedImage heartImage;

    /** Reference to the main frame that contains this panel. */
    private FreeBomber frame;


    /**
     * Constructs the Background panel, initializing its size, tile map, key listeners, and optionally a starting map.
     *
     * @param frame       The reference to the main FreeBomber game frame.
     * @param startingMap The tile map number to load at game start.
     */
    public Background(FreeBomber frame, int startingMap) {
        this.frame = frame;
        this.tileManager = new TileManager(this, startingMap);
        initializeCharacters(0);
        int playerCol = player.getX() / tileSize;
        int playerRow = player.getY() / tileSize;
        tileManager.moveDoorToClosestSoftWall(playerCol, playerRow, startingMap == 5);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
        setLayout(null);
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(620, 650, 80, 30); // x, y, width, height
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to exit?",
                    "Exit Game",
                    JOptionPane.YES_NO_OPTION
            );
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        add(exitButton);
        try {
            heartImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/heart.png"));
        } catch (IOException e) {
            System.out.println("There is a problem when loading the heart image");
        }
    }

    /**
     * Constructs the Background panel without any frame, initializing its size and other properties.
     */
    public Background() {
        this.tileManager = new TileManager(this, 1); // Default to map 1
        initializeCharacters(0);
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
        setLayout(null);
        try {
            heartImage = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/heart.png"));
        } catch (IOException e) {
            System.out.println("There is a problem when loading the heart image");
        }
    }

    /**
     * Returns the enemy character of type EnemyRock.
     *
     * @return the enemy2 (EnemyRock).
     */
    public EnemyRock getEnemy2() {
        return enemy2;
    }

    /**
     * Returns the enemy character of type EnemyMush.
     *
     * @return the enemy3 (EnemyMush).
     */
    public EnemyMush getEnemy3() {
        return enemy3;
    }

    /**
     * Returns the enemy character of type EnemySlug.
     *
     * @return the enemy1 (EnemySlug).
     */
    public EnemySlug getEnemy1() {
        return enemy1;
    }

    /**
     * Returns the enemy character of type EnemySlug2.
     *
     * @return the enemy4 (EnemySlug2).
     */
    public EnemyDragon getEnemy4() {
        return enemy4;
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
                System.out.println("There is a problem when running game, using threads");
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
     * Returns the tile size for the game.
     *
     * @return the tile size in pixels.
     */
    public int getTileSize() {
        return tileSize;
    }

    /**
     * Returns the tile manager that handles the map and tile rendering.
     *
     * @return the tile manager.
     */
    public TileManager getTileManager() {
        return tileManager;
    }

    /**
     * Returns the screen width for the game.
     *
     * @return the screen width in pixels.
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Returns the screen height for the game.
     *
     * @return the screen height in pixels.
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Returns the number of rows on the game screen.
     *
     * @return the number of screen rows.
     */
    public int getScreenRows() {
        return screenRows;
    }

    /**
     * Returns the number of columns on the game screen.
     *
     * @return the number of screen columns.
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

        if (player.isLevelUp() && tileManager.getCurrentMap() <= 5) {
            int oldScore = player.getScore();
            removeCharacters();

            if (tileManager.getCurrentMap() == 5) {
                // Game is finished after map 5
                finished = true;
                frame.setPlayerScore(player.getScore());
                finishedGame();
                return; // Prevent drawing/logic errors from loading a nonexistent map
            } else {
                tileManager.loadMap(tileManager.getCurrentMap() + 1);
                initializeCharacters(oldScore);
                player.setLevelUp(false);
            }
        }
        tileManager.draw(g2);
        keepDrawing(g2);

        int heartTileSize = tileSize;
        int heartStartX = screenWidth - (player.getPlayerHealth() * heartTileSize) - 10;
        int heartY = 1;

        for (int i = 0; i < player.getPlayerHealth(); i++) {
            int x = heartStartX + i * heartTileSize;
            g2.drawImage(heartImage, x, heartY, heartTileSize, heartTileSize, null);
        }

        long gameTime = player.getRemainingTime() / 1000;
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        g2.setColor(Color.BLACK);
        g2.drawString("Timer: " + gameTime, heartStartX - 119, heartY + 31);
        g2.setColor(Color.WHITE);
        g2.drawString("Timer: " + gameTime, heartStartX - 120, heartY + 30);

        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        g.setColor(Color.WHITE);
        String scoreText = "Score: " + player.getScore();
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(scoreText);
        int x = (getWidth() - textWidth) / 2;
        int y = 30;

        g.drawString(scoreText, x, y);

        // Draw player name in top-left corner
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        g2.setColor(Color.BLACK);
        g2.drawString("Player: " + frame.getPlayerName(), 100, 31); // Black shadow
        g2.setColor(Color.WHITE);
        g2.drawString("Player: " + frame.getPlayerName(), 99, 30); // White text

        if (gameOver) {
            frame.setPlayerScore(player.getScore());
            endGame();
        }

        g2.dispose();
    }

    //TODO: add actual methods before getters and setters

    /**
     * Returns the player character for the game.
     *
     * @return the player character.
     */
    public JackBomber getPlayer() {
        return player;
    }

    /**
     * Returns the check collision object for detecting collisions in the game.
     *
     * @return the check collision object.
     */
    public CheckCollision getCheckCollision() {
        return checkCollision;
    }

    /**
     * Returns the enemy collision object for detecting enemy collisions.
     *
     * @return the enemy collision object.
     */
    public EnemyCollision getEslugCollision() {
        return eslugCollision;
    }

    /**
     * Renders all active (alive) characters onto the screen.
     * This includes the player and all enemy types.
     *
     * @param g2 the Graphics2D context used for drawing the characters
     */
    private void keepDrawing(Graphics2D g2) {
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

    /**
     * Temporarily disables all characters by setting their alive status to false.
     * This is typically used when transitioning between levels or restarting the game state.
     */
    private void removeCharacters() {
        player.setAlive(false);
        enemy1.setAlive(false);
        enemy2.setAlive(false);
        enemy3.setAlive(false);
        enemy4.setAlive(false);
    }

    /**
     * Initializes or reinitializes the player and all enemy characters.
     * This method is used during level loading or game restarts.
     */
    private void initializeCharacters(int gameScore) {
        player = new JackBomber(this, keyHandler, new Bomb(this));
        player.setScore(gameScore);
        enemy1 = new EnemySlug(this, player);
        enemy2 = new EnemyRock(this, player);
        enemy3 = new EnemyMush(this, player);
        enemy4 = new EnemyDragon(this, player);
    }

    /**
     * Ends the game and switches to the Game Over screen.
     * <p>
     * This method stops the game loop by nullifying the game thread and
     * then asynchronously invokes the Game Over screen using the Swing event dispatch thread.
     */
    private void endGame() {
        gameThread = null;
        SwingUtilities.invokeLater(() -> frame.showGameOver());
    }

    /**
     * Ends the game and switches to the Victory Screen.
     * <p>
     * This method stops the game loop by nullifying the game thread and
     * then asynchronously invokes the victory screen using the Swing event dispatch thread.
     */
    private void finishedGame() {
        gameThread = null;
        SwingUtilities.invokeLater(() -> frame.showVictory());
    }

}

