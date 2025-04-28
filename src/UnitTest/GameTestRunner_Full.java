package UnitTest;

import Background.Background;
import Background.TileManager;
import Characters.*;
import Characters.Character;

public class GameTestRunner_Full {

    public static void main(String[] args) {
        testJackBomber();
        testCharacter();
        testBomb();
        testEnemies();
        testBackground();
        testTileManager();
        System.out.print("\n=== All tests completed ===");
        System.exit(0);
    }

    public static void testJackBomber() {
        System.out.println("\n=== Testing JackBomber ===");
        try {
            JackBomber bomber = new JackBomber(null, null, null);

            bomber.setScore(100);
            printResult("JackBomber.setScore / getScore", bomber.getScore() == 100);

            bomber.increaseScore(50);
            printResult("JackBomber.increaseScore", bomber.getScore() == 150);

            bomber.decreaseScore(25);
            printResult("JackBomber.decreaseScore", bomber.getScore() == 125);

            printResult("JackBomber.getPlayerHealth", bomber.getPlayerHealth() == 3);

            bomber.setRemainingTime(60000L);
            printResult("JackBomber.setRemainingTime / getRemainingTime", bomber.getRemainingTime() == 60000L);

        } catch (Exception e) {
            System.out.println("✘ JackBomber ERROR: " + e.getMessage());
        }
    }

    public static void testCharacter() {
        System.out.println("\n=== Testing Character ===");
        try {
            Character c = new Character();
            c.setDoorFound(true);
            printResult("Character.setDoorFound(true)", c.isDoorFound() == true);

            c.setDoorFound(false);
            printResult("Character.setDoorFound(false)", c.isDoorFound() == false);
        } catch (Exception e) {
            System.out.println("✘ Character ERROR: " + e.getMessage());
        }
    }

    public static void testBomb() {
        System.out.println("\n=== Testing Bomb ===");
        try {
            Bomb b = new Bomb(null);
            b.setX(100);
            b.setY(200);
            printResult("Bomb.setX / getX", b.getX() == 100);
            printResult("Bomb.setY / getY", b.getY() == 200);
        } catch (Exception e) {
            System.out.println("✘ Bomb ERROR: " + e.getMessage());
        }
    }

    public static void testEnemies() {
        System.out.println("\n=== Testing Enemies ===");
        try {
            Background bg = new Background();
            JackBomber dummyPlayer = new JackBomber(bg, null, null);
            EnemySlug slug = new EnemySlug(bg, dummyPlayer);
            EnemyRock rock = new EnemyRock(bg, dummyPlayer);
            EnemyMush mush = new EnemyMush(bg, dummyPlayer);
            EnemyDragon dragon = new EnemyDragon(bg, dummyPlayer);

            printResult("EnemySlug initialized", slug != null);
            printResult("EnemyRock initialized", rock != null);
            printResult("EnemyMush initialized", mush != null);
            printResult("EnemyDragon initialized", dragon != null);
        } catch (Exception e) {
            System.out.println("✘ Enemy init ERROR: " + e.getMessage());
        }
    }

    public static void testBackground() {
        System.out.println("\n=== Testing Background ===");
        try {
            Background bg = new Background();
            printResult("Background.getScreenWidth > 0", bg.getScreenWidth() > 0);
            printResult("Background.getScreenHeight > 0", bg.getScreenHeight() > 0);
        } catch (Exception e) {
            System.out.println("✘ Background ERROR: " + e.getMessage());
        }
    }

    public static void testTileManager() {
        System.out.println("\n=== Testing TileManager ===");
        try {
            Background bg = new Background();
            TileManager tm = new TileManager(bg, 1);
            printResult("TileManager instance created", tm != null);
        } catch (Exception e) {
            System.out.println("✘ TileManager ERROR: " + e.getMessage());
        }
    }

    private static void printResult(String testName, boolean passed) {
        if (passed) {
            System.out.println("✔ " + testName + " PASSED");
        } else {
            System.out.println("✘ " + testName + " FAILED");
        }
    }
}
