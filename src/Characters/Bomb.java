package Characters;

import Background.Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Bomb extends Character {

    private static final int COUNTDOWN = 180; // ~3 seconds if game runs at 60 FPS
    private final int size = 32; // Assuming tile size
    Background background;
    private int x, y;
    private int timer;
    private boolean exploded;
    private BufferedImage bomb1, bomb2, bomb3, bomb4, bomb5, bomb6, bomb7,
            bomb8, bomb9, bomb10, bomb11, bomb12, bomb13, bomb14, bomb15, bomb16;
    private BufferedImage[] bombFrames;
    private BufferedImage expLeft1, expLeft2, expLeft3, expLeft4, expLeft5, expLeft6,
            expLeft7, expLeft8, expLeft9, expRight1, expRight2, expRight3, expRight4,
            expRight5, expRight6, expRight7, expRight8, expRight9, expUp1, expUp2, expUp3,
            expUp4, expUp5, expUp6, expUp7, expUp8, expUp9, expDown1, expDown2, expDown3,
            expDown4, expDown5, expDown6, expDown7, expDown8, expDown9, expMid1, expMid2,
            expMid3, expMid4, expMid5, expMid6, expMid7, expMid8, expMid9;
    private BufferedImage[] explosionLeftFrames;
    private BufferedImage[] explosionRightFrames;
    private BufferedImage[] explosionUpFrames;
    private BufferedImage[] explosionDownFrames;
    private BufferedImage[] explosionMiddleFrames;

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        this.timer = COUNTDOWN;
        this.exploded = false;
        getBombImage();
    }

    public Bomb() {
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

    private void explode() {
        exploded = true;
    }

    public boolean isFinished() {
        return exploded && timer <= -30;
    }

    public int getSize() {
        return size;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
