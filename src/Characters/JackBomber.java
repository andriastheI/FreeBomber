package Characters;

import Background.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import Characters.Bomb;

public class JackBomber extends Character {
    private final List<Bomb> bombs = new ArrayList<Bomb>();
    Background background;
    KeyHandler keyHandler;
    Bomb bomb;

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

    public JackBomber() {

    }


    public void setDefaultValues() {
        x = 1;
        y = 1;
        speed = 2;
        direction = "down";
    }

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
            int bombX = x + background.getTileSize() / 2 - bomb.getSize()/ 2;
            int bombY = y + background.getTileSize() / 2 - bomb.getSize() / 2;

            boolean alreadyPlaced = false;
            for (Bomb b : bombs) {
                if (b.x == bombX && b.y == bombY && !b.isFinished()) {
                    alreadyPlaced = true;
                    break;
                }
            }

            if (!alreadyPlaced) {
                bombs.add(new Bomb(bombX, bombY));
            }
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
            File imageFile = new File("storage/bombs/bomb1.png");
            BufferedImage img = ImageIO.read(imageFile);
//            String[] naming = {"down", "up", "right", "left"};
            for (int i = 0; i < 16; i++) {
//                for (int j = 0; j < 3; j++) {
                int cropX = i * 32;
                int cropY = 0;
                int cropWidth = 32;
                int cropHeight = 32;
                BufferedImage croppedImage = img.getSubimage(cropX, cropY, cropWidth, cropHeight);
                System.out.println("cropX = " + cropX + " cropY = " + cropY + " cropWidth = " + cropWidth + " cropHeight = " + cropHeight + "");
                File outputfile = new File("storage/bombs/time_cropped_bomb_" + (i + 1) + ".png");
                ImageIO.write(croppedImage, "png", outputfile);
                System.out.println("Done");
//                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JackBomber test = new JackBomber();
        test.crop();
    }
}
