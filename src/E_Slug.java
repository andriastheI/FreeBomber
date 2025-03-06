package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class E_Slug extends Character{
    Background background;
    KeyHandler keyHandler;

    public E_Slug(Background bg, KeyHandler kh) {
        this.background = bg;
        this.keyHandler = kh;
        setDefaultValues();
        getPlayerImage();

        //this rectangle is used as a collision detector that is smaller than the champion player
        //so that it is flexible for going through tight spaces.
        spriteBounds = new Rectangle(6, 18, 28, 25);
    }

    public void setDefaultValues() {
        x = background.screenWidth - 2*background.tileSize;
        y = background.screenHeight - 2*background.tileSize;
        speed = 2;
        direction = "down";
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

    public void update() {

        if (keyHandler.upDirection || keyHandler.downDirection ||
                keyHandler.leftDirection || keyHandler.rightDirection) {
            if (keyHandler.upDirection) {
                direction = "up";
            } else if (keyHandler.downDirection) {
                direction = "down";
            } else if (keyHandler.leftDirection) {
                direction = "left";
            } else {
                direction = "right";
            }

            collsionOn = false;
            background.checkCollision.checkCollision(this);

            if (!collsionOn) {
                // Prevent player from going out of bounds
                if (direction.equals("up")) {
                    if (y - speed >= 0) { // Prevent moving above the top of the screen
                        y -= speed;
                    }
                }
                if (direction.equals("down")) {
                    if (y + speed < background.screenHeight - background.tileSize) { // Prevent moving below the bottom of the screen
                        y += speed;
                    }
                }
                if (direction.equals("left")) {
                    if (x - speed >= 0) { // Prevent moving left off the screen
                        x -= speed;
                    }
                }
                if (direction.equals("right")) {
                    if (x + speed < background.screenWidth - background.tileSize) { // Prevent moving right off the screen
                        x += speed;
                    }
                }
            }


// Sprite animation logic
            spriteCounter++;
            if (spriteCounter > 8) {
                if (direction.equals("up") || direction.equals("down")) {
                    // Toggle between 1 and 2 for up/down
                    spriteNum = (spriteNum == 1) ? 2 : 1;
                } else {
                    // Cycle through 1 → 2 → 3 → 1 for left/right
                    spriteNum++;
                    if (spriteNum > 3) {
                        spriteNum = 1;
                    }
                }
                spriteCounter = 0;
            }

        }


    }

    public void draw(Graphics g) {
        BufferedImage img = null;
        switch (direction) {
            case "up":
                if (spriteNum == 1) {
                    img = up1;
                }
                if (spriteNum == 2) {
                    img = up2;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    img = down1;
                }
                if (spriteNum == 2) {
                    img = down2;
                }
                break;
            case "left":
                if (spriteNum == 1) {
                    img = left1;
                }
                if (spriteNum == 2) {
                    img = left2;
                }
                if (spriteNum == 3) {
                    img = left3;
                }
                break;
            case "right":
                if (spriteNum == 1) {
                    img = right1;
                }
                if (spriteNum == 2) {
                    img = right2;
                }
                if (spriteNum == 3) {
                    img = right3;
                }
                break;
        }
        g.drawImage(img, x, y, background.tileSize, background.tileSize, null);
    }
}
