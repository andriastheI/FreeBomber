package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOError;
import java.io.IOException;
import java.util.Objects;

public class TileManager {

    Background bg;
    Tile[] tile;


    public TileManager(Background bg) {
        this.bg = bg;
        tile = new Tile[10];
    }

    public void getTileImg(){

        try{
            tile[0] = new Tile();
            tile[0].img = ImageIO.read(Objects.requireNonNull(getClass().getResource("src/storage/wall1.png")));

            tile[1] = new Tile();
            tile[1].img = ImageIO.read(Objects.requireNonNull(getClass().getResource("src/storage/softWall.png")));

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
