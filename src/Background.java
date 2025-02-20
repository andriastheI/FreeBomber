package src;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Background extends JFrame {
    private static final int WIN_WIDTH = 800;
    private static final int WIN_HEIGHT = 600;

    public static void main(String[] args) {
        Background background = new Background();
    }


    public Background() {
        setSize(WIN_WIDTH, WIN_HEIGHT);
        getContentPane().setBackground(Color.darkGray);
        setBackground(Color.GREEN);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


}
