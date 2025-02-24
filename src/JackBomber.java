package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class JackBomber extends Character {
    Background background;
    KeyHandler keyHandler;

    public JackBomber (Background bg, KeyHandler kh) {
        this.background = bg;
        this.keyHandler = kh;
        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
        direction = "down";
    }

    public void getPlayerImage() {
        try {
            up1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_up_1.png"));
            up2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_up_2.png"));
            up3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_up_3.png"));
            up4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_up_4.png"));
            down1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_down_1.png"));
            down2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_down_2.png"));
            down3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_down_3.png"));
            down4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_down_4.png"));
            left1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_left_1.png"));
            left2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_left_2.png"));
            left3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_left_3.png"));
            left4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_left_4.png"));
            right1 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_right_1.png"));
            right2 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_right_2.png"));
            right3 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_right_3.png"));
            right4 = ImageIO.read(getClass().getClassLoader().getResourceAsStream("src/storage/player/champ1_cropped_right_4.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if(keyHandler.upDirection || keyHandler.downDirection ||
                keyHandler.leftDirection || keyHandler.rightDirection) {
            if(keyHandler.upDirection){
                direction = "up";
                y -= speed;
            } else if(keyHandler.downDirection){
                direction = "down";
                y += speed;
            } else if(keyHandler.leftDirection){
                direction = "left";
                x -= speed;
            } else if(keyHandler.rightDirection){
                direction = "right";
                x += speed;
            }
            spriteCounter++;
            if(spriteCounter > 8) {
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
        g.drawImage(img, x, y, background.tileSize, background.tileSize, null);
    }

    private void crop() {
        try {
            File imageFile = new File("src/storage/player/champ1.png");
            BufferedImage img = ImageIO.read(imageFile);
            String[] naming = { "up", "down", "left", "right"};
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    int cropX = j * 32;
                    int cropY = i * 48;
                    int cropWidth = 32;
                    int cropHeight = 48;
                    BufferedImage croppedImage = img.getSubimage(cropX, cropY, cropWidth, cropHeight);
                    System.out.println("cropX = " + cropX + " cropY = " + cropY + " cropWidth = " + cropWidth + " cropHeight = " + cropHeight + "");
                    File outputfile = new File("src/storage/player/champ1_cropped_" + naming[i] + "_" + (j + 1) + ".png");
                    ImageIO.write(croppedImage, "png", outputfile);
                    System.out.println("Done");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
