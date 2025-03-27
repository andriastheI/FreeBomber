package FreeBomber;

import Background.Background;

import javax.swing.*;

/**
 * The FreeBomber class represents the main window for the FreeBomber game.
 * It initializes the JFrame, sets properties, and starts the game thread.
 */
public class FreeBomber extends JFrame {

    /**wdsdsdawdwdsdwdwsdsdwd
     * Constructor for the FreeBomber class.
     * Sets up the JFrame, adds the game panel (Background), and starts the game thread.
     */
    public FreeBomber() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Set default close operation
        setResizable(false);  // Disable resizing of the window
        setTitle("FreeBomber");  // Set the window title
        Background gamePanel = new Background();  // Create a new game panel
        add(gamePanel);  // Add the game panel to the JFrame
        pack();  // Adjust the window size to fit the panel
        setLocationRelativeTo(null);  // Center the window on the screen
        setVisible(true);  // Make the window visible
        gamePanel.startGameThread();  // Start the game thread to begin gameplay
    }

    /**
     * The main method, which serves as the entry point for the FreeBomber game.
     * It creates an instance of FreeBomber to launch the game.
     *
     * @param args Command line arguments (not used in this case).
     */
    public static void main(String[] args) {
        FreeBomber freeBomber = new FreeBomber();  // Launch the FreeBomber game
    }
}
