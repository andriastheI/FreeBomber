package src;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    final static int FPS = 60;
    final int tileSize = 46;
    final int screenCols = 18;
    final int screenRows = 14;

    final int screenWidth = screenCols * tileSize;
    final int screenHeight = screenRows * tileSize;


    KeyHandler keyHandler = new KeyHandler();

    Thread gameThread;

    int playerX = 100;
    int playerY = 100;
    int playerSp = 3;

    public GamePanel() {
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
        g2.fillRect(playerX, playerY, tileSize, tileSize);

        g2.dispose();
    }
}
