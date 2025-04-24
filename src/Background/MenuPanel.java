package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Description:
 * <p>
 * The MenuPanel class represents the main menu screen of the game.
 * It provides options to start a new game, view high scores, or exit the application.
 * It also handles user input for entering a username before launching the game.
 * </p>
 *
 * @author mguzelocak
 * @author Zelele
 */
public class MenuPanel extends JPanel implements ActionListener {

    /** Button to initiate a new game session after username input. */
    private JButton newGameButton;

    /** Button that navigates to the high score display panel. */
    private JButton highScoreButton;

    /** Button to exit the game application. */
    private JButton exitButton;

    /** Button to for displaying the rules*/
    private JButton ruleButton;

    /** Background instance used to get screen dimension settings. */
    private Background bg = new Background();

    /** Stores the username entered by the player. */
    private String currentUsername = "";

    /**
     * Constructs the menu panel UI with background and buttons.
     *
     * @param frame Reference to the FreeBomber frame for event callbacks
     */
    public MenuPanel(FreeBomber frame) {
        // Set panel size to match screen dimensions
        setPreferredSize(new Dimension(bg.getScreenWidth(), bg.getScreenHeight()));

        // Use absolute positioning
        setLayout(null);

        // Set game icon
        frame.setIconImage(new ImageIcon("src/storage/logo/logo.png").getImage());

        // Load and set background image
        ImageIcon menuBg = new ImageIcon("src/storage/logo/menuBomber.jpg");
        JLabel bg = new JLabel(menuBg);
        bg.setBounds(0, 0, menuBg.getIconWidth() - 150, menuBg.getIconHeight() - 50);

        // ============================
        // Setup "New Game" button
        // ============================
        newGameButton = new JButton("Start Game") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Change color on press
                Color base = getModel().isArmed() ? new Color(30, 30, 30) : new Color(50, 50, 200);
                g2.setColor(base);

                // Draw rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                // Default painting
                super.paintComponent(g);
            }
        };

        // Style and position the button
        newGameButton.setBounds(314, 300, 150, 50);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        newGameButton.setContentAreaFilled(false);
        newGameButton.setBorderPainted(false);
        newGameButton.setFocusPainted(false);
        newGameButton.setOpaque(false);

        // Add event handler to start the game
        // Add event handler to start the game
        newGameButton.addActionListener((ActionEvent e) -> {
            String promptMessage = "Enter a username (3–15 characters, letters and numbers only):";
            currentUsername = JOptionPane.showInputDialog(this, promptMessage, "Username", JOptionPane.PLAIN_MESSAGE);

            if (currentUsername != null) {
                if (currentUsername.matches("[a-zA-Z0-9]{3,15}")) {
                    frame.setPlayerName(currentUsername);  // assuming you have this method in FreeBomber
                    frame.startGame();
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Invalid username.\nUsername must be 3–15 characters and contain only letters and numbers.",
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Username is required to start the game.");
            }
        });


        // Add button to panel
        add(newGameButton);

        // ============================
        // Setup "High Scores" button
        // ============================
        highScoreButton = new JButton("High Scores") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Change color on press
                Color base = getModel().isArmed() ? new Color(30, 30, 30) : new Color(50, 50, 200);
                g2.setColor(base);

                // Draw rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                // Default painting
                super.paintComponent(g);
            }
        };

        // Style and position the button
        highScoreButton.setBounds(290, 360, 200, 50);
        highScoreButton.setForeground(Color.WHITE);
        highScoreButton.setFont(new Font("Arial", Font.BOLD, 18));
        highScoreButton.setContentAreaFilled(false);
        highScoreButton.setBorderPainted(false);
        highScoreButton.setFocusPainted(false);
        highScoreButton.setOpaque(false);

        // Add button to panel
        add(highScoreButton);

        // Initialize the "Rules" button
        ruleButton = new JButton("Rules") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Change background color on press
                Color base = getModel().isArmed() ? new Color(150, 150, 150) : new Color(50, 50, 200);
                g2.setColor(base);

                // Draw rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        ruleButton.setBounds(314, 420, 150, 50); // Set button size
        ruleButton.setForeground(Color.WHITE);
        ruleButton.setFont(new Font("Arial", Font.BOLD, 18));
        ruleButton.setContentAreaFilled(false);  // Prevent default painting
        ruleButton.setBorderPainted(false);      // Remove border
        ruleButton.setFocusPainted(false);       // No outline on focus
        ruleButton.setOpaque(false);
        ruleButton.addActionListener((ActionEvent e) -> {
            frame.showRules();
        });
        add(ruleButton);



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
        exitButton.setBounds(314, 480, 150, 50); // Set button size
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setContentAreaFilled(false);  // Prevent default painting
        exitButton.setBorderPainted(false);      // Remove border
        exitButton.setFocusPainted(false);       // No outline on focus
        exitButton.setOpaque(false);
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0); // Exit the game
        });
        add(exitButton);
        // Add background image at the end to appear behind other components
        add(bg);

        // Add event handler to show scoreboard
        highScoreButton.addActionListener((ActionEvent e) -> {
            frame.showScoreBoard();
        });
    }

    /**
     * Unused ActionListener method.
     * Placeholder for future action support if needed.
     *
     * @param e The triggered ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // No logic implemented
    }
}
