package Characters;

import Background.Background;
import Background.TileManager;

/**
 * The EnemyCollision class is responsible for detecting collisions between
 * the enemy (Character.E_Slug) and the player character (Character.Character.JackBomber),
 * as well as managing the collision detection with the environment (tiles).
 */
public class EnemyCollision {
    final boolean[] collisionDirection = {false, false, false, false}; // up, down, left, right
    private final Background background;

    /**
     * Constructor for EnemyCollision.
     *
     * @param background The Background object which holds tile and screen information.
     */
    public EnemyCollision(Background background) {
        this.background = background;
    }

    /**
     * Checks for collision between the character (enemy) and the player character (JackBomber).
     * Also checks for collisions with tiles in the current movement direction.
     * If a collision is detected, the character's movement is blocked.
     *
     * @param character The enemy character whose collision needs to be checked.
     * @param jack      The player character (JackBomber) to check for collision with.
     */
    public void checkCollision(Character character, JackBomber jack) {
        // Get coordinates of character (enemy) and JackBomber (player)
        int characterLeftX = character.x + character.spriteBounds.x;
        int characterRightX = character.x + character.spriteBounds.x + character.spriteBounds.width;
        int characterTopY = character.y + character.spriteBounds.y;
        int characterBottomY = character.y + character.spriteBounds.y + character.spriteBounds.height;

        int jackLeftX = jack.x + jack.spriteBounds.x;
        int jackRightX = jack.x + jack.spriteBounds.x + jack.spriteBounds.width;
        int jackTopY = jack.y + jack.spriteBounds.y;
        int jackBottomY = jack.y + jack.spriteBounds.y + jack.spriteBounds.height;


//        int playerHealth = jack.getPlayerHealth();

        // Check for collision between the enemy character and the player character
        if (character.isAlive()) {
            if (characterLeftX < jackRightX && characterRightX > jackLeftX &&
                    characterTopY < jackBottomY && characterBottomY > jackTopY) {
                // Collision detected, trigger game over
                character.collisionOn = true;
                jack.takeDamage();

            }
        }


        // Get tile properties
        int tileSize = background.getTileSize();
        int screenCols = background.getScreenCols();
        int screenRows = background.getScreenRows();
        TileManager tileManager = background.getTileManager();

        // Ensure values are within bounds for map array
        int leftTileIndex = Math.max(0, characterLeftX / tileSize);
        int rightTileIndex = Math.min(screenCols - 1, characterRightX / tileSize);
        int topTileIndex = Math.max(0, characterTopY / tileSize);
        int bottomTileIndex = Math.min(screenRows - 1, characterBottomY / tileSize);

        int tile1, tile2;

        // Check for collision with tiles in the current direction
        switch (character.direction) {
            case "up":
                topTileIndex = Math.max(0, (characterTopY - character.speed) / tileSize);
                tile1 = tileManager.mapTileNum[leftTileIndex][topTileIndex];
                tile2 = tileManager.mapTileNum[rightTileIndex][topTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                    collisionDirection[0] = false;
                } else {
                    collisionDirection[0] = true;
                }
                break;

            case "down":
                bottomTileIndex = Math.min(screenRows - 1, (characterBottomY + character.speed) / tileSize);
                tile1 = tileManager.mapTileNum[leftTileIndex][bottomTileIndex];
                tile2 = tileManager.mapTileNum[rightTileIndex][bottomTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                    collisionDirection[1] = false;
                } else {
                    collisionDirection[1] = true;
                }
                break;

            case "left":
                leftTileIndex = Math.max(0, (characterLeftX - character.speed) / tileSize);
                tile1 = tileManager.mapTileNum[leftTileIndex][topTileIndex];
                tile2 = tileManager.mapTileNum[leftTileIndex][bottomTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                    collisionDirection[2] = false;
                } else {
                    collisionDirection[2] = true;
                }
                break;

            case "right":
                rightTileIndex = Math.min(screenCols - 1, (characterRightX + character.speed) / tileSize);
                tile1 = tileManager.mapTileNum[rightTileIndex][topTileIndex];
                tile2 = tileManager.mapTileNum[rightTileIndex][bottomTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                    collisionDirection[3] = false;
                } else {
                    collisionDirection[3] = true;
                }
                break;
        }
    }

    /**
     * Displays the game over message and updates the background state to reflect game over.
     */
    public void gameOver() {
        background.gameOver = true;
        background.repaint();
    }
}
