package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 * <p>
 * A JPanel that displays the top 10 high scores in a scrollable table.
 * Includes functionality to read, store, and update score data from a file.
 * It also provides a button to return to the main menu.
 * </p>
 *
 * @author mguzelocak
 * @author Zelele
 */
public class HighscorePanel extends JPanel implements ActionListener {

    /** Reference to a background object for screen dimension access. */
    private Background backg = new Background();

    /** Button used to return from the high score panel to the main menu. */
    private JButton backButton;

    /** JTable component used to visually display the scoreboard. */
    private JTable scoreTable;

    /** Table model backing the scoreTable, used to store and update score rows. */
    private DefaultTableModel tableModel;

    /** Internal storage of scoreboard entries mapped from player name to score. */
    private Map<String, Integer> scoreboardData = new HashMap<>();

    /**
     * Constructs the HighscorePanel and sets up its layout, visuals, score table, and navigation button.
     * This panel is rendered over the main game frame and includes a button to return to the main menu.
     *
     * @param frame the main game frame to allow returning to the menu
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
            /**
             * Determines if a cell in the score table is editable.
             * This implementation always returns false, making all cells read-only.
             *
             * @param row the row index of the cell
             * @param column the column index of the cell
             * @return false, indicating the cell is not editable
             */
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
                new InputStreamReader(getClass().getClassLoader().getResourceAsStream("storage/scores/scoreboard.txt")))
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
            System.out.println("Failed to load scoreboard");
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
     * @param score      The new score to consider for the player.
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
            System.out.println("Failed to write top 10 scores");
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
     * Handles button actions for the panel. Currently unused.
     *
     * @param e the action event triggered by a UI interaction
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // No action handling implemented here
    }
}
