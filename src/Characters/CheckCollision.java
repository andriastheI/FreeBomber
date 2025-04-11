package Characters;

import Background.Background;
import Background.TileManager;

public class CheckCollision {
    private final Background background;

    public CheckCollision(Background background) {
        this.background = background;
    }

    public void checkCollision(Character character) {
        int characterLeftX = character.x + character.spriteBounds.x;
        int characterRightX = character.x + character.spriteBounds.x + character.spriteBounds.width;
        int characterTopY = character.y + character.spriteBounds.y;
        int characterBottomY = character.y + character.spriteBounds.y + character.spriteBounds.height;

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
                tile1 = tileManager.mapTileNum[leftTileIndex][topTileIndex];
                tile2 = tileManager.mapTileNum[rightTileIndex][topTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                }
                if (tile1 == 3 || tile2 == 3) {
                    character.setLevelUp(true);
                    character.setDoorFound(true);
                    character.setLevelStartTime(System.currentTimeMillis());
                }
                break;

            case "down":
                bottomTileIndex = Math.min(screenRows - 1, (characterBottomY + character.speed) / tileSize);
                tile1 = tileManager.mapTileNum[leftTileIndex][bottomTileIndex];
                tile2 = tileManager.mapTileNum[rightTileIndex][bottomTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                }
                if (tile1 == 3 || tile2 == 3) {
                    character.setLevelUp(true);
                    character.setDoorFound(true);
                    character.setLevelStartTime(System.currentTimeMillis());
                }
                break;

            case "left":
                leftTileIndex = Math.max(0, (characterLeftX - character.speed) / tileSize);
                tile1 = tileManager.mapTileNum[leftTileIndex][topTileIndex];
                tile2 = tileManager.mapTileNum[leftTileIndex][bottomTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                }
                if (tile1 == 3 || tile2 == 3) {
                    character.setLevelUp(true);
                    character.setDoorFound(true);
                    character.setLevelStartTime(System.currentTimeMillis());
                }
                break;

            case "right":
                rightTileIndex = Math.min(screenCols - 1, (characterRightX + character.speed) / tileSize);
                tile1 = tileManager.mapTileNum[rightTileIndex][topTileIndex];
                tile2 = tileManager.mapTileNum[rightTileIndex][bottomTileIndex];
                if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
                    character.collisionOn = true;
                }
                if (tile1 == 3 || tile2 == 3) {
                    character.setLevelUp(true);
                    character.setDoorFound(true);
                    character.setLevelStartTime(System.currentTimeMillis());
                }
                break;
        }
    }
}
