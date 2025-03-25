package Bomb;

import Background.Background;

import java.awt.*;

public class Bomb {
    public int x, y;
    public int timer;
    public boolean exploded;
    public static final int SIZE = 32; // Assuming tile size
    private static final int COUNTDOWN = 180; // ~3 seconds if game runs at 60 FPS

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        this.timer = COUNTDOWN;
        this.exploded = false;
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

    public void draw(Graphics2D g2) {
        if (!exploded) {
            g2.setColor(Color.BLACK);
            g2.fillOval(x, y, SIZE, SIZE);
        } else {
            g2.setColor(Color.ORANGE);
            g2.fillOval(x - SIZE, y, SIZE, SIZE); // left
            g2.fillOval(x + SIZE, y, SIZE, SIZE); // right
            g2.fillOval(x, y - SIZE, SIZE, SIZE); // up
            g2.fillOval(x, y + SIZE, SIZE, SIZE); // down
            g2.fillOval(x, y, SIZE, SIZE); // center
        }
    }

    private void explode() {
        exploded = true;
    }

    public boolean isFinished() {
        return exploded && timer <= -30;
    }
}
