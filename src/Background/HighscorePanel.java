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

public class HighscorePanel extends JPanel implements ActionListener {
    private Background backg = new Background();
    private JButton backButton;
    private Map<String, Integer> scoreboardData = new HashMap<>();

    public HighscorePanel(FreeBomber frame) {
        setPreferredSize(new Dimension(backg.getScreenWidth(),backg.getScreenHeight()));
        setLayout(null);
        setBackground(Color.DARK_GRAY);


        backButton = new JButton("Back") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background color change on press
                Color base = getModel().isArmed() ? new Color(150, 150, 150) : new Color(255, 0, 0);
                g2.setColor(base);

                // Rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        backButton.setBounds(500, 500, 100, 50); // more width for text
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 18));
        backButton.setContentAreaFilled(false);  // prevent default painting
        backButton.setBorderPainted(false);      // remove border
        backButton.setFocusPainted(false);       // no outline on focus
        backButton.setOpaque(false);
        backButton.addActionListener((ActionEvent e) -> {
            frame.getBackToMenu();
        });
        add(backButton);
        setVisible(true);
        readAndStore();
    }
    public void readAndStore() {
        Map<String, Integer> tempDictionary = new HashMap<>();

        try {
            // Load the file as a resource (works in JARs too)
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getClass().getClassLoader().getResourceAsStream("storage/scores/scoreboard.txt"))
            );

            String line;
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

            reader.close();
            this.scoreboardData = tempDictionary;
        } catch (Exception e) {
            System.out.println("Failed to load scoreboard: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        g2.drawString("HIGH SCORES", 100, 100);

        // Smaller font for entries
        g2.setFont(new Font("Arial", Font.PLAIN, 24));

        // Sort and display scores
        AtomicInteger y = new AtomicInteger(150);
        scoreboardData.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue())) // descending order
                .limit(10) // show top 10
                .forEach(entry -> {
                    g2.drawString(entry.getKey() + " - " + entry.getValue(), 120, y.get());
                    y.addAndGet(35);
                });
    }




    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
