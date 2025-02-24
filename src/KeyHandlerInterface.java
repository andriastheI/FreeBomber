package src;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface KeyHandlerInterface extends KeyListener {
        void keyPressed(KeyEvent e);
        void keyReleased(KeyEvent e);
        void keyTyped(KeyEvent e);

}
