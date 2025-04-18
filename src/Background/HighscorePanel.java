package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * HighscorePanel is a JPanel that displays a list of high scores from a file
 * and includes a back button to return to the main menu.
 */
public class HighscorePanel extends JPanel implements ActionListener {

    /** Stores screen background and dimension utilities */
    private Background backg = new Background();

    /** Back button to return to the main menu */
    private JButton backButton;

    /** Table to display the score board */
    private JTable scoreTable;

    /** Table to list the score board */
    private DefaultTableModel tableModel;

    /** A map to store the scoreboard entries with player names and their scores */
    private Map<String, Integer> scoreboardData = new HashMap<>();

    /**
     * Constructs the High score Panel.
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
        setBackground(Color.LIGHT_GRAY);

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
        backButton.setBounds(680, 570, 100, 50);
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

        // Create a table model with non-editable cells
        tableModel = new DefaultTableModel(new Object[]{"Player", "Score"}, 0) {
            // All cells are non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        scoreTable = new JTable(tableModel);
        // Table formatting
        scoreTable.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreTable.setRowHeight(30);
        scoreTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 22));

        // Wrap the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setBounds(230, 120, 400, 350);
        add(scrollPane);

    }
    /**
     * Paints the high score panel, including the title and top 10 scores.
     *
     * @param g The graphics object used for rendering
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Title
        g2.setColor(new Color(0, 0, 139));
        g2.setFont(new Font("Georgia", Font.PLAIN, 60));
        g2.drawString("High Scores", 90, 80);
    }
    /**
     * Reads scoreboard data from a comma-separated file.
     * Username is everything before the last comma, score is after.
     */
    public Map<String, Integer> readAndStore() {
        Map<String, Integer> tempDictionary = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("src/storage/scores/scoreboard.txt")))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                int lastCommaIndex = line.lastIndexOf(',');

                if (lastCommaIndex != -1 && lastCommaIndex < line.length() - 1) {
                    String username = line.substring(0, lastCommaIndex).trim();
                    String scoreStr = line.substring(lastCommaIndex + 1).trim();

                    try {
                        int score = Integer.parseInt(scoreStr);
                        tempDictionary.put(username, score);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid score format in line: " + line);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Failed to load scoreboard: " + e.getMessage());
            e.printStackTrace();
        }
        return tempDictionary;
    }
    /**
     * Refreshes the scoreboard with a new score for the given player.
     * <p>
     * If the player already exists in the scoreboard, updates their score only if the new score is higher.
     * The scoreboard is then trimmed to the top 10 scores, stored in a file, and displayed in the table model.
     *
     * @param playerName The name of the player whose score is being updated.
     * @param score The new score to consider for the player.
     */
    public void refreshScoreboard(String playerName, int score) {
        // Only proceed if valid playerName and score > 0
        if (playerName != null && score > 0) {
            // Merge only if score is positive and better than existing
            scoreboardData.merge(playerName, score, Math::max);

            // Sort and retain top 10
            scoreboardData = scoreboardData.entrySet().stream()
                    .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                    .limit(10)
                    .collect(java.util.stream.Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue,
                            (e1, e2) -> e1,
                            java.util.LinkedHashMap::new
                    ));

            storeTop10Scores(scoreboardData);  // Save updated scores
        }

        // Clear table and re-populate with updated top 10
        tableModel.setRowCount(0);
        scoreboardData.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .forEach(entry -> tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()}));
    }

    /**
     * Stores the top 10 high scores from the provided map into the scoreboard file as comma-separated values.
     * The file is overwritten with the top 10 entries based on highest scores.
     *
     * @param newScores a map of usernames and their scores to be saved.
     */
    public void storeTop10Scores(Map<String, Integer> newScores) {
        try {
            java.io.File file = new java.io.File("src/storage/scores/scoreboard.txt");
            java.io.PrintWriter writer = new java.io.PrintWriter(file);

            newScores.entrySet().stream()
                    .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                    .limit(10)
                    .forEach(entry -> writer.println(entry.getKey() + "," + entry.getValue()));

            writer.close();
        } catch (Exception e) {
            System.out.println("Failed to write top 10 scores: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Sets the scoreboard data with the provided map of player names and scores.
     *
     * @param scoreboardData a map containing player names as keys and their corresponding scores as values
     */
    public void setScoreboardData(Map<String, Integer> scoreboardData) {
        this.scoreboardData = scoreboardData;
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
