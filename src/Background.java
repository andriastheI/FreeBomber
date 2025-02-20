package src;

import javax.swing.*;
import java.awt.*;

public class Background extends JPanel implements Runnable{

    final static int FPS = 60;
    public final int tileSize = 46;
    public final int screenCols = 18;
    public final int screenRows = 14;

    public final int screenWidth = screenCols * tileSize;
    public final int screenHeight = screenRows * tileSize;

    TileManager tileManager = new TileManager(this);
    KeyHandler keyHandler = new KeyHandler();
    Thread gameThread;
    // We need the players instance here

    int playerX = 100;
    int playerY = 100;
    int playerSp = 3;

    public Background() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        addKeyListener(keyHandler);
        setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }
    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double nextDrawT = System.nanoTime() + drawInterval;

        while(gameThread != null){

            update();

            repaint();
            try {
                double remainingTime = nextDrawT - System.nanoTime();
                remainingTime = remainingTime /1000000;

                if(remainingTime < 0){
                    remainingTime = 0;
                }
                Thread.sleep((long) remainingTime);
                nextDrawT += drawInterval;
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }
    public void update(){
        if(keyHandler.upDirection){
            playerY -= playerSp;
        } else if(keyHandler.downDirection){
            playerY += playerSp;
        } else if(keyHandler.leftDirection){
            playerX -= playerSp;
        } else if(keyHandler.rightDirection){
            playerX += playerSp;
        }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(Color.WHITE);
        tileManager.draw(g2);
        g2.fillRect(playerX, playerY, tileSize, tileSize);


        g2.dispose();
    }
}
