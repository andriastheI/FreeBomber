package Characters;

import Background.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

/**
 * Description:
 * <p>
 * Represents a dragon-type enemy character that inherits behavior from EnemySlug.
 * It overrides image loading and movement logic to customize its visual appearance and behavior.
 * It also handles explosions and awards points to the player upon defeat.
 * </p>
 *
 * @author mguzelocak
 * @author Zelele
 */
public class EnemyDragon extends EnemySlug {
    /** Reference to the Background */
    private final Background background;

    /** Reference to the JackBomber */
    private final JackBomber jackBomber;

    /**
     * Constructor for the EnemySlug2 class.
     *
     * @param bg   The Background object that holds the game map and other environmental data.
     * @param jack The player character, used to determine interactions with the enemy.
     */
    public EnemyDragon(Background bg, JackBomber jack) {
        super(bg, jack);
        this.background = bg;
        this.jackBomber = jack;
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
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/dragon_up1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/dragon_up2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/dragon_down1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/dragon_down2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/dragon_left1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/dragon_left2.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/dragon_left3.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/dragon_right1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/dragon_right2.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("storage/Enemies/dragon_right3.png"));
        } catch (IOException e) {
            System.out.println("Failed to load Enemy Dragon Images");
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

    public void handleExplosion(Rectangle explosionArea) {
        Rectangle enemyRect = new Rectangle(getX(), getY(), background.getTileSize(), background.getTileSize());

        if (explosionArea.intersects(enemyRect)) {
            // Enemy is hit by the explosion, remove or mark as defeated
            this.setAlive(false);
            //increase the score when character is dead
            jackBomber.increaseScore(150);
        }
    }

}
