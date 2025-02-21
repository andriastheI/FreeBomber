package src;

import java.awt.*;

public class JackBomber extends Character {
    BackgroundInterface background;
    KeyHandlerInterface keyHandler;

    public void Player(BackgroundInterface bg, KeyHandlerInterface kh) {
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
        if(keyHandler.upDirection == true){
            y -= speed;
        } else if(keyHandler.downDirection == true){
            y += speed;
        } else if(keyHandler.leftDirection == true){
            x -= playerSp;
        } else if(keyHandler.rightDirection == true){
            x += playerSp;
        }
    }

    public void draw(Graphics g) {
        g2.setColor(Color.WHITE);
        g2.fillRect(x, y, background.TILE_SIZE, background.TILE_SIZE);
    }
}
