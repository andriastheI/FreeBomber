package src;

import javax.swing.*;
import java.awt.*;

public class Background extends JFrame {
    private static final int WIN_WIDTH = 900;
    private static final int WIN_HEIGHT = 600;

    public static void main(String[] args) {
        Background background = new Background();
    }


    public Background() {
        setSize(WIN_WIDTH, WIN_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


}
