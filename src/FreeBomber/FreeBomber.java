package FreeBomber;

import Background.Background;
import Background.GameOverPanel;
import Background.HighscorePanel;
import Background.MenuPanel;

import javax.swing.*;

/**
 * The FreeBomber class represents the main window for the FreeBomber game.
 * It initializes the JFrame, displays the menu panel, and handles switching to the game.
 */
public class FreeBomber extends JFrame {

    // game background class
    private Background gamePanel;
    // score board instance for game
    private HighscorePanel scoreBoard;

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
        //setContentPane(new GameOverPanel(this)); // Uncomment this if you want to show GameOverPanel initially
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Main method to launch the FreeBomber game.
     * This method starts the FreeBomber application by initializing the game window.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(FreeBomber::new);
    }

    /**
     * Switches from the menu panel to the actual game panel and starts the game thread.
     * This method changes the content pane of the JFrame to the game panel and initializes the game.
     */
    public void startGame() {
        gamePanel = new Background(this);
        setContentPane(gamePanel);
        revalidate();  // Re-layout the frame with the new content
        repaint();

        // Ensure key input works
        SwingUtilities.invokeLater(() -> {
            gamePanel.requestFocusInWindow();
        });

        gamePanel.startGameThread();
    }

    /**
     * Displays the high score board panel.
     * This method switches the content pane to the high score panel and allows users to view the high scores.
     */
    public void showScoreBoard() {
        scoreBoard = new HighscorePanel(this);
        setContentPane(scoreBoard);
        revalidate();
        repaint();

        // Ensure key input works
        SwingUtilities.invokeLater(() -> {
            scoreBoard.requestFocusInWindow();
        });
    }

    /**
     * Displays the game over panel.
     * This method switches the content pane to the game over panel to show the game results.
     */
    public void showGameOver() {
        setContentPane(new GameOverPanel(this));
        revalidate();
        repaint();
    }

    /**
     * Switches the content pane back to the main menu.
     * This method takes the user back to the menu screen, where they can choose to restart or exit the game.
     */
    public void getBackToMenu() {
        setContentPane(new MenuPanel(this));
        revalidate();
        repaint();
    }
}
