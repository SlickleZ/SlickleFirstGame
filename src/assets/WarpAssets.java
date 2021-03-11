package assets;

import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class WarpAssets {

    // warp : 32x32 render with 60x60
    //lava.grabImage(0, 0, 32, 32); col 0 > 3
    //lava.grabImage(0, 1, 32, 32);
    //lava.grabImage(0, 2, 32, 32);
    //lava.grabImage(0, 3, 32, 32);
    //lava.grabImage(0, 4, 32, 32); only 0

    private SpriteSheet portal;

    public  BufferedImage[] warp = new BufferedImage[17];

    public void init(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            BufferedImage image = loader.loadImage("res\\TileSet\\portalRings1.png");
            portal = new SpriteSheet(image);
        } catch (IOException e){ e.printStackTrace(); }

        for(int i = 0, index = 0; i <= 3; i++){
            for(int j = 0; j<=3; j++){
                warp[index] = portal.grabImage(j, i, 32, 32);
                index++;
            }
        }
        warp[16] = portal.grabImage(0, 4, 32, 32);
    }

}
