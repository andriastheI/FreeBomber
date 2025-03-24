public class ESlugCollision {
    Background background;
    boolean[] collisionDirection = {false, false, false, false}; // up, down, left, right

    public ESlugCollision(Background background) {
        this.background = background;
    }

    // Updated to accept both the character and JackBomber as parameters
    public void checkCollision(Character character, JackBomber jack) {
        // Enemy (E_Slug) collision detection with JackBomber
        int characterLeftX = character.x + character.spriteBounds.x;
        int characterRightX = character.x + character.spriteBounds.x + character.spriteBounds.width;
        int characterTopY = character.y + character.spriteBounds.y;
        int characterBottomY = character.y + character.spriteBounds.y + character.spriteBounds.height;

        int jackLeftX = jack.x + jack.spriteBounds.x;
        int jackRightX = jack.x + jack.spriteBounds.x + jack.spriteBounds.width;
        int jackTopY = jack.y + jack.spriteBounds.y;
        int jackBottomY = jack.y + jack.spriteBounds.y + jack.spriteBounds.height;

        // Collision detection between character (E_Slug) and JackBomber
        if (characterLeftX < jackRightX && characterRightX > jackLeftX &&
                characterTopY < jackBottomY && characterBottomY > jackTopY) {
            // Collision detected, trigger game over
            character.collisionOn = true;
            gameOver();
        }

        // Ensure values are within bounds for map array
        int leftTileIndex = Math.max(0, characterLeftX / background.tileSize);
        int rightTileIndex = Math.min(background.screenCols - 1, characterRightX / background.tileSize);
        int topTileIndex = Math.max(0, characterTopY / background.tileSize);
        int bottomTileIndex = Math.min(background.screenRows - 1, characterBottomY / background.tileSize);

        int tile1, tile2;

        // Check for collision with tiles in the current direction
        switch (character.direction) {
            case "up":
                topTileIndex = Math.max(0, (characterTopY - character.speed) / background.tileSize); // Prevent moving above 0
                tile1 = background.tileManager.mapTileNum[leftTileIndex][topTileIndex];
                tile2 = background.tileManager.mapTileNum[rightTileIndex][topTileIndex];

                if (background.tileManager.tile[tile1].collision || background.tileManager.tile[tile2].collision) {
                    character.collisionOn = true; // Block upward movement if there's a collision
                    collisionDirection[0] = false;
                } else {
                    collisionDirection[0] = true;
                }
                break;
            case "down":
                bottomTileIndex = Math.min(background.screenRows - 1, (characterBottomY + character.speed) / background.tileSize); // Prevent moving below screen
                tile1 = background.tileManager.mapTileNum[leftTileIndex][bottomTileIndex];
                tile2 = background.tileManager.mapTileNum[rightTileIndex][bottomTileIndex];

                if (background.tileManager.tile[tile1].collision || background.tileManager.tile[tile2].collision) {
                    character.collisionOn = true; // Block downward movement if there's a collision
                    collisionDirection[1] = false;
                } else {
                    collisionDirection[1] = true;
                }
                break;
            case "left":
                leftTileIndex = Math.max(0, (characterLeftX - character.speed) / background.tileSize); // Prevent moving left of 0
                tile1 = background.tileManager.mapTileNum[leftTileIndex][topTileIndex];
                tile2 = background.tileManager.mapTileNum[leftTileIndex][bottomTileIndex];

                if (background.tileManager.tile[tile1].collision || background.tileManager.tile[tile2].collision) {
                    character.collisionOn = true; // Block leftward movement if there's a collision
                    collisionDirection[2] = false;
                } else {
                    collisionDirection[2] = true;
                }
                break;
            case "right":
                rightTileIndex = Math.min(background.screenCols - 1, (characterRightX + character.speed) / background.tileSize); // Prevent moving beyond the right edge
                tile1 = background.tileManager.mapTileNum[rightTileIndex][topTileIndex];
                tile2 = background.tileManager.mapTileNum[rightTileIndex][bottomTileIndex];

                if (background.tileManager.tile[tile1].collision || background.tileManager.tile[tile2].collision) {
                    character.collisionOn = true; // Block rightward movement if there's a collision
                    collisionDirection[3] = false;
                } else {
                    collisionDirection[3] = true;
                }
                break;
        }
    }

    // Game over method to display the game over message
    public void gameOver() {
        background.gameOver = true;
        background.repaint();
    }
}
