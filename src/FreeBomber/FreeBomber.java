package FreeBomber;

import Background.*;

import javax.swing.*;
import java.awt.*;

/**
 * Description:
 * <p>
 * The FreeBomber class serves as the main entry point and window frame for the FreeBomber game.
 * It initializes the game UI, manages transitions between game states (menu, gameplay, scoreboard, victory, and game over),
 * and facilitates player interactions including score tracking and map loading.
 * </p>
 * <p>
 * Features:
 * - Launches the game window and menu on startup
 * - Allows cheat-based map initialization via command-line arguments
 * - Dynamically switches between different UI panels during gameplay
 * - Integrates with the game engine through the Background class
 * - Manages player name and score
 *
 * @author @mguzelocak @zelele
 */
public class FreeBomber extends JFrame {

    /** The player's name displayed in high scores and menus. */
    private String playerName;

    /** Reference to the panel shown when the player loses the game. */
    private GameOverPanel gameOverPanel;

    /** Reference to the high score display panel. */
    private HighscorePanel highscorePanel;

    /** Reference to the main menu panel. */
    private MenuPanel menuPanel;

    /** Reference to the Rule Panel */
    private RulePanel rulePanel;

    /** Reference to the active game canvas (Background). */
    private Background background;

    /** Panel shown upon player victory. */
    private YouWonPanel victory;

    /** Stores the current game score for the player. */
    private int playerScore;

    /** The map number to start the game from. Used for cheat mode or custom level starts. */
    private int startingMap = 1;

    /**
     * Constructs the FreeBomber game window and initializes the starting map.
     *
     * @param startingMap The map number to start the game from. Used for cheat mode or custom level starts.
     */
    public FreeBomber(int startingMap) {
        this.startingMap = startingMap;
        menuPanel = new MenuPanel(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("FreeBomber");
        setPlayerName(menuPanel.getName());
        setContentPane(menuPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Main method to launch the FreeBomber game.
     * This method starts the FreeBomber application by initializing the game window.
     *
     * @param args Command-line arguments. Use "nwilliams" to start from level 5 in cheat mode.
     */
    public static void main(String[] args) {
        int map = 1;
        if (args.length > 0 && args[0].equalsIgnoreCase("nwilliams")) {
            map = 5;
        }
        int finalMap = map;
        SwingUtilities.invokeLater(() -> new FreeBomber(finalMap));
    }

    /**
     * Switches from the menu panel to the actual game panel and starts the game thread.
     * This method changes the content pane of the JFrame to the game panel and initializes the game.
     */
    public void startGame() {
        highscorePanel = new HighscorePanel(this);
        highscorePanel.setScoreboardData(highscorePanel.readAndStore());
        highscorePanel.refreshScoreboard(this.playerName, this.playerScore);

        background = new Background(this, startingMap);
        background.getPlayer().setScore(0);

        // Create a layered pane to hold both game and button
        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(background.getScreenWidth(), background.getScreenHeight())); // match your screen size

        // Set bounds for the game panel
        background.setBounds(0, 0, background.getScreenWidth(), background.getScreenHeight());
        layeredPane.add(background, Integer.valueOf(0)); // Game layer

        // Create button overlay panel
        JPanel overlay = new JPanel(null); // null layout for manual positioning
        overlay.setOpaque(false); // transparent
        overlay.setBounds(0, 0, background.getScreenWidth(), background.getScreenHeight());

        JButton exitButton = new JButton("Exit");
        exitButton.setForeground(Color.RED);
        exitButton.setFont(new Font("Georgia", Font.BOLD, 15));
        exitButton.setBounds(720, 605, 80, 30); // top right corner
        exitButton.addActionListener(e -> getBackToMenu());

        overlay.add(exitButton);
        layeredPane.add(overlay, Integer.valueOf(1)); // UI layer

        setContentPane(layeredPane);
        revalidate();
        repaint();

        SwingUtilities.invokeLater(background::requestFocusInWindow);
        background.startGameThread();
    }


    /**
     * Displays the high score board panel.
     * This method switches the content pane to the high score panel and allows users to view the high scores.
     */
    public void showScoreBoard() {
        highscorePanel = new HighscorePanel(this);
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
        gameOverPanel = new GameOverPanel(this);
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
     * Displays the YouWon panel.
     * This method switches the background panel to the YouWon panel to show that the player finished the game.
     */
    public void showVictory() {
        victory = new YouWonPanel(this);
        setContentPane(victory);
        revalidate();
        repaint();

        SwingUtilities.invokeLater(victory::requestFocusInWindow);
    }

    /**
     * Switches the screen to show the game rules panel.
     * Replaces the current content with the RulePanel and refreshes the view.
     */
    public void showRules() {
        rulePanel = new RulePanel(this);
        setContentPane(rulePanel);
        revalidate();
        repaint();

        SwingUtilities.invokeLater(rulePanel::requestFocusInWindow);
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
     * Sets the player's name.
     *
     * @param name The name to assign to the player.
     */
    public void setPlayerName(String name) {
        this.playerName = name;
    }

    /**
     * Returns the current player's score.
     *
     * @return The player's score as an integer.
     */
    public int getPlayerScore() {
        return this.playerScore;
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
