package Characters;

import Background.Background;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * Represents a mushroom-type enemy character in the game.
 * <p>
 * EnemyMush moves randomly, checks for collisions with the environment and player,
 * and reacts to bomb explosions. It uses a timer-based system to update direction periodically.
 * </p>
 */
public class EnemyMush extends Character {

    /** Reference to the game background for movement boundaries and collision. */
    Background background;
    /** Timer that triggers movement updates every 200ms. */
    Timer movementTimer;
    /** Random object to generate directions. */
    Random random;
    /** Reference to the JackBomber player, used for collision handling. */
    JackBomber jackBomber;

    /**
     * Constructs a new EnemyMush instance.
     *
     * @param bg   the game background
     * @param jack the player character
     */
    public EnemyMush(Background bg, JackBomber jack) {
        this.background = bg;
        this.random = new Random();
        this.jackBomber = jack;
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

    /**
     * Sets the default position, speed, and direction of the enemy.
     */
    public void setDefaultValues() {
        x = background.getScreenWidth() - 2 * background.getTileSize();
        y = background.getScreenHeight() - 2 * background.getTileSize();
        speed = 10;
        direction = "left";
    }

    /**
     * Loads sprite images for the enemy in each direction.
     */
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_up_1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_up_2.png"));
            up3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_up_3.png"));

            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_down_1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_down_2.png"));
            down3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_down_3.png"));

            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_left_1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_left_2.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_left_3.png"));

            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_right_1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_right_2.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy3_cropped_right_3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Moves the enemy in the current direction unless there's a collision.
     * If blocked, chooses a new random direction.
     */
    public void moveRandomly() {
        collisionOn = false;
        background.getEslugCollision().checkCollision(this, jackBomber);
        if (!collisionOn) {
            switch (direction) {
                case "up":
                    if (y - speed >= 0) {
                        y -= speed;
                    }
                    break;
                case "down":
                    if (y + speed < background.getScreenHeight() - background.getTileSize()) {
                        y += speed;
                    }
                    break;
                case "left":
                    if (x - speed >= 0) {
                        x -= speed;
                    }
                    break;
                case "right":
                    if (x + speed < background.getScreenWidth() - background.getTileSize()) {
                        x += speed;
                    }
                    break;
            }
        } else {
            // Change direction ONLY after collision
            String newDirection = direction;
            while (newDirection.equals(direction)) {
                String[] directions = {"up", "down", "left", "right"};
                newDirection = directions[random.nextInt(directions.length)];
            }
            direction = newDirection;
        }
    }

    /**
     * Updates the animation frame for sprite switching.
     */
    public void update() {
        spriteCounter++;
        if (spriteCounter > 8) {
            if (spriteNum == 1) {
                spriteNum = 2;
            } else if (spriteNum == 2) {
                spriteNum = 3;
            } else if (spriteNum == 3) {
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }

    /**
     * Renders the current sprite image based on direction and animation state.
     *
     * @param g the graphics context to draw on
     */
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
                if (spriteNum == 3) {
                    img = up3;
                }
                break;
            case "down":
                if (spriteNum == 1) {
                    img = down1;
                }
                if (spriteNum == 2) {
                    img = down2;
                }
                if (spriteNum == 3) {
                    img = down3;
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
        g.drawImage(img, x, y, background.getTileSize(), background.getTileSize(), null);
    }

    /**
     * If the enemy is within an explosion area, marks it as defeated.
     *
     * @param explosionArea the area affected by the explosion
     */
    public void handleExplosion(Rectangle explosionArea) {
        Rectangle enemyRect = new Rectangle(x, y, background.getTileSize(), background.getTileSize());

        if (explosionArea.intersects(enemyRect)) {
            // Enemy is hit by the explosion, remove or mark as defeated
            this.setAlive(false);
            //increase the score when character is dead
            //TODO: update if it is fixed
//            JackBomber.increaseScore(150);
            background.getPlayer().increaseScore(150);
        }
    }
}


