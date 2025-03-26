package Bomb;

import Background.Background;
import Characters.Character;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bomb extends Character {
    public static final int SIZE = 32; // Assuming tile size
    private static final int COUNTDOWN = 180; // ~3 seconds if game runs at 60 FPS
    public int x, y;
    public int timer;
    public boolean exploded;
    public BufferedImage bomb1, bomb2, bomb3, bomb4, bomb5, bomb6, bomb7, bomb8, bomb9, bomb10, bomb11, bomb12, bomb13, bomb14, bomb15, bomb16;
    public BufferedImage[] bombFrames;
    Background background;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        this.timer = COUNTDOWN;
        this.exploded = false;
        getBombImage();
    }

    public void update() {
        if (!exploded) {
            if (timer > 0) {
                timer--;
            } else {
                explode();
            }
        } else {
            timer--;
        }
    }

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
            bombFrames = new BufferedImage[]{
                    bomb1, bomb2, bomb3, bomb4,
                    bomb5, bomb6, bomb7, bomb8,
                    bomb9, bomb10, bomb11, bomb12,
                    bomb13, bomb14, bomb15, bomb16
            };
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics g) {
        BufferedImage img = null;
        if (!exploded) {
            int frameIndex = (COUNTDOWN - timer) * bombFrames.length / COUNTDOWN;
            frameIndex = Math.min(frameIndex, bombFrames.length - 1);  // prevent overflow
            img = bombFrames[frameIndex];
            g.drawImage(img, x, y, SIZE, SIZE, null);
        } else {
            g.setColor(Color.ORANGE);
            g.fillOval(x - SIZE, y, SIZE, SIZE); // left
            g.fillOval(x + SIZE, y, SIZE, SIZE); // right
            g.fillOval(x, y - SIZE, SIZE, SIZE); // up
            g.fillOval(x, y + SIZE, SIZE, SIZE); // down
            g.fillOval(x, y, SIZE, SIZE); // center
//            collisionOn = false;
//            background.checkCollision.checkCollision(this);
//
//            if (!collisionOn) {
//
//            }
        }
        g.drawImage(img, x, y, null);
    }

    private void explode() {
        exploded = true;
    }

    public boolean isFinished() {
        return exploded && timer <= -30;
    }
}
