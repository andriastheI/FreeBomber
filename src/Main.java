package src;

<<<<<<< HEAD
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello World");
        System.out.println("This is a second edit");
    }
=======
import javax.swing.*;

public class Main extends JFrame {
   public static void main(String[] args) {
       Main main = new Main();
   }
   public Main(){
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setResizable(false);
       setTitle("FreeBomber");
       Background gamePanel = new Background();
       add(gamePanel);
       pack();
       setLocationRelativeTo(null);
       setVisible(true);
       gamePanel.startGameThread();
   }
>>>>>>> createBackG_A
}
