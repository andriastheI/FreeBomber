package Characters;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Character {
    public int x, y;
    public int speed;

    public BufferedImage up1, up2, up3, up4, down1, down2, down3, down4, left1, left2, left3, left4, right1, right2, right3, right4;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle spriteBounds;
    public boolean collisionOn = false;
    private boolean levelUp = false;
    private boolean doorFound = false;
    private boolean alive = true;
    private long levelStartTime = System.currentTimeMillis();

    public long getLevelStartTime() {
        return levelStartTime;
    }

    public void setLevelStartTime(long levelStartTime) {
        this.levelStartTime = levelStartTime;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDoorFound() {
        return doorFound;
    }

    public void setDoorFound(boolean doorFound) {
        this.doorFound = doorFound;
    }



    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isLevelUp() {
        return levelUp;
    }

    public void setLevelUp(boolean levelUp) {
        this.levelUp = levelUp;
    }
}
