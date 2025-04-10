package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The GameOverPanel class represents the game-over screen where the player can choose to restart the game,
 * start a new game, or exit the application.
 */
public class GameOverPanel extends JPanel implements ActionListener {

    // Restart button that resumes the game from current state
    private JButton restartGameButton;
    // Starts a completely new game session
    private JButton newGameButton;
    // Closes the game/application entirely
    private JButton exitButton;
    // Reference background used to fetch screen dimensions
    private Background backg = new Background();

    /**
     * Constructs the GameOverPanel, initializes buttons, and sets up the game-over screen layout.
     *
     * @param frame the main game frame where the game logic is managed.
     */
    public GameOverPanel(FreeBomber frame) {
        setPreferredSize(new Dimension(backg.getScreenWidth(), backg.getScreenHeight()));
        setLayout(null);

        // Create background image for game over screen
        ImageIcon overBg = new ImageIcon("src/storage/logo/gameover.jpg");
        JLabel bg = new JLabel(overBg);
        bg.setBounds(0, 0, overBg.getIconWidth() - 180, overBg.getIconHeight() - 80);

        // Initialize the "Restart Game" button
        restartGameButton = new JButton("Restart Game") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Change background color on press
                Color base = getModel().isArmed() ? new Color(30, 30, 30) : new Color(50, 50, 200);
                g2.setColor(base);

                // Draw rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        restartGameButton.setBounds(320, 380, 180, 50); // Set button size
        restartGameButton.setForeground(Color.WHITE);
        restartGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartGameButton.setContentAreaFilled(false);  // Prevent default painting
        restartGameButton.setBorderPainted(false);      // Remove border
        restartGameButton.setFocusPainted(false);       // No outline on focus
        restartGameButton.setOpaque(false);             // Transparency
        restartGameButton.addActionListener((ActionEvent e) -> {
            frame.startGame(); // Start the game from FreeBomber
        });

        // Initialize the "New Game" button
        newGameButton = new JButton("New Game") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Change background color on press
                Color base = getModel().isArmed() ? new Color(30, 30, 30) : new Color(50, 50, 200);
                g2.setColor(base);

                // Draw rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        newGameButton.setBounds(340, 440, 150, 50); // Set button size
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        newGameButton.setContentAreaFilled(false);  // Prevent default painting
        newGameButton.setBorderPainted(false);      // Remove border
        newGameButton.setFocusPainted(false);       // No outline on focus
        newGameButton.setOpaque(false);             // Transparency
        newGameButton.addActionListener(e -> frame.startGame());

        // Initialize the "Exit" button
        exitButton = new JButton("Exit") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Change background color on press
                Color base = getModel().isArmed() ? new Color(150, 150, 150) : new Color(255, 0, 0);
                g2.setColor(base);

                // Draw rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        exitButton.setBounds(355, 500, 120, 50); // Set button size
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setContentAreaFilled(false);  // Prevent default painting
        exitButton.setBorderPainted(false);      // Remove border
        exitButton.setFocusPainted(false);       // No outline on focus
        exitButton.setOpaque(false);
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0); // Exit the game
        });

        // Add components to panel
        add(restartGameButton);
        add(newGameButton);
        add(exitButton);
        add(bg);
    }

    /**
     * Empty action performed method, implemented from ActionListener interface.
     * This method is not used in this class as the button actions are handled separately for each button.
     *
     * @param e the ActionEvent triggered by a button.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Empty method body.
    }
}
