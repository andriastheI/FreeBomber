package FreeBomber;

import Background.Background;
import Background.MenuPanel;

import javax.swing.*;

/**
 * The FreeBomber class represents the main window for the FreeBomber game.
 * It initializes the JFrame, displays the menu panel, and handles switching to the game.
 */
public class FreeBomber extends JFrame {

    /**
     * Constructor for the FreeBomber class.
     * Sets up the JFrame and shows the menu panel first.
     */
    public FreeBomber() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("FreeBomber");

        // Show menu panel first
        setContentPane(new MenuPanel(this));

        pack();
        setLocationRelativeTo(null);
        setFocusable(false);
        setVisible(true);
    }

    /**
     * Switches from the menu panel to the actual game panel and starts the game thread.
     */
    public void startGame() {
        Background gamePanel = new Background();
        setContentPane(gamePanel);
        revalidate();  // Re-layout the frame with the new content
        repaint();
        gamePanel.startGameThread();
    }

    /**
     * Main method to launch the FreeBomber game.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FreeBomber::new);
    }
}
