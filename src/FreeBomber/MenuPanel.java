package FreeBomber;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    public MenuPanel(JPanel mainPanel, CardLayout cardLayout) {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        JLabel title = new JLabel("BOMBER GAME", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 50));
        title.setForeground(Color.WHITE);
        add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1, 10, 10));
        buttonPanel.setBackground(Color.BLACK);

        // "New Game" Button
        JButton newGameButton = new JButton("New Game");
        newGameButton.setFont(new Font("Arial", Font.BOLD, 24));
        newGameButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Game"); // Switch to game panel
        });

        // "High Score" Button
        JButton highScoreButton = new JButton("High Scores");
        highScoreButton.setFont(new Font("Arial", Font.BOLD, 24));
        highScoreButton.addActionListener(e -> showHighScores());

        buttonPanel.add(newGameButton);
        buttonPanel.add(highScoreButton);

        add(buttonPanel, BorderLayout.CENTER);
    }

    // Show a simple High Score popup (you can improve this)
    private void showHighScores() {
        JOptionPane.showMessageDialog(this, "High Scores:\n1. PlayerA - 500\n2. PlayerB - 400", "High Scores", JOptionPane.INFORMATION_MESSAGE);
    }
}
