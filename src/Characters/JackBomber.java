package Characters;

import Background.Background;
import Background.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * <p>
 * Represents the main player character, JackBomber.
 * Handles movement, sprite animation, collision detection, bomb placement,
 * score management, level time tracking, and interaction with game elements like enemies and tiles.
 * </p>
 *
 * @author mguzelocak
 * @author Zelele
 */
public class JackBomber extends Character {

    /** List of active bombs placed by the player. */
    private final List<Bomb> bombs = new ArrayList<Bomb>();
    /** Duration (in milliseconds) for which the player remains invincible after taking damage. */
    private final int INVINCIBILITY_DURATION = 1000;
    /** Total time (in milliseconds) allowed to complete a level. */
    private final int TIME_LIMIT = 60000;
    /** Maximum number of bombs the player can place on the screen simultaneously. */
    private final int BOMB_LIMIT = 3;
    /** Reference to the game background. */
    private final Background background;
    /** Reference to the key handler for capturing input. */
    private final KeyHandler keyHandler;
    /** The current bomb object being placed. */
    public Bomb bomb;
    /** game score keeper */
    private int score = 0;
    /** Flag indicating whether a bomb was just dropped. */
    private boolean bombJustDropped = false;

    /** Current health of the player, measured in number of hearts */
    private int playerHealth = 3;

    /** Indicates whether the player is currently invincible (e.g., after taking damage) */
    private boolean invincible = false;

    /** Timestamp of the last time the player took damage, used to manage invincibility frames */
    private long lastDamageTime = 0;

    /** Remaining time for the current level, in milliseconds */
    private long remainingTime = 0;


    /**
     * Constructs a new JackBomber with background and input handler.
     *
     * @param bg   the background environment
     * @param kh   the key handler
     * @param bomb the bomb reference
     */
    public JackBomber(Background bg, KeyHandler kh, Bomb bomb) {
        this.background = bg;
        this.keyHandler = kh;
        setDefaultValues();
        getPlayerImage();
        this.bomb = bomb;

        //this rectangle is used as a collision detector that is smaller than the champion player
        //so that it is flexible for going through tight spaces.
        spriteBounds = new Rectangle(6, 18, 28, 25);
    }

    /**
     * Increases the player's score by the specified amount.
     *
     * @param amount the number of points to add to the current score
     */
    public void increaseScore(int amount) {
        this.score += amount;
    }

    /**
     * Decreases the player's score by the specified amount.
     *
     * @param amount the number of points to subtract from the current score
     */
    public void decreaseScore(int amount) {
        this.score -= amount;
    }

    /**
     * Returns the current score of the player.
     *
     * @return the current score as an integer
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Sets the player's current score.
     *
     * @param score the score value to assign to the player
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Sets the initial default values for position, speed and direction.
     */
    public void setDefaultValues() {
        x = 1;
        y = 1;
        speed = 2;
        direction = "down";
        setLevelStartTime(System.currentTimeMillis());
    }

    /**
     * Loads the player's sprite images from resources for animation.
     */
    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_up_1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_up_2.png"));
            up3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_up_3.png"));
            up4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_up_4.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_down_1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_down_2.png"));
            down3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_down_3.png"));
            down4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_down_4.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_left_1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_left_2.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_left_3.png"));
            left4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_left_4.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_right_1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_right_2.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_right_3.png"));
            right4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/player/champ1_cropped_right_4.png"));

        } catch (IOException e) {
            System.out.println("Failed to load JackBomber Images");
        }
    }

    /**
     * Updates the player's state each frame, including movement and animation.
     */
    public void update() {

        if (keyHandler.isUpDirection() || keyHandler.isDownDirection() ||
                keyHandler.isLeftDirection() || keyHandler.isRightDirection()) {
            if (keyHandler.isUpDirection()) {
                direction = "up";
            } else if (keyHandler.isDownDirection()) {
                direction = "down";
            } else if (keyHandler.isLeftDirection()) {
                direction = "left";
            } else {
                direction = "right";
            }

            collisionOn = false;
            background.getCheckCollision().checkCollision(this);

            if (!collisionOn) {
                // Prevent player from going out of bounds
                if (direction.equals("up")) {
                    if (y - speed >= 0) { // Prevent moving above the top of the screen
                        y -= speed;
                    }
                }
                if (direction.equals("down")) {
                    if (y + speed < background.getScreenHeight() - background.getTileSize()) { // Prevent moving below the bottom of the screen
                        y += speed;
                    }
                }
                if (direction.equals("left")) {
                    if (x - speed >= 0) { // Prevent moving left off the screen
                        x -= speed;
                    }
                }
                if (direction.equals("right")) {
                    if (x + speed < background.getScreenWidth() - background.getTileSize()) { // Prevent moving right off the screen
                        x += speed;
                    }
                }
            }

            spriteCounter++;
            if (spriteCounter > 8) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 3;
                } else if (spriteNum == 3) {
                    spriteNum = 4;
                } else if (spriteNum == 4) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }
        }

        // checks if space bar is used
        if (keyHandler.isBombDrop()) {
            // prevents holding the space bar and repeated bom drops
            if (!bombJustDropped && bombs.size() < BOMB_LIMIT) {

                // bomb position relative to player
                int bombX = x + background.getTileSize() / 4;
                int bombY = y + background.getTileSize() / 4;

                boolean alreadyPlaced = false;
                // loops trough all the active bombs
                for (Bomb b : bombs) {
                    //check if bomb is on the game and hasn't exploded yet
                    if (b.getX() == bombX && b.getY() == bombY && !b.isFinished()) {
                        // prevent duplication
                        alreadyPlaced = true;
                        break;
                    }
                }

                // if no bombs are active adds bombs
                if (!alreadyPlaced) {
                    bombs.add(new Bomb(bombX, bombY, this.background));
                }

                // prevents bombs to drop multiple times again
                bombJustDropped = true;
            }
        } else {
            bombJustDropped = false;
        }

        // this loop updates the bombs' animation and removes the bombs when animation over
        for (int i = 0; i < bombs.size(); i++) {
            Bomb b = bombs.get(i);
            b.update();
            if (b.isFinished()) {
                bombs.remove(i);
                i--;
            }
        }

        // player takes a damage if player cant find the door
        long currentTime = System.currentTimeMillis();
        if (!isDoorFound() && currentTime - getLevelStartTime() > TIME_LIMIT) {
            invincible = false;
            takeDamage();
            setLevelStartTime(currentTime);
        }
        long remaining = TIME_LIMIT - (System.currentTimeMillis() - getLevelStartTime());
        setRemainingTime(remaining);
    }

    /**
     * Renders the player sprite on the screen.
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
                if (spriteNum == 4) {
                    img = up4;
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
                if (spriteNum == 4) {
                    img = down4;
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
                if (spriteNum == 4) {
                    img = left4;
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
                if (spriteNum == 4) {
                    img = right4;
                }
                break;
        }
        g.drawImage(img, x, y, background.getTileSize(), background.getTileSize(), null);
        for (Bomb b : bombs) {
            b.draw((Graphics2D) g);
        }
    }

    /**
     * Applies damage to the player, reducing health if not currently invincible.
     * Triggers game over if health reaches zero.
     */
    public void takeDamage() {
        long currentTime = System.currentTimeMillis();

        if (invincible) {
            if (currentTime - lastDamageTime < INVINCIBILITY_DURATION) {
                return;
            } else {
                invincible = false;
            }
        }

        playerHealth--;
        decreaseScore(300);
        if (getPlayerHealth() <= 0) {
            background.gameOver = true;
        }

        invincible = true;
        lastDamageTime = System.currentTimeMillis();
    }

    /**
     * Returns the actual bounds of the character sprite used for collision detection.
     *
     * @return the sprite bounds rectangle adjusted to the current position
     */
    public Rectangle getSpriteBounds() {
        return new Rectangle(x + spriteBounds.x, y + spriteBounds.y, spriteBounds.width, spriteBounds.height);
    }

    /**
     * Gets the current health of the player.
     *
     * @return number of remaining hearts
     */
    public int getPlayerHealth() {
        return playerHealth;
    }

    /**
     * Gets the remaining time for the current level.
     *
     * @return remaining time in milliseconds
     */
    public long getRemainingTime() {
        return remainingTime;
    }

    /**
     * Sets the remaining time for the current level.
     * This is used to update the level countdown timer shown to the player.
     *
     * @param remainingTime the remaining time in milliseconds
     */
    public void setRemainingTime(long remainingTime) {
        this.remainingTime = remainingTime;
    }

}
