package Characters;

import Background.Background;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Represents a rock-type enemy character in the game.
 * <p>
 * EnemyRock moves in a random direction every few frames,
 * interacts with the environment and the player,
 * and can be defeated by explosions.
 * </p>
 */
public class EnemyRock extends Character {

    /** Reference to the game background for screen and tile information. */
    Background background;
    /** Timer that triggers movement every 200 milliseconds. */
    Timer movementTimer;
    /** Random object used for direction selection. */
    Random random;
    /** Reference to the JackBomber player, used for collision checks. */
    JackBomber jackBomber;


    /**
     * Constructs a new EnemyRock with the specified background and player reference.
     *
     * @param bg   the game background
     * @param jack the JackBomber player character
     */
    public EnemyRock(Background bg, JackBomber jack) {
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
                if (Math.abs(getplayerDistanceX()) < 100 && Math.abs(getplayerDistanceY()) < 100) {
                    moveSmart();
                } else {
                    moveRandomly();
                }

            }
        });
        movementTimer.start();
    }

    /**
     * Initializes default position, speed, and direction values for the enemy.
     */
    public void setDefaultValues() {
        x = background.getScreenWidth() - 2 * background.getTileSize();
        y = background.getScreenHeight() - 2 * background.getTileSize();
        speed = 10;
        direction = "left";
    }

    /**
     * Loads enemy sprite images for different directions from the resources.
     */
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_up_1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_up_2.png"));
            up3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_up_3.png"));

            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_down_1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_down_2.png"));
            down3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_down_3.png"));

            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_left_1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_left_2.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_left_3.png"));

            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_right_1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_right_2.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_cropped_right_3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void moveSmart() {
        collisionOn = false;

        // Determine direction toward player
        int dx = getplayerDistanceX();
        int dy = getplayerDistanceY();

        String[] directions = {"up", "down", "left", "right"};
        List<String> preferredDirections = new ArrayList<>();

        // Prioritize vertical/horizontal based on distance
        if (Math.abs(dx) > Math.abs(dy)) {
            if (dx > 0) preferredDirections.add("right");
            else preferredDirections.add("left");

            if (dy > 0) preferredDirections.add("down");
            else preferredDirections.add("up");
        } else {
            if (dy > 0) preferredDirections.add("down");
            else preferredDirections.add("up");

            if (dx > 0) preferredDirections.add("right");
            else preferredDirections.add("left");
        }

        // Add any directions not in preferred to complete list
        for (String dir : directions) {
            if (!preferredDirections.contains(dir)) {
                preferredDirections.add(dir);
            }
        }

        // Try preferred directions in order
        for (String dir : preferredDirections) {
            direction = dir;
            collisionOn = false;
            background.getEslugCollision().checkCollision(this, jackBomber);

            if (!collisionOn) {
                switch (dir) {
                    case "up":
                        if (y - speed >= 0) y -= speed;
                        break;
                    case "down":
                        if (y + speed < background.getScreenHeight() - background.getTileSize()) y += speed;
                        break;
                    case "left":
                        if (x - speed >= 0) x -= speed;
                        break;
                    case "right":
                        if (x + speed < background.getScreenWidth() - background.getTileSize()) x += speed;
                        break;
                }
                return; // Move was successful
            }
        }
    }


    /**
     * Moves the enemy in the current direction if there is no collision.
     * If a collision occurs, picks a new random direction.
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
            // Change to a new direction
            String newDirection = direction;
            while (newDirection.equals(direction)) {
                String[] directions = {"up", "down", "left", "right"};
                newDirection = directions[random.nextInt(directions.length)];
            }
            direction = newDirection;
        }
    }

    /**
     * Updates the animation frame counter for sprite switching.
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
     * Draws the enemy sprite to the screen based on direction and animation frame.
     *
     * @param g the Graphics object used to draw the enemy
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
     * Checks if the enemy is within an explosion area and sets it as defeated if so.
     *
     * @param explosionArea the rectangular area affected by the bomb explosion
     */
    public void handleExplosion(Rectangle explosionArea) {
        Rectangle enemyRect = new Rectangle(x, y, background.getTileSize(), background.getTileSize());

        if (explosionArea.intersects(enemyRect)) {
            // Enemy is hit by the explosion, remove or mark as defeated
            this.setAlive(false);
            //increase the score when character is dead
            JackBomber.increaseScore(150);
        }
    }

    private int getplayerDistanceX() {
        return jackBomber.x - this.x;
    }

    private int getplayerDistanceY() {
        return jackBomber.y - this.y;
    }
}

