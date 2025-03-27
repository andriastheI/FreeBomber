package Characters;

import Background.Background;
import Background.TileManager;

public class BombCollision {
    private final Background background;

    public BombCollision(Background background) {
        this.background = background;
    }

    public void checkBombCollision(Bomb bomb) {
        int bombLeftX = bomb.getX();
        int bombRightX = bomb.getX() + bomb.getSize();
        int bombTopY = bomb.getY();
        int bombBottomY = bomb.getY() + bomb.getSize();

        // Get tile properties
        int tileSize = background.getTileSize();
        int screenCols = background.getScreenCols();
        int screenRows = background.getScreenRows();
        TileManager tileManager = background.getTileManager();

        // Ensure values remain within bounds
        int leftTileIndex = Math.max(0, bombLeftX / tileSize);
        int rightTileIndex = Math.min(screenCols - 1, bombRightX / tileSize);
        int topTileIndex = Math.max(0, bombTopY / tileSize);
        int bottomTileIndex = Math.min(screenRows - 1, bombBottomY / tileSize);

        int tile1, tile2;

        // Checking for collision with tiles around the bomb
        tile1 = tileManager.mapTileNum[leftTileIndex][topTileIndex];
        tile2 = tileManager.mapTileNum[rightTileIndex][topTileIndex];
        if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
            bomb.setCollision(true); // Bomb collided with the top tiles
            return; // No need to check further
        }

        tile1 = tileManager.mapTileNum[leftTileIndex][bottomTileIndex];
        tile2 = tileManager.mapTileNum[rightTileIndex][bottomTileIndex];
        if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
            bomb.setCollision(true); // Bomb collided with the bottom tiles
            return; // No need to check further
        }

        tile1 = tileManager.mapTileNum[leftTileIndex][topTileIndex];
        tile2 = tileManager.mapTileNum[leftTileIndex][bottomTileIndex];
        if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
            bomb.setCollision(true); // Bomb collided with the left tiles
            return; // No need to check further
        }

        tile1 = tileManager.mapTileNum[rightTileIndex][topTileIndex];
        tile2 = tileManager.mapTileNum[rightTileIndex][bottomTileIndex];
        if (tileManager.tile[tile1].collision || tileManager.tile[tile2].collision) {
            bomb.setCollision(true); // Bomb collided with the right tiles
        }
    }
}
