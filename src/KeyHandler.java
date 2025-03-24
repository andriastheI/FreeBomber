import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    protected boolean upDirection, downDirection, leftDirection, rightDirection;


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int value = e.getKeyCode();

        if (value == KeyEvent.VK_W) {
            upDirection = true;
        }
        if (value == KeyEvent.VK_S) {
            downDirection = true;
        }
        if (value == KeyEvent.VK_D) {
            rightDirection = true;
        }
        if (value == KeyEvent.VK_A) {
            leftDirection = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int value = e.getKeyCode();

        if (value == KeyEvent.VK_W) {
            upDirection = false;
        }
        if (value == KeyEvent.VK_S) {
            downDirection = false;
        }
        if (value == KeyEvent.VK_D) {
            rightDirection = false;
        }
        if (value == KeyEvent.VK_A) {
            leftDirection = false;
        }
    }
}
