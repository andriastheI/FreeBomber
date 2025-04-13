package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The MenuPanel class represents the main menu screen of the game.
 * It provides options to start a new game or view high scores.
 */
public class MenuPanel extends JPanel implements ActionListener {

    /** Button to start a new game */
    private JButton newGameButton;
    /** Button to view high scores */
    private JButton highScoreButton;
    /** Background object for screen dimension info */
    private Background bg = new Background();
    /** Temporary scoreboard map (not used in display in current version) */
    private Map<String, Integer> scoreBoardUpload = new HashMap<>();
    /** used for exracting the username for the player*/
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
        newGameButton = new JButton("New Game") {
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
        newGameButton.addActionListener((ActionEvent e) -> {
            currentUsername = JOptionPane.showInputDialog(this, "Enter your username:", "Username", JOptionPane.PLAIN_MESSAGE);

            if (currentUsername != null && !currentUsername.trim().isEmpty()) {
                frame.setPlayerName(currentUsername.trim());  // assuming you have this method in FreeBomber
                frame.startGame();
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

        // Add background image at the end to appear behind other components
        add(bg);

        // Add event handler to show scoreboard
        highScoreButton.addActionListener((ActionEvent e) -> {
            frame.showScoreBoard();
        });
    }

    /**
     * Reads user scores from the provided file and stores them.
     * Currently unused by the GUI, but intended for backend data loading.
     *
     * @param filename the file to read (currently hardcoded inside method)
     */
    public void readAndStore(String filename) {
        // Path to the scoreboard file
        String store = "src/storage/scores/scoreboard.txt";
        File scoreFile = new File(store);

        // Check if file exists
        if (scoreFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(scoreFile))) {
                String line;

                // Read each line and parse user and score
                while ((line = reader.readLine()) != null) {
                    String[] words = line.split(",");
                    User theUser = new User();
                    theUser.setName(words[0]);
                    theUser.setScore(Integer.parseInt(words[1]));
                }

            } catch (IOException e) {
                System.out.println("There is a problem loading the file!");
                e.printStackTrace();
            }
        } else {
            System.out.println("There is a problem finding the file!");
        }
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
