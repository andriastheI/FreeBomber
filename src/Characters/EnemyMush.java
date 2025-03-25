package Characters;

import Background.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.Timer;
import java.util.Random;

public class EnemyMush extends Character {
    Background background;
    Timer movementTimer;
    Random random;
    JackBomber jackBomber;

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

    public void setDefaultValues() {
        x = background.getScreenWidth() - 2 * background.getTileSize();
        y = background.getScreenHeight() - 2 * background.getTileSize();
        speed = 10;
        direction = "left";
    }

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
}


