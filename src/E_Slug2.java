package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class E_Slug2 extends E_Slug {
    Background background;

    public E_Slug2(Background bg, JackBomber jack) {
        super(bg, jack);
        // Customize the images for E_Slug2
        getPlayerImage();  // Load different images
    }

    @Override
    public void getPlayerImage() {
        try {
            // Load different images for the second enemy
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_up1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_up2.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_down1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_down2.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_left1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_left2.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_left3.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_right1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_right2.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/Enemies/Enemy1_right3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
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
}

