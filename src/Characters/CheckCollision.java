package Characters;

import Background.Background;

public class CheckCollision {
    Background background;

    public CheckCollision(Background background) {
        this.background = background;
    }

    public void checkCollision(Character character) {
        int characterLeftX = character.x + character.spriteBounds.x;
        int characterRightX = character.x + character.spriteBounds.x + character.spriteBounds.width;
        int characterTopY = character.y + character.spriteBounds.y;
        int characterBottomY = character.y + character.spriteBounds.y + character.spriteBounds.height;

        // Ensure that the values are within bounds for the map array
        int leftTileIndex = Math.max(0, characterLeftX / background.tileSize);
        int rightTileIndex = Math.min(background.screenCols - 1, characterRightX / background.tileSize);
        int topTileIndex = Math.max(0, characterTopY / background.tileSize);
        int bottomTileIndex = Math.min(background.screenRows - 1, characterBottomY / background.tileSize);

        int tile1, tile2;

        switch (character.direction) {
            case "up":
                topTileIndex = Math.max(0, (characterTopY - character.speed) / background.tileSize); // Prevent going above 0
                tile1 = background.tileManager.mapTileNum[leftTileIndex][topTileIndex];
                tile2 = background.tileManager.mapTileNum[rightTileIndex][topTileIndex];

                if (background.tileManager.tile[tile1].collision || background.tileManager.tile[tile2].collision) {
                    character.collisionOn = true; // Block up movement if there's a collision
                }
                break;
            case "down":
                bottomTileIndex = Math.min(background.screenRows - 1, (characterBottomY + character.speed) / background.tileSize); // Prevent going below the screen
                tile1 = background.tileManager.mapTileNum[leftTileIndex][bottomTileIndex];
                tile2 = background.tileManager.mapTileNum[rightTileIndex][bottomTileIndex];

                if (background.tileManager.tile[tile1].collision || background.tileManager.tile[tile2].collision) {
                    character.collisionOn = true; // Block down movement if there's a collision
                }
                break;
            case "left":
                leftTileIndex = Math.max(0, (characterLeftX - character.speed) / background.tileSize); // Prevent going left of 0
                tile1 = background.tileManager.mapTileNum[leftTileIndex][topTileIndex];
                tile2 = background.tileManager.mapTileNum[leftTileIndex][bottomTileIndex];

                if (background.tileManager.tile[tile1].collision || background.tileManager.tile[tile2].collision) {
                    character.collisionOn = true; // Block left movement if there's a collision
                }
                break;
            case "right":
                rightTileIndex = Math.min(background.screenCols - 1, (characterRightX + character.speed) / background.tileSize); // Prevent going beyond the right edge
                tile1 = background.tileManager.mapTileNum[rightTileIndex][topTileIndex];
                tile2 = background.tileManager.mapTileNum[rightTileIndex][bottomTileIndex];

                if (background.tileManager.tile[tile1].collision || background.tileManager.tile[tile2].collision) {
                    character.collisionOn = true; // Block right movement if there's a collision
                }
                break;
        }
    }
}
