package Background;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import FreeBomber.*;

/**
 * The GameOverPanel is the game's ending menu.
 * It provides buttons to restart,play a new game or exit the game.
 */
public class GameOverPanel extends JPanel implements ActionListener {
    private JButton restartGameButton;
    private JButton newGameButton;
    private JButton exitButton;
    private Background backg = new Background();

    public GameOverPanel() {

    }

    public GameOverPanel(FreeBomber frame){
        setPreferredSize(new Dimension(backg.getScreenWidth(), backg.getScreenHeight()));
        setLayout(null);
        ImageIcon overBg = new ImageIcon("src/storage/logo/gameover.jpg");
        JLabel bg = new JLabel(overBg);
        bg.setBounds(0,0,overBg.getIconWidth() - 180,overBg.getIconHeight() - 80);

        // New Game Button
        restartGameButton = new JButton("Restart Game") {
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
        restartGameButton.setBounds(320, 380, 180, 50); // more width for text
        restartGameButton.setForeground(Color.WHITE);
        restartGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        restartGameButton.setContentAreaFilled(false);  // prevent default painting
        restartGameButton.setBorderPainted(false);      // remove border
        restartGameButton.setFocusPainted(false);       // no outline on focus
        restartGameButton.setOpaque(false);             // transparency
        restartGameButton.addActionListener((ActionEvent e) -> {
            frame.startGame(); // Start the game from FreeBomber
        });
        //newGameButton.addActionListener(e -> frame.startGame());

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
        newGameButton.setBounds(340, 440, 150, 50); // more width for text
        newGameButton.setForeground(Color.WHITE);
        newGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        newGameButton.setContentAreaFilled(false);  // prevent default painting
        newGameButton.setBorderPainted(false);      // remove border
        newGameButton.setFocusPainted(false);       // no outline on focus
        newGameButton.setOpaque(false);             // transparency
        newGameButton.addActionListener(e -> frame.startGame());

        // New Game Button
        exitButton = new JButton("Exit") {
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
        exitButton.setBounds(355, 500, 120, 50); // more width for text
        exitButton.setForeground(Color.WHITE);
        exitButton.setFont(new Font("Arial", Font.BOLD, 18));
        exitButton.setContentAreaFilled(false);  // prevent default painting
        exitButton.setBorderPainted(false);      // remove border
        exitButton.setFocusPainted(false);       // no outline on focus
        exitButton.setOpaque(false);
        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        add(restartGameButton);
        add(newGameButton);
        add(exitButton);
        add(bg);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

    }

}
