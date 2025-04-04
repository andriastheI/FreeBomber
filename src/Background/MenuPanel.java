package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import FreeBomber.*;

/**
 * The MenuPanel class is the game's starting menu.
 * It provides buttons to start the game or view high scores.
 */
public class MenuPanel extends JPanel implements ActionListener {

    private JButton newGameButton;
    private JButton highScoreButton;
    private Background bg = new Background();



    public MenuPanel(FreeBomber frame) {
        setPreferredSize(new Dimension(bg.getScreenWidth(), bg.getScreenHeight()));
        setLayout(null);
        setBackground(Color.BLACK);

        // New Game Button
        newGameButton = new JButton("New Game");
        newGameButton.setBounds(314, 220, 200, 50);
        newGameButton.setBackground(Color.BLUE);
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 20));
        newGameButton.setFocusable(false);
        newGameButton.addActionListener(this);
        newGameButton.addActionListener((ActionEvent e) -> {
            frame.startGame(); // Start the game from FreeBomber
        });
        add(newGameButton);

        // High Score Button
        highScoreButton = new JButton("High Scores");
        highScoreButton.setBounds(314, 300, 200, 50);
        highScoreButton.setBackground(Color.GRAY);
        highScoreButton.setForeground(Color.WHITE);
        highScoreButton.setFont(new Font("Arial", Font.BOLD, 20));
        highScoreButton.setFocusable(false);
        add(highScoreButton);

        // Add action listeners
        newGameButton.addActionListener(e -> frame.startGame());

        highScoreButton.addActionListener(e -> JOptionPane.showMessageDialog(
                frame,
                "High scores coming soon!",
                "High Scores",
                JOptionPane.INFORMATION_MESSAGE
        ));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
