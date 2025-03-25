package FreeBomber;

import Background.Background;

import javax.swing.*;

public class FreeBomber extends JFrame {
    public FreeBomber() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("FreeBomber");
        Background gamePanel = new Background();
        add(gamePanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        gamePanel.startGameThread();
    }

    public static void main(String[] args) {
        FreeBomber freeBomber = new FreeBomber();
    }
}
