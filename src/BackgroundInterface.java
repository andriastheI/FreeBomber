package src;

import java.awt.*;

public interface BackgroundInterface {

    int FPS = 60;
    public int TILE_SIZE = 46;
    int SCREEN_COLS = 18;
    int SCREEN_ROWS = 14;
    int SCREEN_WIDTH = SCREEN_COLS * TILE_SIZE;
    int SCREEN_HEIGHT = SCREEN_ROWS * TILE_SIZE;

    void startGameThread();

    void run();

    void update();

    void paintComponent(Graphics g);
}