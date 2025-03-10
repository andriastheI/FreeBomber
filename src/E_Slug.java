package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;
import java.util.Random;

public class E_Slug extends Character {
    Background background;
    Timer movementTimer;
    Random random;

    public E_Slug(Background bg) {
        this.background = bg;
        this.random = new Random();
        setDefaultValues();
        getPlayerImage();

        // Collision detection bounds
        spriteBounds = new Rectangle(6, 18, 28, 25);

        // Set up the timer to move in a random direction every 2 seconds (2000 milliseconds)
        movementTimer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                moveRandomly();
            }
        });
        movementTimer.start();
    }

    public void setDefaultValues() {
        x = background.screenWidth - 2 * background.tileSize;
        y = background.screenHeight - 2 * background.tileSize;
        speed = 10;
        direction = "left";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_up1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_up2.png"));

            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_down1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_down2.png"));

            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_left1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_left2.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_left3.png"));

            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_right1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_right2.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_right3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveRandomly() {
        String[] directions = {"up", "down", "left", "right"};
        direction = directions[random.nextInt(directions.length)];

        collisionOn = false;
        background.eslugCollision.checkCollision(this);

        if (!collisionOn) {
            switch (direction) {
                case "up":
                    if (y - speed >= 0) {
                        y -= speed;
                    }
                    break;
                case "down":
                    if (y + speed < background.screenHeight - background.tileSize) {
                        y += speed;
                    }
                    break;
                case "left":
                    if (x - speed >= 0) {
                        x -= speed;
                    }
                    break;
                case "right":
                    if (x + speed < background.screenWidth - background.tileSize) {
                        x += speed;
                    }
                    break;
            }
        }
    }

    public void update() {
        spriteCounter++;
        if (spriteCounter > 8) {
            if (direction.equals("up") || direction.equals("down")) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
            } else {
                spriteNum++;
                if (spriteNum > 3) {
                    spriteNum = 1;
                }
            }
            spriteCounter = 0;
        }
    }

    public void draw(Graphics g) {
        BufferedImage img = null;
        switch (direction) {
            case "up":
                img = (spriteNum == 1) ? up1 : up2;
                break;
            case "down":
                img = (spriteNum == 1) ? down1 : down2;
                break;
            case "left":
                img = (spriteNum == 1) ? left1 : (spriteNum == 2) ? left2 : left3;
                break;
            case "right":
                img = (spriteNum == 1) ? right1 : (spriteNum == 2) ? right2 : right3;
                break;
        }
        g.drawImage(img, x, y, background.tileSize, background.tileSize, null);
    }
}
