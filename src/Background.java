package src;

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
    public ESlugCollision eslugCollision = new ESlugCollision(this);
    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    JackBomber player = new JackBomber(this, keyHandler);
    E_Slug enemy1 = new E_Slug(this);
    E_Rock enemy2 = new E_Rock(this);
    E_Mush enemy3 = new E_Mush(this);


    public Background() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
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
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        tileManager.draw(g2);
        player.draw(g2);
        enemy1.draw(g2);
        enemy2.draw(g2);
        enemy3.draw(g2);

        g2.dispose();
    }
}
