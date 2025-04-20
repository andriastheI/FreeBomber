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
        ImageIcon overBg = new ImageIcon("src/storage/logo/victory.png");
        JLabel bg = new JLabel(overBg);
        bg.setBounds(0, 0, overBg.getIconWidth() - 180, overBg.getIconHeight() - 80);

        // Initialize the "New Game" button
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
