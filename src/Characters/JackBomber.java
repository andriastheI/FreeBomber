package Characters;

import Background.Background;
import Background.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the main player character, JackBomber.
 * <p>
 * JackBomber handles movement, animation, bomb placement, and collision logic.
 * It interacts with the game background and listens for keyboard input.
 * </p>
 */
public class JackBomber extends Character {
//    private final EnemyCollision enemyCollision = new EnemyCollision(this.background);

    /**
     * List of active bombs placed by the player.
     */
    private final List<Bomb> bombs = new ArrayList<Bomb>();
    /**
     * The current bomb object being placed.
     */
    public Bomb bomb;
    /**
     * Reference to the game background.
     */
    Background background;
    /**
     * Reference to the key handler for capturing input.
     */
    KeyHandler keyHandler;
    /**
     * Flag indicating whether a bomb was just dropped.
     */
    private boolean bombJustDropped = false;

    private int playerHealth = 3;

    private boolean invincible = false;

    private long lastDamageTime = 0;

    private final int INVINCIBILITY_DURATION = 3000;


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
     * Default constructor.
     */
    public JackBomber() {
    }

    /**
     * Main method for testing image cropping.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        JackBomber test = new JackBomber();
        test.crop();
    }

    /**
     * Sets the initial default values for position, speed and direction.
     */
    public void setDefaultValues() {
        x = 1;
        y = 1;
        speed = 2;
        direction = "down";
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
            e.printStackTrace();
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

        if (keyHandler.isBombDrop()) {
            if (!bombJustDropped) {

                int bombX = x + background.getTileSize() / 4;
                int bombY = y + background.getTileSize() / 2;


                boolean alreadyPlaced = false;
                for (Bomb b : bombs) {
                    if (b.getX() == bombX && b.getY() == bombY && !b.isFinished()) {
                        alreadyPlaced = true;
                        break;
                    }
                }

                if (!alreadyPlaced) {
                    bombs.add(new Bomb(bombX, bombY, this.background));
                }

                bombJustDropped = true;
            }
        } else {
            bombJustDropped = false;
        }

        for (int i = 0; i < bombs.size(); i++) {
            Bomb b = bombs.get(i);
            b.update();
            if (b.isFinished()) {
                bombs.remove(i);
                i--;
            }
        }
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

    private void crop() {
        try {
            File imageFile = new File("storage/bombs/explosion.png");
            BufferedImage img = ImageIO.read(imageFile);
            for (int i = 0; i < 9; i++) {
                int cropX = i * 32;
                int cropY = 64;
                int cropWidth = 32;
                int cropHeight = 32;
                BufferedImage croppedImage = img.getSubimage(cropX, cropY, cropWidth, cropHeight);
                System.out.println("cropX = " + cropX + " cropY = " + cropY + " cropWidth = " + cropWidth + " cropHeight = " + cropHeight + "");
                File outputfile = new File("storage/bombs/explosion_cropped_extensionVertical" + (i + 1) + ".png");
                ImageIO.write(croppedImage, "png", outputfile);
                System.out.println("Done");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        System.out.println("Player hit! Remaining health: " + playerHealth);
        if (getPlayerHealth() <= 0) {
            background.gameOver = true;
        }

        invincible = true;
        lastDamageTime = System.currentTimeMillis();
    }

    public Rectangle getSpriteBounds() {
        return new Rectangle(x + spriteBounds.x, y + spriteBounds.y, spriteBounds.width, spriteBounds.height);
    }

    public int getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }
}
