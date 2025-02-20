package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class TileManager {

    Background gp;
    Tile[] tile;
    int[][] mapTileNum;


    public TileManager(Background gp) {
        this.gp = gp;
        tile = new Tile[10];
        mapTileNum = new int[gp.screenCols][gp.screenRows];
        getTileImg();
        loadMap();
    }

    public void getTileImg(){

        try{
            tile[0] = new Tile();
            tile[0].img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("src/storage/tiles/grass1.png")));

            tile[1] = new Tile();
            tile[1].img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("src/storage/tiles/wall1.png")));

            tile[2] = new Tile();
            tile[2].img = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("src/storage/tiles/softWall.png")));

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //Reading the map and loading it to a 2D array called mapTiles
    public void loadMap(){
        try{
            InputStream iS = getClass().getClassLoader().getResourceAsStream("src/storage/maps/level1.txt");
            assert iS != null;
            BufferedReader br = new BufferedReader(new InputStreamReader(iS));

            int col = 0;
            int row = 0;

            while(col < gp.screenCols && row < gp.screenRows){
                String line = br.readLine();
                while(col < gp.screenCols){
                    String[] numbers = line.split(" ");
                    int x = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = x;
                    col++;
                }
                if(col == gp.screenCols){
                    col = 0;
                    row++;
                }
            }
            br.close();

        } catch(Exception e){
            e.printStackTrace();
        }
    }


    public void draw(Graphics2D g2d){
        int col = 0;
        int row = 0;
        int x = 0;
        int y = 0;

        while(col < gp.screenCols && row < gp.screenRows){
            int tileNum = mapTileNum[col][row];
            
            g2d.drawImage(tile[tileNum].img,x,y,gp.tileSize,gp.tileSize,null);
            col++;
            x += gp.tileSize;
            if(col == gp.screenCols){
                col = 0;
                x = 0;
                row++;
                y += gp.tileSize;
            }
        }
    }
}
