package Background;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Description:
 * <p>
 * Handles keyboard input for movement and bomb control using the KeyListener interface.
 * Tracks the state of directional keys (W, A, S, D) and space bar (bomb drop).
 * </p>
 *
 * @author mguzelocak
 * @author Zelele
 */
public class KeyHandler implements KeyListener {

    /** True if 'W' key is pressed to move up */
    private boolean upDirection;

    /** True if 'S' key is pressed to move down */
    private boolean downDirection;

    /** True if 'A' key is pressed to move left */
    private boolean leftDirection;

    /** True if 'D' key is pressed to move right */
    private boolean rightDirection;

    /** True if spacebar is pressed to drop a bomb */
    private boolean bombDrop;

    /**
     * Returns whether the bomb drop action is triggered.
     *
     * @return true if the bomb drop is active, false otherwise.
     */
    public boolean isBombDrop() {
        return bombDrop;
    }

    /**
     * Sets the state of the bomb drop action.
     *
     * @param bombDrop true to activate the bomb drop, false to deactivate it.
     */
    public void setBombDrop(boolean bombDrop) {
        this.bombDrop = bombDrop;
    }

    /**
     * Not used but required for implementation of the KeyListener interface.
     * This method is called when a key is typed, but it's not needed in this case.
     *
     * @param e the KeyEvent triggered by typing a key.
     */
    @Override
    public void keyTyped(KeyEvent e) {
        // Not used but required for implementation.
    }

    /**
     * Handles key press events. It updates the movement direction flags and triggers bomb drop action.
     *
     * @param e the KeyEvent triggered by pressing a key.
     */
    @Override
    public void keyPressed(KeyEvent e) {

        // Toggle the movement direction flag for the pressed key
        toggleDirection(e.getKeyCode(), true);

        int value = e.getKeyCode();

        // Set specific direction flags based on the key pressed
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
        if (value == KeyEvent.VK_SPACE) {
            bombDrop = true;
        }
    }

    /**
     * Handles key release events. It resets the bomb drop action and movement direction flags.
     *
     * @param e the KeyEvent triggered by releasing a key.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int value = e.getKeyCode();

        // Reset bomb drop when space key is released
        if (value == KeyEvent.VK_SPACE) {
            bombDrop = false;
        }

        // Toggle the movement direction flag for the released key
        toggleDirection(e.getKeyCode(), false);
    }

    /**
     * Toggles the movement direction based on the key event.
     * This method is called to enable or disable specific movement directions.
     *
     * @param keyCode   The key code of the pressed/released key.
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

    /**
     * Returns whether the up direction movement is active.
     *
     * @return true if the up direction is active, false otherwise.
     */
    public boolean isUpDirection() {
        return upDirection;
    }

    /**
     * Sets the state of the up direction movement.
     *
     * @param upDirection true to activate the up direction, false to deactivate it.
     */
    public void setUpDirection(boolean upDirection) {
        this.upDirection = upDirection;
    }

    /**
     * Returns whether the down direction movement is active.
     *
     * @return true if the down direction is active, false otherwise.
     */
    public boolean isDownDirection() {
        return downDirection;
    }

    /**
     * Sets the state of the down direction movement.
     *
     * @param downDirection true to activate the down direction, false to deactivate it.
     */
    public void setDownDirection(boolean downDirection) {
        this.downDirection = downDirection;
    }

    /**
     * Returns whether the left direction movement is active.
     *
     * @return true if the left direction is active, false otherwise.
     */
    public boolean isLeftDirection() {
        return leftDirection;
    }

    /**
     * Sets the state of the left direction movement.
     *
     * @param leftDirection true to activate the left direction, false to deactivate it.
     */
    public void setLeftDirection(boolean leftDirection) {
        this.leftDirection = leftDirection;
    }

    /**
     * Returns whether the right direction movement is active.
     *
     * @return true if the right direction is active, false otherwise.
     */
    public boolean isRightDirection() {
        return rightDirection;
    }

    /**
     * Sets the state of the right direction movement.
     *
     * @param rightDirection true to activate the right direction, false to deactivate it.
     */
    public void setRightDirection(boolean rightDirection) {
        this.rightDirection = rightDirection;
    }
}
