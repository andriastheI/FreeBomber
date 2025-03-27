package FreeBomber;

import Background.Background;

import javax.swing.*;

/**
 * The FreeBomber class represents the main window for the FreeBomber game.
 * It initializes the JFrame, sets properties, and starts the game thread.
 */
public class FreeBomber extends JFrame {

    /**
     * Constructor for the FreeBomber class.
     * Sets up the JFrame, adds the game panel (Background), and starts the game thread.
     */
    public FreeBomber() {
        // Set default close operation
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Disable resizing of the window
        setResizable(false);
        // Set the window title
        setTitle("FreeBomber");
        // Create a new game panel
        Background gamePanel = new Background();
        // Add the game panel to the JFrame
        add(gamePanel);
        // Adjust the window size to fit the panel
        pack();
        // Center the window on the screen
        setLocationRelativeTo(null);
        // Make the window visible
        setVisible(true);
        // Start the game thread to begin gameplay
        gamePanel.startGameThread();
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
