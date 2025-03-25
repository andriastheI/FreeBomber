package Background;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Handles keyboard input for movement using the KeyListener interface.
 */
public class KeyHandler implements KeyListener {

    // Movement direction flags
    private boolean upDirection, downDirection, leftDirection, rightDirection;

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used but required for implementation.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        toggleDirection(e.getKeyCode(), true);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        toggleDirection(e.getKeyCode(), false);
    }

    /**
     * Toggles the movement direction based on the key event.
     * @param keyCode The key code of the pressed/released key.
     * @param isPressed True if the key is pressed, false if released.
     */
    private void toggleDirection(int keyCode, boolean isPressed) {
        switch (keyCode) {
            case KeyEvent.VK_W -> upDirection = isPressed;
            case KeyEvent.VK_S -> downDirection = isPressed;
            case KeyEvent.VK_A -> leftDirection = isPressed;
            case KeyEvent.VK_D -> rightDirection = isPressed;
        }
    }

    public boolean isUpDirection() { return upDirection; }
    public boolean isDownDirection() { return downDirection; }
    public boolean isLeftDirection() { return leftDirection; }
    public boolean isRightDirection() { return rightDirection; }

    public void setUpDirection(boolean upDirection) { this.upDirection = upDirection; }
    public void setDownDirection(boolean downDirection) { this.downDirection = downDirection; }
    public void setLeftDirection(boolean leftDirection) { this.leftDirection = leftDirection; }
    public void setRightDirection(boolean rightDirection) { this.rightDirection = rightDirection; }
}
