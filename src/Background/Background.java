package Background;

import Characters.*;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel implements Runnable {

    final static int FPS = 60;
    public final int tileSize = 46;
    public final int screenCols = 18;
    public final int screenRows = 14;

    public final int screenWidth = screenCols * tileSize;
    public final int screenHeight = screenRows * tileSize;
    public CheckCollision checkCollision = new CheckCollision(this);
    public EnemyCollision eslugCollision = new EnemyCollision(this);
    public TileManager tileManager = new TileManager(this);
    // Add the gameOver flag here
    public boolean gameOver = false;
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    JackBomber player = new JackBomber(this, keyHandler);
    EnemyRock enemy2 = new EnemyRock(this, this.player);
    EnemyMush enemy3 = new EnemyMush(this, this.player);
    EnemySlug enemy1 = new EnemySlug(this, this.player);
    EnemySlug2 enemy4 = new EnemySlug2(this, this.player);

    //private JButton toggleButton;

    public Background() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);

        // Set layout to null to manually place the button
        setLayout(null);

        // Create a toggle map button
        //toggleButton = new JButton("Toggle Map");
        //toggleButton.setBounds(10, 10, 120, 40);  // Position the button at the top-left corner
        //toggleButton.addActionListener(e -> toggleMap());  // Attach the action to the button
        //add(toggleButton);
        //toggleButton.setFocusable(false);
    }

    // Method to switch maps
    private void toggleMap() {
        // Get the next map in the sequence
        if (tileManager.currentMap == 1) {
            tileManager.loadMap(2);  // Switch to map 2
        } else if (tileManager.currentMap == 2) {
            tileManager.loadMap(3);  // Switch to map 3
        } else if (tileManager.currentMap == 3) {
            tileManager.loadMap(4);  // Switch to map 4
        } else if (tileManager.currentMap == 4) {
            tileManager.loadMap(5);  // Switch to map 5
        } else {
            tileManager.loadMap(1);  // Switch back to map 1
        }
        repaint();  // Refresh the panel to show the new map
    }


    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = (double) 1000000000 / FPS;
        double nextDrawT = System.nanoTime() + drawInterval;

        while (gameThread != null) {

            update();

            repaint();
            try {
                double remainingTime = nextDrawT - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawT += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        player.update();
        enemy1.update();
        enemy2.update();
        enemy3.update();
        enemy4.update();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);
        enemy1.draw(g2);
        enemy2.draw(g2);
        enemy3.draw(g2);
        enemy4.draw(g2);

        // If the game is over, display the Game Over message
        if (gameOver) {
            String message = "GAME OVER";
            Font font = new Font("Arial", Font.BOLD, 60);
            g2.setFont(font);
            g2.setColor(Color.RED);
            g2.drawString(message, screenWidth / 4, screenHeight / 2);
        }
        g2.dispose();
    }
}
