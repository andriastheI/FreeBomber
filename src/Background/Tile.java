package Background;

import java.awt.image.BufferedImage;

public class Tile {
    public boolean collision = false;
    protected BufferedImage img;
    private boolean levelUp = false;

    public boolean isLevelUp() {
        return levelUp;
    }

    public void setLevelUp(boolean levelUp) {
        this.levelUp = levelUp;
    }
}
