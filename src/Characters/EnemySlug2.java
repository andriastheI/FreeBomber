package Characters;

import Background.Background;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * The EnemySlug2 class represents a specific enemy type in the game, inheriting behavior from EnemySlug.
 * This class overrides methods to customize the images and movement behavior for the second enemy type.
 */
public class EnemySlug2 extends EnemySlug {
    private final Background background;

    /**
     * Constructor for the EnemySlug2 class.
     *
     * @param bg   The Background object that holds the game map and other environmental data.
     * @param jack The player character, used to determine interactions with the enemy.
     */
    public EnemySlug2(Background bg, JackBomber jack) {
        super(bg, jack);
        this.background = bg;
        // Customize the images for Character.E_Slug2
        getPlayerImage();  // Load different images specific to this enemy
    }

    /**
     * Overrides the getPlayerImage method to load different images for EnemySlug2.
     * This method loads different images for each direction the enemy moves in.
     */
    @Override
    public void getPlayerImage() {
        try {
            // Load different images for the second enemy (EnemySlug2)
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_up1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_up2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_down1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_down2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_left1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_left2.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_left3.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_right1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_right2.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/Enemy2_right3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Overrides the changeDirection method to alter the movement behavior of the enemy.
     * This method changes the enemy's direction to avoid moving in the reverse direction.
     */
    @Override
    public void changeDirection() {
        switch (direction) {
            case "up":
                // If moving up, try moving right or down next (avoid reverse)
                direction = "right";
                break;
            case "down":
                // If moving down, try moving left or up next (avoid reverse)
                direction = "left";
                break;
            case "right":
                // If moving right, try moving down or up next (avoid reverse)
                direction = "down";
                break;
            case "left":
                // If moving left, try moving up or down next (avoid reverse)
                direction = "up";
                break;
        }
    }

    public Background getBackground() {
        return background;
    }
}
