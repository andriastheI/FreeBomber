package Characters;

import Background.Background;
import Background.TileManager;

/**
 * Description:
 * <p>
 * Handles collision detection between characters and the environment.
 * This includes interactions with walls, soft blocks, and the door tile.
 * It updates the character's collision state and triggers level progression logic when the door is found.
 * </p>
 *
 * @author mguzelocak
 * @author Zelele
 */
public class CheckCollision {
    /** background instance */
    private final Background background;

    /** constructor  for this class */
    public CheckCollision(Background background) {
        this.background = background;
    }

    /**
     * Checks for collisions between a character and the game environment,
     * including walls, objects, and possibly other characters.
     *
     * @param character the character to check for collisions
     */
    public void checkCollision(JackBomber character) {
        int characterLeftX = character.getX() + character.spriteBounds.x;
        int characterRightX = character.getX() + character.spriteBounds.x + character.spriteBounds.width;
        int characterTopY = character.getY() + character.spriteBounds.y;
        int characterBottomY = character.getY() + character.spriteBounds.y + character.spriteBounds.height;

        // Get tile properties
        int tileSize = background.getTileSize();
        int screenCols = background.getScreenCols();
        int screenRows = background.getScreenRows();
        TileManager tileManager = background.getTileManager();

        // Ensure values remain within bounds
        int leftTileIndex = Math.max(0, characterLeftX / tileSize);
        int rightTileIndex = Math.min(screenCols - 1, characterRightX / tileSize);
        int topTileIndex = Math.max(0, characterTopY / tileSize);
        int bottomTileIndex = Math.min(screenRows - 1, characterBottomY / tileSize);

        int tile1, tile2;

        switch (character.direction) {
            case "up":
                topTileIndex = Math.max(0, (characterTopY - character.speed) / tileSize);
                tile1 = tileManager.getMapTileNum()[leftTileIndex][topTileIndex];
                tile2 = tileManager.getMapTileNum()[rightTileIndex][topTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                }
                if (tile1 == 3 || tile2 == 3) {
                    character.increaseScore(400);
                    character.setLevelUp(true);
                    character.setDoorFound(true);
                    character.setLevelStartTime(System.currentTimeMillis());
                }
                break;

            case "down":
                bottomTileIndex = Math.min(screenRows - 1, (characterBottomY + character.speed) / tileSize);
                tile1 = tileManager.getMapTileNum()[leftTileIndex][bottomTileIndex];
                tile2 = tileManager.getMapTileNum()[rightTileIndex][bottomTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                }
                if (tile1 == 3 || tile2 == 3) {
                    character.increaseScore(400);
                    character.setLevelUp(true);
                    character.setDoorFound(true);
                    character.setLevelStartTime(System.currentTimeMillis());
                }
                break;

            case "left":
                leftTileIndex = Math.max(0, (characterLeftX - character.speed) / tileSize);
                tile1 = tileManager.getMapTileNum()[leftTileIndex][topTileIndex];
                tile2 = tileManager.getMapTileNum()[leftTileIndex][bottomTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                }
                if (tile1 == 3 || tile2 == 3) {
                    character.increaseScore(400);
                    character.setLevelUp(true);
                    character.setDoorFound(true);
                    character.setLevelStartTime(System.currentTimeMillis());
                }
                break;

            case "right":
                rightTileIndex = Math.min(screenCols - 1, (characterRightX + character.speed) / tileSize);
                tile1 = tileManager.getMapTileNum()[rightTileIndex][topTileIndex];
                tile2 = tileManager.getMapTileNum()[rightTileIndex][bottomTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                }
                if (tile1 == 3 || tile2 == 3) {
                    character.increaseScore(400);
                    character.setLevelUp(true);
                    character.setDoorFound(true);
                    character.setLevelStartTime(System.currentTimeMillis());
                }
                break;
        }
    }
}
