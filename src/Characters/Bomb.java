package Characters;

import Background.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a bomb object that can be placed by the player.
 * <p>
 * Bombs have a countdown timer and explode after a certain duration,
 * affecting nearby enemies and destructible tiles.
 * </p>
 */
public class Bomb extends Character {

    // Countdown value in frames before bomb explodes (~3 seconds at 60 FPS).
    private static final int COUNTDOWN = 180;
    // Size of the bomb, typically matching the tile size.
    private final int size = 32;
    // Game background for managing tiles and enemies.
    Background background;
    // Position of the bomb in pixels.
    private int x, y;
    // Countdown timer that decreases every frame.
    private int timer;
    // Whether the bomb has exploded.
    private boolean exploded;
    // Whether the bomb has collided with something.
    private boolean collision;
    // Individual frames (not directly used outside init)
    private BufferedImage bomb1, bomb2, bomb3, bomb4, bomb5, bomb6, bomb7,
            bomb8, bomb9, bomb10, bomb11, bomb12, bomb13, bomb14, bomb15, bomb16,
            expLeft1, expLeft2, expLeft3, expLeft4, expLeft5, expLeft6,
            expLeft7, expLeft8, expLeft9, expRight1, expRight2, expRight3, expRight4,
            expRight5, expRight6, expRight7, expRight8, expRight9, expUp1, expUp2, expUp3,
            expUp4, expUp5, expUp6, expUp7, expUp8, expUp9, expDown1, expDown2, expDown3,
            expDown4, expDown5, expDown6, expDown7, expDown8, expDown9, expMid1, expMid2,
            expMid3, expMid4, expMid5, expMid6, expMid7, expMid8, expMid9;

    /**
     * Bomb animation frames
     */
    private BufferedImage[] bombFrames;

    /**
     * Explosion animation frames (in each direction)
     */
    private BufferedImage[] explosionLeftFrames;
    private BufferedImage[] explosionRightFrames;
    private BufferedImage[] explosionUpFrames;
    private BufferedImage[] explosionDownFrames;
    private BufferedImage[] explosionMiddleFrames;

    /**
     * Minimal constructor used for placeholder instantiation.
     */
    public Bomb(int x, int y) {
    }

    /**
     * Full constructor used to create an active bomb.
     *
     * @param x  x position in pixels
     * @param y  y position in pixels
     * @param bg background reference
     */
    public Bomb(int x, int y, Background bg) {
        this.x = x;
        this.y = y;
        this.timer = COUNTDOWN;
        this.exploded = false;
        this.background = bg;
        getBombImage();
    }

    /**
     * Constructor used when bomb is initialized but not placed.
     *
     * @param bg background reference
     */
    public Bomb(Background bg) {
        this.background = bg;
    }

    /**
     * Returns whether the bomb has exploded.
     *
     * @return true if exploded, false otherwise
     */
    public boolean isExploded() {
        return exploded;

    }

    /**
     * Updates the bomb timer and triggers explosion when countdown ends.
     */
    public void update() {
        if (!exploded) {
            if (timer > 0) {
                timer--;
            } else {
                explode();
                triggerExplosion(); // Move explosion logic into a separate method

            }
        } else {
            timer--;
        }
    }

    /**
     * Handles the explosion logic and checks for collisions with enemies and soft walls.
     */
    private void triggerExplosion() {
        int explosionRadius = 28;
        Rectangle explosionArea = new Rectangle(this.getX() - explosionRadius, this.getY() - explosionRadius, 2 * explosionRadius, 2 * explosionRadius);
        background.getTileManager().handleExplosion(explosionArea);// Handle explosion and replace soft walls
        background.getEnemy1().handleExplosion(explosionArea);
        background.getEnemy2().handleExplosion(explosionArea);
        background.getEnemy3().handleExplosion(explosionArea);
        background.getEnemy4().handleExplosion(explosionArea);
        exploded = true;
        if (background.getPlayer().getSpriteBounds().intersects(explosionArea)) {
            background.getPlayer().takeDamage();
        }
    }

    /**
     * Loads all bomb and explosion animation frames.
     */
    public void getBombImage() {
        try {
            bomb1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_1.png"));
            bomb2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_2.png"));
            bomb3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_3.png"));
            bomb4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_4.png"));
            bomb5 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_5.png"));
            bomb6 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_6.png"));
            bomb7 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_7.png"));
            bomb8 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_8.png"));
            bomb9 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_9.png"));
            bomb10 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_10.png"));
            bomb11 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_11.png"));
            bomb12 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_12.png"));
            bomb13 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_13.png"));
            bomb14 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_14.png"));
            bomb15 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_15.png"));
            bomb16 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/time_cropped_bomb_16.png"));
            expLeft1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_left1.png"));
            expLeft2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_left2.png"));
            expLeft3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_left3.png"));
            expLeft4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_left4.png"));
            expLeft5 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_left5.png"));
            expLeft6 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_left6.png"));
            expLeft7 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_left7.png"));
            expLeft8 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_left8.png"));
            expLeft9 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_left9.png"));
            expRight1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_right1.png"));
            expRight2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_right2.png"));
            expRight3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_right3.png"));
            expRight4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_right4.png"));
            expRight5 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_right5.png"));
            expRight6 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_right6.png"));
            expRight7 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_right7.png"));
            expRight8 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_right8.png"));
            expRight9 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_right9.png"));
            expUp1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_up1.png"));
            expUp2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_up2.png"));
            expUp3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_up3.png"));
            expUp4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_up4.png"));
            expUp5 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_up5.png"));
            expUp6 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_up6.png"));
            expUp7 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_up7.png"));
            expUp8 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_up8.png"));
            expUp9 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_up9.png"));
            expDown1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_down1.png"));
            expDown2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_down2.png"));
            expDown3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_down3.png"));
            expDown4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_down4.png"));
            expDown5 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_down5.png"));
            expDown6 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_down6.png"));
            expDown7 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_down7.png"));
            expDown8 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_down8.png"));
            expDown9 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_down9.png"));
            expMid1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_mid1.png"));
            expMid2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_mid2.png"));
            expMid3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_mid3.png"));
            expMid4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_mid4.png"));
            expMid5 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_mid5.png"));
            expMid6 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_mid6.png"));
            expMid7 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_mid7.png"));
            expMid8 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_mid8.png"));
            expMid9 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/bombs/explosion_cropped_mid9.png"));

            bombFrames = new BufferedImage[]{
                    bomb1, bomb2, bomb3, bomb4,
                    bomb5, bomb6, bomb7, bomb8,
                    bomb9, bomb10, bomb11, bomb12,
                    bomb13, bomb14, bomb15, bomb16
            };
            explosionLeftFrames = new BufferedImage[]{
                    expLeft1, expLeft2, expLeft3,
                    expLeft4, expLeft5, expLeft6,
                    expLeft7, expLeft8, expLeft9
            };
            explosionRightFrames = new BufferedImage[]{
                    expRight1, expRight2, expRight3,
                    expRight4, expRight5, expRight6,
                    expRight7, expRight8, expRight9
            };
            explosionUpFrames = new BufferedImage[]{
                    expUp1, expUp2, expUp3,
                    expUp4, expUp5, expUp6,
                    expUp7, expUp8, expUp9
            };
            explosionDownFrames = new BufferedImage[]{
                    expDown1, expDown2, expDown3,
                    expDown4, expDown5, expDown6,
                    expDown7, expDown8, expDown9
            };
            explosionMiddleFrames = new BufferedImage[]{
                    expMid1, expMid2, expMid3,
                    expMid4, expMid5, expMid6,
                    expMid7, expMid8, expMid9
            };

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draws the bomb or explosion animation depending on state.
     *
     * @param g the Graphics context to draw on
     */
    public void draw(Graphics g) {
        BufferedImage img = null;
        if (!exploded) {
            int frameIndex = (COUNTDOWN - timer) * bombFrames.length / COUNTDOWN;
            frameIndex = Math.min(frameIndex, bombFrames.length - 1);  // prevent overflow
            img = bombFrames[frameIndex];
            g.drawImage(img, x, y, getSize(), getSize(), null);
        } else {
            int frameIndex = Math.min((Math.abs(timer) * 9) / 30, 8); // explosion animation with 30 frame / 9 pictures

            g.drawImage(explosionMiddleFrames[frameIndex], x, y, size, size, null);
            g.drawImage(explosionLeftFrames[frameIndex], x - size, y, size, size, null);
            g.drawImage(explosionRightFrames[frameIndex], x + size, y, size, size, null);
            g.drawImage(explosionUpFrames[frameIndex], x, y - size, size, size, null);
            g.drawImage(explosionDownFrames[frameIndex], x, y + size, size, size, null);
        }
        g.drawImage(img, x, y, null);
    }

    /**
     * Flags the bomb as exploded.
     */
    private void explode() {
        exploded = true;
    }

    /**
     * Returns whether the bomb's explosion animation is finished.
     *
     * @return true if the animation is done, false otherwise
     */
    public boolean isFinished() {
        return exploded && timer <= -30;
    }

    /**
     * Returns the bomb's visual size (usually 32x32).
     *
     * @return bomb size in pixels
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the x position of the bomb.
     *
     * @return x in pixels
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y position of the bomb.
     *
     * @return y in pixels
     */
    public int getY() {
        return y;
    }

    /**
     * Checks and destroys any soft walls (boxes) adjacent to the bomb.
     *
     * @param tileX tile x-coordinate
     * @param tileY tile y-coordinate
     */
    private void destroyNearbyBoxes(int tileX, int tileY) {
        // Check nearby tiles (left, right, up, down) for boxes (assuming box is represented by a specific tile index)
        if (this.background.getTileManager().mapTileNum[tileX][tileY] == 2) { // Assume '1' is the tile representing a box
            this.background.getTileManager().mapTileNum[tileX][tileY] = 0; // Destroy box by setting it to empty space
        }
        // Check in adjacent directions
        if (tileX > 0 && this.background.getTileManager().mapTileNum[tileX - 1][tileY] == 2) {
            this.background.getTileManager().mapTileNum[tileX - 1][tileY] = 0; // Destroy box to the left
        }
        if (tileX < this.background.getTileManager().mapTileNum.length - 1 && this.background.getTileManager().mapTileNum[tileX + 1][tileY] == 1) {
            this.background.getTileManager().mapTileNum[tileX + 1][tileY] = 0; // Destroy box to the right
        }
        if (tileY > 0 && this.background.getTileManager().mapTileNum[tileX][tileY - 1] == 2) {
            this.background.getTileManager().mapTileNum[tileX][tileY - 1] = 0; // Destroy box above
        }
        if (tileY < this.background.getTileManager().mapTileNum[0].length - 1 && this.background.getTileManager().mapTileNum[tileX][tileY + 1] == 2) {
            this.background.getTileManager().mapTileNum[tileX][tileY + 1] = 0; // Destroy box below
        }
    }

    /**
     * Returns whether a collision was detected.
     *
     * @return true if collided
     */
    public boolean isCollision() {
        return collision;
    }

    /**
     * Sets the collision flag for the bomb.
     *
     * @param collision true if collision occurred
     */
    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}

