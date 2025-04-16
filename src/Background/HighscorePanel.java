package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Background.ScoreManager;

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



        // Create a table model with non-editable cells
        tableModel = new DefaultTableModel(new Object[]{"Player", "Score"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // All cells are non-editable
            }
        };
        scoreTable = new JTable(tableModel);
        // Table formatting
        scoreTable.setFont(new Font("Arial", Font.PLAIN, 20));
        scoreTable.setRowHeight(30);
        scoreTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 22));

        // Wrap the table in a scroll pane
        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setBounds(100, 120, 400, 350);
        add(scrollPane);
        this.scoreboardData = ScoreManager.getTopScores(10);
        refreshScoreboard(null, 0);

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
     * Reads scoreboard data from a comma-separated file.
     * Username is everything before the last comma, score is after.
     */
    public Map<String, Integer> readAndStore() {
        Map<String, Integer> temp = new HashMap<>();
        try {
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/freebomber", "root", "your_password_here"
            );
            String query = "SELECT username, MAX(score) AS score FROM scores GROUP BY username ORDER BY score DESC LIMIT 10";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                temp.put(rs.getString("username"), rs.getInt("score"));
            }

            conn.close();
        } catch (Exception e) {
            System.out.println("DB read error: " + e.getMessage());
        }
        return temp;
    }
    /**
     * Refreshes the scoreboard with a new score for the given player.
     * Saves the score to the database, then retrieves the top 10 from the database
     * and displays them in the table.
     *
     * @param playerName The name of the player whose score is being updated.
     * @param score      The new score to consider for the player.
     */
    public void refreshScoreboard(String playerName, int score) {
        if (playerName != null) {
            ScoreManager.saveScore(playerName, score);
        }

        // Load the top 10 from database
        scoreboardData = ScoreManager.getTopScores(10);

        // Update the table
        tableModel.setRowCount(0); // Clear previous entries
        scoreboardData.forEach((name, val) -> tableModel.addRow(new Object[]{name, val}));
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
