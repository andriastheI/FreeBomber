package Characters;

import Background.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class EnemySlug extends Character {
    Background background;
    JackBomber jackBomber; // Add Character.Character.JackBomber reference

    public EnemySlug(Background bg, JackBomber jack) {
        this.background = bg;
        this.jackBomber = jack; // Initialize Character.Character.JackBomber
        setDefaultValues();
        getPlayerImage();

        // Collision detection bounds
        spriteBounds = new Rectangle(6, 18, 28, 25);
        move();
    }

    public void setDefaultValues() {
        x = background.screenWidth - 2 * background.tileSize;
        y = background.screenHeight - 2 * background.tileSize;
        speed = 1;
        direction = "left";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy1_up1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy1_up2.png"));

            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy1_down1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy1_down2.png"));

            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy1_left1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy1_left2.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy1_left3.png"));

            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy1_right1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy1_right2.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy1_right3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void move() {
        // Pass the Character.Character.JackBomber instance to the collision check method
        background.eslugCollision.checkCollision(this, jackBomber);

        // If collision occurs, change direction
        if (isBlocked()) {
            changeDirection();
        } else {
            // Move normally if no collision
            if (direction.equals("up") && y - speed >= 0) {
                y -= speed;
            } else if (direction.equals("down") && y + speed < background.screenHeight - background.tileSize) {
                y += speed;
            } else if (direction.equals("left") && x - speed >= 0) {
                x -= speed;
            } else if (direction.equals("right") && x + speed < background.screenWidth - background.tileSize) {
                x += speed;
            }
        }
    }

    // Helper method to determine if the enemy is blocked
    private boolean isBlocked() {
        switch (direction) {
            case "up": return !background.eslugCollision.collisionDirection[0];
            case "down": return !background.eslugCollision.collisionDirection[1];
            case "left": return !background.eslugCollision.collisionDirection[2];
            case "right": return !background.eslugCollision.collisionDirection[3];
            default: return false;
        }
    }

    public void changeDirection() {
        switch (direction) {
            case "up":
                // If moving up, try moving right or down next (avoid reverse)
                direction = "left";
                break;
            case "down":
                // If moving down, try moving left or up next (avoid reverse)
                direction = "right";
                break;
            case "right":
                // If moving right, try moving down or up next (avoid reverse)
                direction = "up";
                break;
            case "left":
                // If moving left, try moving up or down next (avoid reverse)
                direction = "down";
                break;
        }
    }

    public void update() {
        move();
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
