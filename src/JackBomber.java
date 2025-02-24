package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class JackBomber extends Character {
    Background background;
    KeyHandler keyHandler;

    public void Player(Background bg, KeyHandler kh) {
        this.background = bg;
        this.keyHandler = kh;
        setDefaultValues();
    }

    public void setDefaultValues() {
        x = 100;
        y = 100;
        speed = 4;
    }

    public void update() {
        if(keyHandler.upDirection){
            y -= speed;
        } else if(keyHandler.downDirection){
            y += speed;
        } else if(keyHandler.leftDirection){
            x -= speed;
        } else if(keyHandler.rightDirection){
            x += speed;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y, background.tileSize, background.tileSize);
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

    public static void main(String[] args) {
        System.out.println(new File("storage/player/champ1.png").getAbsolutePath());
        JackBomber jackBomber = new JackBomber();
        jackBomber.crop();
    }
}
