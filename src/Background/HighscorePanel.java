package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
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

        // Create table with columns: "Player" and "Score"
        tableModel = new DefaultTableModel(new Object[]{"Player", "Score"}, 0);
        scoreTable = new JTable(tableModel);

        // Populate the table with top 10 scores
        scoreboardData.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .limit(10)
                .forEach(entry -> tableModel.addRow(new Object[]{entry.getKey(), entry.getValue()}));

        // Table formatting
        scoreTable.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreTable.setRowHeight(30);
        scoreTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 22));

        // Wrap the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setBounds(100, 120, 400, 350);
        add(scrollPane);

    }

    /**
     * Reads scoreboard data from a comma-separated file.
     * Username is everything before the last comma, score is after.
     */
    public void readAndStore() {
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
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Title
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        g2.drawString("HIGH SCORES", 100, 80);
    }

    /**
     * Stores the top 10 high scores into the scoreboard file as comma-separated values.
     */
    public void storeTop10Scores() {
        try {
            java.io.File file = new java.io.File("src/main/resources/storage/scores/scoreboard.txt");
            java.io.PrintWriter writer = new java.io.PrintWriter(file);

            scoreboardData.entrySet().stream()
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
