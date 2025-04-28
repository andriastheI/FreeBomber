package Characters;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * The base class for all characters in the game, including players and enemies.
 * It holds common attributes such as position, direction, sprite animations,
 * and state flags like collision and level progression.
 */
public class Character {

    /** X-coordinate of the character on the screen. */
    private int x;

    /** Y-coordinate of the character on the screen. */
    private int y;

    /** Movement speed of the character. */
    public int speed;

    /** Sprite frames for animations in all four directions. */
    public BufferedImage up1, up2, up3, up4,
            down1, down2, down3, down4,
            left1, left2, left3, left4,
            right1, right2, right3, right4;

    /** The current direction the character is facing ("up", "down", "left", "right"). */
    public String direction;
    /** Controls the frame update rate for sprite animations. */
    public int spriteCounter = 0;
    /** Indicates which sprite frame is currently active (1-4). */
    public int spriteNum = 1;
    /** Rectangle used for collision detection (typically smaller than the sprite itself). */
    public Rectangle spriteBounds;
    /** Flag indicating whether the character is currently colliding with something. */
    public boolean collisionOn = false;
    /** Flag indicating whether the character has triggered a level-up. */
    private boolean levelUp = false;
    /** Flag indicating whether the character has found the level's exit door. */
    private boolean doorFound = false;
    /** Flag indicating whether the character is alive. */
    private boolean alive = true;
    /** Timestamp when the current level started, used for time-based logic. */
    private long levelStartTime = System.currentTimeMillis();

    /**
     * Gets the timestamp when the level started.
     *
     * @return the level start time in milliseconds.
     */
    public long getLevelStartTime() {
        return levelStartTime;
    }

    /**
     * Sets the timestamp when the level started.
     *
     * @param levelStartTime the start time in milliseconds.
     */
    public void setLevelStartTime(long levelStartTime) {
        this.levelStartTime = levelStartTime;
    }

    /**
     * Returns whether the character is currently alive.
     *
     * @return true if the character is alive, false otherwise.
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * Sets the alive status of the character.
     *
     * @param alive true to mark the character as alive, false otherwise.
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    /**
     * Returns whether the character has found the exit door.
     *
     * @return true if the door has been found, false otherwise.
     */
    public boolean isDoorFound() {
        return doorFound;
    }

    /**
     * Sets whether the character has found the exit door.
     *
     * @param doorFound true if the door has been found, false otherwise.
     */
    public void setDoorFound(boolean doorFound) {
        this.doorFound = doorFound;
    }

    /**
     * Returns whether the character has triggered a level-up.
     *
     * @return true if the character leveled up, false otherwise.
     */
    public boolean isLevelUp() {
        return levelUp;
    }

    /**
     * Sets whether the character has triggered a level-up.
     *
     * @param levelUp true to mark the character as having leveled up, false otherwise.
     */
    public void setLevelUp(boolean levelUp) {
        this.levelUp = levelUp;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}