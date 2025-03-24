import javax.swing.*;

public class Main extends JFrame {
    public Main() {
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
        Main main = new Main();
    }
}
