package Background;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * Description:
 * <p>
 * Manages tile data for the game including loading tile images and map layouts.
 * Responsible for rendering tile maps and placing interactive tiles like doors.
 * </p>
 *
 * @author mguzelocak
 * @author Zelele
 */
public class TileManager {

    /** Used to randomly pick one of the door locations */
    private final Random theWizard = new Random();
    /** All possible door tile locations for the current map */
    private final List<int[]> doorLocations = new ArrayList<>();
    /** Reference to the game panel (Background class) */
    private final Background gp;
    /** Array to hold different tile types (grass, wall, etc.) */
    public Tile[] tile;
    /** Stores tile layout of the current map using tile indices */
    public int[][] mapTileNum;
    /** Track which map is currently loaded */
    private int currentMap = 1;
    /** Coordinates of the actual active door tile */
    private int[] theDoor;

    /**
     * Constructor for the TileManager class.
     * Initializes the tile array and mapTileNum array, then loads tile images and the default map.
     *
     * @param gp The Background object that holds the game's environment and properties.
     */
    public TileManager(Background gp, int startMap) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.getScreenCols()][gp.getScreenRows()];
        currentMap = startMap;
        getTileImg();
        loadMap(currentMap);
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
            tile[2].collision = true;

            tile[3] = new Tile();
            tile[3].img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("storage/tiles/thedoor.png")));
            tile[3].collision = true;

        } catch (IOException e) {
            System.out.println("Failed to load tiles");
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
            doorLocations.clear();

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

                    if (x == 2) {
                        doorLocations.add(new int[]{col, row});
                    }

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
            System.out.println("Failed to load map");
        }

        if (!doorLocations.isEmpty()) {
            theDoor = doorLocations.get(theWizard.nextInt(doorLocations.size()));
        }
    }

    /**
     * Handles the explosion logic and replaces soft walls with grass after a bomb explosion.
     * This method updates the map by replacing soft walls (tile[2]) with grass (tile[0]) within the explosion area.
     *
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

                    if (explosionArea.intersects(tileRect) && col == theDoor[0] && row == theDoor[1]) {
                        // Replace soft wall with grass after explosion
                        mapTileNum[col][row] = 3; // Change tile to grass (tile[0])
                        getGp().getPlayer().increaseScore(50);

                    } else if (explosionArea.intersects(tileRect)) {
                        mapTileNum[col][row] = 0; // Change tile to grass (tile[0])
                        getGp().getPlayer().increaseScore(50);
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

    /**
     * Gets the current map number.
     *
     * @return The number of the current map.
     */
    public int getCurrentMap() {
        return currentMap;
    }

    /**
     * Moves the door tile to the closest soft wall tile relative to the player's location.
     * This only applies in cheat mode. It ensures the door appears near the player rather than at random.
     *
     * @param playerX the column position of the player
     * @param playerY the row position of the player
     * @param isCheat true if cheat mode is enabled; false otherwise
     */
    public void moveDoorToClosestSoftWall(int playerX, int playerY, boolean isCheat) {
        if (!isCheat) return;
        int closestX = -1;
        int closestY = -1;
        int minDist = Integer.MAX_VALUE;

        for (int col = 0; col < mapTileNum.length; col++) {
            for (int row = 0; row < mapTileNum[0].length; row++) {
                if (mapTileNum[col][row] == 2) { // soft wall
                    int dist = Math.abs(playerX - col) + Math.abs(playerY - row);
                    if (dist < minDist) {
                        minDist = dist;
                        closestX = col;
                        closestY = row;
                    }
                }
            }
        }

        // If we found a valid tile, set it as the door
        if (closestX != -1 && closestY != -1) {
            mapTileNum[closestX][closestY] = 3; // 3 = door
            System.out.println("Moved door to: (" + closestX + ", " + closestY + ")");
        }
    }

    public Background getGp() {
        return gp;
    }
}
