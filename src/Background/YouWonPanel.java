package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class YouWonPanel extends JPanel implements ActionListener {

    /** Starts a completely new game session */
    private JButton menuGameButton;
    /** Reference background used to fetch screen dimensions */
    private Background backg = new Background();

    /**
     * Constructs the GameOverPanel, initializes buttons, and sets up the game-over screen layout.
     *
     * @param frame the main game frame where the game logic is managed.
     */
    public YouWonPanel(FreeBomber frame) {
        setPreferredSize(new Dimension(backg.getScreenWidth(), backg.getScreenHeight()));
        setLayout(null);

        // Create background image for game over screen
        ImageIcon originalIcon = new ImageIcon("src/storage/logo/victory.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(backg.getScreenWidth(), backg.getScreenHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel bg = new JLabel(scaledIcon);
        bg.setBounds(0, 0,backg.getScreenWidth(), backg.getScreenHeight());

        String playerName = frame.getPlayerName();
        int playerScore = frame.getPlayerScore();

        // Name label
        JLabel nameLabel = new JLabel("Congratulations, " + playerName + "!");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 28));
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(170, 10, 500, 40); // Adjust X/Y as needed
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Score label
        JLabel scoreLabel = new JLabel("Your Score: " + playerScore);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.YELLOW);
        scoreLabel.setBounds(160, 590, 400, 35); // Adjust as needed
        scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Add labels before background
        add(nameLabel);
        add(scoreLabel);

        menuGameButton = new JButton("Menu") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Change background color on press
                Color base = getModel().isArmed() ? new Color(30, 30, 30) : new Color(0, 200, 0);
                g2.setColor(base);

                // Draw rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        menuGameButton.setBounds(660, 580, 150, 50); // Set button size
        menuGameButton.setForeground(Color.WHITE);
        menuGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        menuGameButton.setContentAreaFilled(false);  // Prevent default painting
        menuGameButton.setBorderPainted(false);      // Remove border
        menuGameButton.setFocusPainted(false);       // No outline on focus
        menuGameButton.setOpaque(false);             // Transparency
        menuGameButton.addActionListener(e -> frame.getBackToMenu());

        add(menuGameButton);
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
