package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * HighscorePanel is a JPanel that displays a list of high scores from a file
 * and includes a back button to return to the main menu.
 */
public class HighscorePanel extends JPanel implements ActionListener {

    /** Stores screen background and dimension utilities */
    private Background backg = new Background();

    /** Back button to return to the main menu */
    private JButton backButton;

    /** A map to store the scoreboard entries with player names and their scores */
    private Map<String, Integer> scoreboardData = new HashMap<>();

    /**
     * Constructs the HighscorePanel.
     * Sets up layout, background, back button, and loads scoreboard data.
     *
     * @param frame The main FreeBomber game frame
     */
    public HighscorePanel(FreeBomber frame) {
        // Set panel size to match screen dimensions
        setPreferredSize(new Dimension(backg.getScreenWidth(), backg.getScreenHeight()));

        // Use null layout for absolute positioning
        setLayout(null);

        // Set panel background color
        setBackground(Color.DARK_GRAY);

        // Create and customize back button
        backButton = new JButton("Back") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Change color when pressed
                Color base = getModel().isArmed() ? new Color(150, 150, 150) : new Color(255, 0, 0);
                g2.setColor(base);

                // Draw rounded rectangle background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                // Call default button painting
                super.paintComponent(g);
            }
        };

        // Position and style the back button
        backButton.setBounds(500, 500, 100, 50);
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setFocusPainted(false);
        backButton.setOpaque(false);

        // Add action to return to menu on click
        backButton.addActionListener((ActionEvent e) -> {
            frame.getBackToMenu();
        });

        // Add the button to the panel
        add(backButton);

        // Make the panel visible
        setVisible(true);

        // Load high score data from file
        readAndStore();
    }

    /**
     * Reads scoreboard data from a file and stores it in the scoreboardData map.
     * Handles parsing and file access errors safely.
     */
    public void readAndStore() {
        // Temporary map to load values before storing
        Map<String, Integer> tempDictionary = new HashMap<>();

        try {
            // Load the file using classloader for compatibility inside JARs
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getClass().getClassLoader().getResourceAsStream("storage/scores/scoreboard.txt"))
            );

            String line;

            // Read each line and parse name, score
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(",");
                if (words.length == 2) {
                    try {
                        tempDictionary.put(words[0].trim(), Integer.parseInt(words[1].trim()));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format in line: " + line);
                    }
                }
            }

            // Close the reader after reading
            reader.close();

            // Update the main scoreboard map
            this.scoreboardData = tempDictionary;

        } catch (Exception e) {
            System.out.println("Failed to load scoreboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Paints the high score panel, including the title and top 10 scores.
     *
     * @param g The graphics object used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        // Always call super first for proper Swing painting
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Enable antialiasing for smoother text
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the title
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        g2.drawString("HIGH SCORES", 100, 100);

        // Set font for scores
        g2.setFont(new Font("Arial", Font.PLAIN, 24));

        // Vertical position tracker
        AtomicInteger y = new AtomicInteger(150);

        // Display top 10 scores in descending order
        scoreboardData.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .forEach(entry -> {
                    g2.drawString(entry.getKey() + " - " + entry.getValue(), 120, y.get());
                    y.addAndGet(35);
                });
    }

    /**
     * Unused ActionListener method.
     * Placeholder for potential future event handling.
     *
     * @param e The ActionEvent triggered
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // No action handling implemented here
    }
}
