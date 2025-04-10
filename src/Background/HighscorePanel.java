package Background;

import FreeBomber.FreeBomber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HighscorePanel extends JPanel implements ActionListener {
    private Background backg = new Background();
    private JButton backButton;

    public HighscorePanel(FreeBomber frame) {
        setPreferredSize(new Dimension(backg.getScreenWidth(),backg.getScreenHeight()));
        setLayout(null);
        setBackground(Color.GREEN);

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

    }


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
