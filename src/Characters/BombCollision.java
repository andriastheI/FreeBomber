package Characters;

import Background.Background;
import Background.TileManager;

/**
 * The BombCollision class handles the collision detection for bombs in the game.
 * It checks if a bomb collides with any tiles in the game world and updates the bomb's collision status accordingly.
 */
public class BombCollision {

    // The background object, which contains the game environment and tile data.
    private final Background background;

    /**
     * Constructor for the BombCollision class.
     * Initializes the background object used for collision detection.
     *
     * @param background The background object that holds game properties and the tile manager.
     */
    public BombCollision(Background background) {
        this.background = background;
    }

    /**
     * Checks for collision between the bomb and surrounding tiles.
     * If the bomb collides with a tile, it sets the bomb's collision status to true.
     *
     * @param bomb The bomb object to check for collisions.
     */
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
