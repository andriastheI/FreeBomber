package src;

public class CheckCollision {
    Background bp;

    public CheckCollision(Background bg) {
        this.bp = bg;

    }

    public void checkCollision(Character checkJack) {
        int leftWallX = checkJack.x + checkJack.spriteBounds.x;
        int rightWallX = checkJack.x + checkJack.spriteBounds.x + checkJack.spriteBounds.width;
        int topWallY = checkJack.y + checkJack.spriteBounds.y;
        int bottomWallY = checkJack.y + checkJack.spriteBounds.y + checkJack.spriteBounds.height;

        // Ensure that the values are within bounds for the map array
        int leftColX = Math.max(0, leftWallX / bp.tileSize);
        int rightColX = Math.min(bp.screenCols - 1, rightWallX / bp.tileSize);
        int topRowY = Math.max(0, topWallY / bp.tileSize);
        int bottomRowY = Math.min(bp.screenRows - 1, bottomWallY / bp.tileSize);

        int topCorner1, topCorner2;

        switch (checkJack.direction) {
            case "up":
                topRowY = Math.max(0, (topWallY - checkJack.speed) / bp.tileSize); // Prevent going above 0
                topCorner1 = bp.tileManager.mapTileNum[leftColX][topRowY];
                topCorner2 = bp.tileManager.mapTileNum[rightColX][topRowY];

                if (bp.tileManager.tile[topCorner1].collison || bp.tileManager.tile[topCorner2].collison) {
                    checkJack.collsionOn = true; // Block up movement if there's a collision
                }
                break;
            case "down":
                bottomRowY = Math.min(bp.screenRows - 1, (bottomWallY + checkJack.speed) / bp.tileSize); // Prevent going below the screen
                topCorner1 = bp.tileManager.mapTileNum[leftColX][bottomRowY];
                topCorner2 = bp.tileManager.mapTileNum[rightColX][bottomRowY];

                if (bp.tileManager.tile[topCorner1].collison || bp.tileManager.tile[topCorner2].collison) {
                    checkJack.collsionOn = true; // Block down movement if there's a collision
                }
                break;
            case "left":
                leftColX = Math.max(0, (leftWallX - checkJack.speed) / bp.tileSize); // Prevent going left of 0
                topCorner1 = bp.tileManager.mapTileNum[leftColX][topRowY];
                topCorner2 = bp.tileManager.mapTileNum[leftColX][bottomRowY];

                if (bp.tileManager.tile[topCorner1].collison || bp.tileManager.tile[topCorner2].collison) {
                    checkJack.collsionOn = true; // Block left movement if there's a collision
                }
                break;
            case "right":
                rightColX = Math.min(bp.screenCols - 1, (rightWallX + checkJack.speed) / bp.tileSize); // Prevent going beyond the right edge
                topCorner1 = bp.tileManager.mapTileNum[rightColX][topRowY];
                topCorner2 = bp.tileManager.mapTileNum[rightColX][bottomRowY];

                if (bp.tileManager.tile[topCorner1].collison || bp.tileManager.tile[topCorner2].collison) {
                    checkJack.collsionOn = true; // Block right movement if there's a collision
                }
                break;
        }
    }


}
