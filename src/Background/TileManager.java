package Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * The TileManager class is responsible for managing the tiles used in the game.
 * It handles loading tile images, reading map data, and drawing tiles on the screen.
 */
public class TileManager {

    public Tile[] tile;
    public int[][] mapTileNum;
    public int currentMap = 1;  // Track which map is currently loaded
    Background gp;

    /**
     * Constructor for the TileManager class.
     * Initializes the tile array and mapTileNum array, then loads tile images and the default map.
     *
     * @param gp The Background object that holds the game's environment and properties.
     */
    public TileManager(Background gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.getScreenCols()][gp.getScreenRows()];
        getTileImg();
        loadMap(1);  // Load map 1 by default
    }

    /**
     * Loads tile images and their properties (like collision) from the specified resources.
     * This method reads images for different tiles (e.g., grass, wall) and assigns collision behavior.
     */
    public void getTileImg() {
        try {
            tile[0] = new Tile();
            tile[0].img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("storage/tiles/grass1.png")));
            tile[0].collision = false;

            tile[1] = new Tile();
            tile[1].img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("storage/tiles/wall1.png")));
            tile[1].collision = true;

            tile[2] = new Tile();
            tile[2].img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("storage/tiles/softWall.png")));
            tile[2].collision = false;

            tile[3] = new Tile();
            tile[3].img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("storage/tiles/wall2.png")));
            tile[3].collision = true;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads the map data from a text file corresponding to the specified map number.
     * The map data is read into a 2D array called mapTileNum, where each entry represents a tile.
     *
     * @param mapNumber The number of the map to load (e.g., level1, level2).
     */
    public void loadMap(int mapNumber) {
        try {
            currentMap = mapNumber;  // Set the current map number

            InputStream iS = getClass().getClassLoader().getResourceAsStream("storage/maps/level" + mapNumber + ".txt");
            assert iS != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(iS));

            int col = 0;
            int row = 0;

            while (col < gp.getScreenCols() && row < gp.getScreenRows()) {
                String line = br.readLine();
                while (col < gp.getScreenCols()) {
                    String[] numbers = line.split(" ");
                    int x = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = x;
                    col++;
                }
                if (col == gp.getScreenCols()) {
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the explosion logic and replaces soft walls with grass after a bomb explosion.
     * @param explosionArea The area affected by the explosion.
     */
    public void handleExplosion(Rectangle explosionArea) {
        for (int col = 0; col < gp.getScreenCols(); col++) {
            for (int row = 0; row < gp.getScreenRows(); row++) {
                // Check if the tile is a soft wall (tile[2]) and is within the explosion area
                if (mapTileNum[col][row] == 2) {
                    int tileX = col * gp.getTileSize();
                    int tileY = row * gp.getTileSize();
                    Rectangle tileRect = new Rectangle(tileX, tileY, gp.getTileSize(), gp.getTileSize());

                    if (explosionArea.intersects(tileRect)) {
                        // Replace soft wall with grass after explosion
                        mapTileNum[col][row] = 0; // Change tile to grass (tile[0])
                    }
                }
            }
        }
    }

    /**
     * Draws the map on the screen using the Graphics2D object.
     * Iterates over the mapTileNum array and draws the corresponding tile images at the appropriate screen coordinates.
     *
     * @param g2d The Graphics2D object used for drawing the tiles on the screen.
     */
    public void draw(Graphics2D g2d) {
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while (col < gp.getScreenCols() && row < gp.getScreenRows()) {
            int tileNum = mapTileNum[col][row];

            g2d.drawImage(tile[tileNum].img, x, y, gp.getTileSize(), gp.getTileSize(), null);
            col++;
            x += gp.getTileSize();
            if (col == gp.getScreenCols()) {
                col = 0;
                x = 0;
                row++;
                y += gp.getTileSize();
            }
        }
    }
}
