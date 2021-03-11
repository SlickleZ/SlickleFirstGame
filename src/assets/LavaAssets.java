package assets;

import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class LavaAssets {

    //lava.grabImage(1, 1, 95, 100);
    //lava.grabImage(6, 1, 95, 100);
    //lava.grabImage(12, 1, 95, 100);
    //lava.grabImage(17, 1, 95, 100);
    //lava.grabImage(22, 1, 95, 100);
    //lava.grabImage(27, 1, 95, 100);

    //lava.grabImage(1, 3, 95, 100);
    //lava.grabImage(5, 3, 95, 100);
    //lava.grabImage(12, 3, 95, 100);
    //lava.grabImage(17, 3, 95, 100);
    //lava.grabImage(23, 3, 95, 100);
    //lava.grabImage(25, 3, 95, 100);

    private SpriteSheet lava;

    private final int[] col1 = {1, 6, 12, 17, 22, 27};
    private final int[] col2 = {1, 5, 12, 17, 23, 25};

    public  BufferedImage[] Lava1 = new BufferedImage[6];
    public  BufferedImage[] Lava2 = new BufferedImage[6];

    public void init(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            BufferedImage image = loader.loadImage("res\\TileSet\\animationlava.png");
            lava = new SpriteSheet(image);
        } catch (IOException e){ e.printStackTrace(); }

        for(int i = 0;i <= 5;i++){
            Lava1[i] = lava.grabImage(col1[i], 1, 95, 100);
        }

        for(int i = 0;i <= 5;i++){
            Lava2[i] = lava.grabImage(col2[i], 3, 95, 100);
        }
    }

}
