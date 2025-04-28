package Background;

import java.awt.image.BufferedImage;

/**
 * Description:
 * <p>
 * The Tile class represents a single tile in the game.
 * It stores information about the tile's collision behavior and the image associated with the tile.
 * This data is used to determine player movement and render visuals in the map.
 * </p>
 *
 * @author mguzelocak
 * @author Zelele
 * <p>
 * Algorithm idea adapted from:
 * <a href="https://www.youtube.com/@RyiSnow">...</a>
 * </p>
 */
public class Tile {

    /**
     * Indicates whether the tile has collision.
     * If true, characters cannot walk through this tile. Defaults to false.
     */
    public boolean collision = false;

    /**
     * The image used to visually represent the tile in the game world.
     * This image is rendered on screen during gameplay.
     */
    protected BufferedImage img;
}
