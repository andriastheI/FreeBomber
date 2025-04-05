package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

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
        frame.setIconImage(new ImageIcon("src/storage/logo/logo.png").getImage());
        ImageIcon menuBg = new ImageIcon("src/storage/logo/menuBomber.jpg");
        JLabel bg = new JLabel(menuBg);
        bg.setBounds(0, 0, menuBg.getIconWidth()-150, menuBg.getIconHeight()-50);


        // New Game Button
        newGameButton = new JButton("New Game") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background color change on press
                Color base = getModel().isArmed() ? new Color(30, 30, 30) : new Color(50, 50, 200);
                g2.setColor(base);

                // Rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        newGameButton.setBounds(314, 300, 150, 50); // more width for text
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        newGameButton.setContentAreaFilled(false);  // prevent default painting
        newGameButton.setBorderPainted(false);      // remove border
        newGameButton.setFocusPainted(false);       // no outline on focus
        newGameButton.setOpaque(false);             // transparency
        newGameButton.addActionListener((ActionEvent e) -> {
            frame.startGame(); // Start the game from FreeBomber
        });

        add(newGameButton);
        // High Score Button
        highScoreButton = new JButton("High Scores"){
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Background color change on press
                Color base = getModel().isArmed() ? new Color(30, 30, 30) : new Color(50, 50, 200);
                g2.setColor(base);

                // Rounded background
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.dispose();

                super.paintComponent(g);
            }
        };
        highScoreButton.setBounds(290, 360, 200, 50); // more width for text
        highScoreButton.setForeground(Color.WHITE);
        highScoreButton.setFont(new Font("Arial", Font.BOLD, 18));
        highScoreButton.setContentAreaFilled(false);  // prevent default painting
        highScoreButton.setBorderPainted(false);      // remove border
        highScoreButton.setFocusPainted(false);       // no outline on focus
        highScoreButton.setOpaque(false);
        add(highScoreButton);
        add(bg);
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
