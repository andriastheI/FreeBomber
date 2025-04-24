package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * RulePanel is a custom JPanel that displays the game rules
 * for the Jack Bomber game. It includes a scrollable text area
 * showing gameplay instructions and a "Back" button to return
 * to the main menu.
 *
 * <p>This panel is styled with a custom color scheme and layout,
 * making it user-friendly for players to read and understand
 * how to play the game.</p>
 *
 * @author Andrias Zelele
 */
public class RulePanel extends JPanel implements ActionListener {

    /** Background object to get screen dimensions. */
    private Background backg = new Background();

    /** Button to return from the rules panel to the main menu. */
    private JButton backButton;

    /** Map for scoreboard data; not used in this panel currently. */
    private Map<String, Integer> scoreboardData = new HashMap<>();

    /** Text area for displaying game rules to the player. */
    private JTextArea rulesTextArea;

    /**
     * Constructs the RulePanel, which provides instructions
     * for the player. Sets up layout, back button, and game rules area.
     *
     * @param frame the main game frame, used to return to the menu
     */
    public RulePanel(FreeBomber frame) {
        // Set the size of the panel
        setPreferredSize(new Dimension(backg.getScreenWidth(), backg.getScreenHeight()));
        setLayout(null);
        setBackground(Color.WHITE);

        // Initialize and style the back button
        backButton = new JButton("Back") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Color base = getModel().isArmed() ? new Color(150, 150, 150) : new Color(255, 0, 0);
                g2.setColor(base);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        backButton.setBounds(680, 570, 100, 50);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);
        backButton.addActionListener((ActionEvent e) -> frame.getBackToMenu());
        add(backButton);

        // Set up the game rules text area
        rulesTextArea = new JTextArea();
        rulesTextArea.setText("""
                Welcome to Jack Bomber!
                "Find your way home"
                
                1. Use 'w' for up direction, 's' for down direction, 'd' for right direction and 'a' for left direction player movement.
                2. Press SPACE to plant a bomb.
                3. Bombs explode after a few seconds, destroying enemies and destructible blocks.
                4. Avoid standing in the blast radius of your own bombs! or else you will lose a health level.
                5. You have 180 seconds to finish each level. Every 60 seconds you will lose a health level.
                6. There is an exit door hidden in one of the blocks. Your goal is to find it and earn as much as points as you can.
                7. Enemies are worth 150 points, blocks are worth 50 points and 400 points for using the exit door when leveling up,
                but watch out for enemies, if you are in contact with one you will lose a health level. If you lose a health level
                its -300 from your current score.
                
                Watch your health! Lose all and it’s game over.
                Good luck, and blow things up wisely!""");
        rulesTextArea.setFont(new Font("Monospaced", Font.PLAIN, 16));
        rulesTextArea.setEditable(false);
        rulesTextArea.setWrapStyleWord(true);
        rulesTextArea.setLineWrap(true);
        rulesTextArea.setMargin(new Insets(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(rulesTextArea);
        scrollPane.setBounds(50, 100, 750, 450);
        add(scrollPane);

        setVisible(true);
    }

    /**
     * Paints the panel's title text "Game Rules:" on the screen.
     *
     * @param g The graphics context used for drawing
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(15, 33, 0));
        g2.setFont(new Font("Georgia", Font.PLAIN, 40));
        g2.drawString("Game Rules:", 40, 80);
    }

    /**
     * Handles action events for the panel.
     * Currently not used since the "Back" button
     * uses a lambda expression directly.
     *
     * @param e the action event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // No additional actions needed
    }
}
