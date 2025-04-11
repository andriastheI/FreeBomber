package Background;

import java.awt.image.BufferedImage;

/**
 * The Tile class represents a single tile in the game.
 * It stores information about the tile's collision behavior and the image associated with the tile.
 */
public class Tile {

    /**
     * Indicates whether the tile has collision (e.g., if a character can walk through it).
     * Default value is false, meaning no collision.
     */
    public boolean collision = false;

    /**
     * The image representing the tile.
     * It is used to visually render the tile in the game world.
     */
    protected BufferedImage img;
}
