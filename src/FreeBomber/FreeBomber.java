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

    /** game background class */
    private Background gamePanel;
    /** score board instance for game */
    private HighscorePanel scoreBoard;
    /** players name instance */
    private String playerName;
    /** game over panel class */
    private final GameOverPanel gameOverPanel = new GameOverPanel(this);
    /** high score panel class */
    private final HighscorePanel highscorePanel = new HighscorePanel(this);
    /** menu panel class */
    private final MenuPanel menuPanel = new MenuPanel(this);
    /** game background class */
    private final Background background = new Background(this);
    /** players score instance */
    private int playerScore;


    /**
     * Constructor for the FreeBomber class.
     * Sets up the JFrame and shows the menu panel first.
     */
    public FreeBomber() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("FreeBomber");
        setPlayerName(menuPanel.getName());

        // Show menu panel first
        setContentPane(menuPanel);
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
        setContentPane(background);
        revalidate();  // Re-layout the frame with the new content
        repaint();

        // Ensure key input works
        SwingUtilities.invokeLater(background::requestFocusInWindow);

        background.startGameThread();
    }

    /**
     * Displays the high score board panel.
     * This method switches the content pane to the high score panel and allows users to view the high scores.
     */
    public void showScoreBoard() {
        highscorePanel.setScoreboardData(highscorePanel.readAndStore());
        highscorePanel.refreshScoreboard(this.playerName, this.playerScore);
        setContentPane(highscorePanel);
        revalidate();
        repaint();

        // Ensure key input works
        SwingUtilities.invokeLater(highscorePanel::requestFocusInWindow);
    }

    /**
     * Displays the game over panel.
     * This method switches the content pane to the game over panel to show the game results.
     */
    public void showGameOver() {
        setContentPane(gameOverPanel);
        revalidate();
        repaint();
        // Ensure key input works
        SwingUtilities.invokeLater(gameOverPanel::requestFocusInWindow);
    }

    /**
     * Switches the content pane back to the main menu.
     * This method takes the user back to the menu screen, where they can choose to restart or exit the game.
     */
    public void getBackToMenu() {
        setContentPane(menuPanel);
        revalidate();
        repaint();
        // Ensure key input works
        SwingUtilities.invokeLater(menuPanel::requestFocusInWindow);
    }
    /**
     * Sets the player's name.
     *
     * @param name The name to assign to the player.
     */
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    /**
     * Retrieves the player's name.
     *
     * @return The current name of the player.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Sets the player's score.
     *
     * @param playerScore The score to assign to the player.
     */
    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

}
