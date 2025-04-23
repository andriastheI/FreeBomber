package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description:
 * <p>
 * The GameOverPanel class represents the game-over screen where the player can choose to restart the game,
 * start a new game, or exit the application.
 * </p>
 *
 * @author Zelele
 * @author mguzelocak
 */
public class GameOverPanel extends JPanel implements ActionListener {

    /** Button that allows the player to restart the game from the beginning. */
    private JButton restartGameButton;

    /** Button that returns the player to the main menu screen. */
    private JButton menuGameButton;

    /** Temporary background instance used to fetch screen size for panel layout. */
    private Background backg = new Background();

    /**
     * Constructs the GameOverPanel, including layout configuration and visual design.
     * Initializes buttons for restarting the game or returning to the main menu.
     *
     * @param frame the main FreeBomber game frame that manages transitions and state.
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
            frame.setPlayerScore(0);
            frame.startGame(); // Start the game from FreeBomber
        });

        // Initialize the "Menu" button
        menuGameButton = new JButton("Menu") {
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
        menuGameButton.setBounds(340, 440, 150, 50); // Set button size
        menuGameButton.setForeground(Color.WHITE);
        menuGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        menuGameButton.setContentAreaFilled(false);  // Prevent default painting
        menuGameButton.setBorderPainted(false);      // Remove border
        menuGameButton.setFocusPainted(false);       // No outline on focus
        menuGameButton.setOpaque(false);             // Transparency
        menuGameButton.addActionListener(e -> frame.getBackToMenu());

        // Add components to panel
        add(restartGameButton);
        add(menuGameButton);
        add(bg);
    }

    /**
     * Unused method required by ActionListener. All button actions are handled via lambda expressions.
     *
     * @param e the action event, not used in this context.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Empty method body.
    }
}
