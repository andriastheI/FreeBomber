package Characters;

import Background.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Description:
 * <p>
 * Represents a slug-type enemy in the game that moves in a fixed direction until blocked.
 * It changes direction upon collision and reacts to explosions by being removed and increasing the player’s score.
 * The class also handles sprite animation and image rendering during movement.
 * </p>
 *
 * @author mguzelocak
 * @author Zelele
 */
public class EnemySlug extends Character {
    /** Reference to the Background */
    private final Background background;
    /** Reference to the JackBomber character */
    private final JackBomber jackBomber;

    /**
     * Constructor for the EnemySlug class.
     * Initializes the background and JackBomber instances and sets default values.
     * Loads the player image and initializes collision bounds for the enemy.
     *
     * @param bg   The background object that holds the game environment.
     * @param jack The JackBomber character object used for collision detection.
     */
    public EnemySlug(Background bg, JackBomber jack) {
        this.background = bg;
        this.jackBomber = jack; // Initialize JackBomber reference
        setDefaultValues();
        getPlayerImage();

        // Set collision detection bounds for the enemy sprite
        spriteBounds = new Rectangle(6, 18, 28, 25);
        move();
    }

    /**
     * Sets the default values for the enemy slug's position, speed, and direction.
     */
    public void setDefaultValues() {
        setX(background.getScreenWidth() - 2 * background.getTileSize());
        setY(background.getScreenHeight() - 2 * background.getTileSize());
        speed = 1;
        direction = "left";
    }

    /**
     * Loads the images for the enemy slug's different directions and sprite frames.
     * The images are read from the resources and assigned to the corresponding direction variables.
     */
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
            System.out.println("Failed to load Enemy Slug Images");
        }
    }

    /**
     * Moves the enemy slug based on its direction and handles collision detection.
     * If a collision is detected, the enemy will change direction.
     */
    public void move() {
        // Pass the JackBomber instance to the collision check method
        background.getEslugCollision().checkCollision(this, jackBomber);

        // If a collision occurs, change direction
        if (isBlocked()) {
            changeDirection();
        } else {
            // Move normally if no collision
            if (direction.equals("up") && getY() - speed >= 0) {
                setY(getY() - speed);
            } else if (direction.equals("down") && getY() + speed < background.getScreenHeight() - background.getTileSize()) {
                setY(getY() + speed);
            } else if (direction.equals("left") && getX() - speed >= 0) {
                setX(getX() - speed);
            } else if (direction.equals("right") && getX() + speed < background.getScreenWidth() - background.getTileSize()) {
                setX(getX() + speed);
            }
        }
    }

    /**
     * Helper method to determine if the enemy is blocked based on its direction.
     *
     * @return true if the enemy is blocked, false otherwise.
     */
    private boolean isBlocked() {
        switch (direction) {
            case "up":
                return !background.getEslugCollision().collisionDirection[0];
            case "down":
                return !background.getEslugCollision().collisionDirection[1];
            case "left":
                return !background.getEslugCollision().collisionDirection[2];
            case "right":
                return !background.getEslugCollision().collisionDirection[3];
            default:
                return false;
        }
    }

    /**
     * Handles logic for when the enemy slug is affected by a bomb explosion.
     * If the enemy intersects the explosion area, it is marked as not alive and points are awarded.
     *
     * @param explosionArea the rectangular area of the bomb explosion
     */
    public void handleExplosion(Rectangle explosionArea) {
        Rectangle enemyRect = new Rectangle(getX(), getY(), background.getTileSize(), background.getTileSize());

        if (explosionArea.intersects(enemyRect)) {
            // Enemy is hit by the explosion, remove or mark as defeated
            this.setAlive(false);
            //increase the score when character is dead
            jackBomber.increaseScore(150);
        }
    }

    /**
     * Changes the direction of the enemy slug to avoid reversing its current direction.
     */
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

    /**
     * Updates the sprite animation and movement of the enemy slug.
     * The sprite will alternate every 8 updates for smooth animation.
     */
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

    /**
     * Draws the enemy slug's current sprite on the screen based on its direction.
     *
     * @param g The graphics object used for drawing the enemy's image.
     */
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
        g.drawImage(img, getX(), getY(), background.getTileSize(), background.getTileSize(), null);
    }

}
