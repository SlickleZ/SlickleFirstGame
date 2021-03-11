package assets;

import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

/* This class for prepare arrays of animation frame, It'll make you can use it with utils.Animation easily! */

public class GoblinAssets {

    private  SpriteSheet e1;

    public  BufferedImage[] eDown = new BufferedImage[5];
    public  BufferedImage[] eUp = new BufferedImage[5];
    public  BufferedImage[] eRight = new BufferedImage[5];
    public  BufferedImage[] eLeft = new BufferedImage[5];

    public BufferedImage[] eDie = new BufferedImage[5];

    public  BufferedImage[] eAttDown = new BufferedImage[6];
    public  BufferedImage[] eAttUp = new BufferedImage[6];
    public  BufferedImage[] eAttRight = new BufferedImage[6];
    public  BufferedImage[] eAttLeft = new BufferedImage[6];

    public void init(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            BufferedImage goblin = loader.loadImage("res\\Sprites\\Enemies\\Goblin\\goblinsword.png");
            e1 = new SpriteSheet(goblin);
        } catch (IOException e){ e.printStackTrace(); }

        /* Initial animation for enemies */
        for(int i= 0;i<=4;i++){
            eDown[i] = e1.grabImage(2*i, 0, 55, 60);
            eUp[i] = e1.grabImage(2*i, 4, 55, 60);
            eRight[i] = e1.grabImage(2*i, 2, 55, 60);
            eLeft[i] = e1.grabImage(2*i, 6, 55, 60);
            eDie[i] = e1.grabImage(2*i, 8, 55, 60);
        }

        for(int i= 0;i<=5;i++){
            eAttDown[i] = e1.grabImage((2*i)+10, 0, 55, 60);
            eAttUp[i] = e1.grabImage((2*i)+10, 4, 55, 60);
            eAttRight[i] = e1.grabImage((2*i)+10, 2, 55, 60);
            eAttLeft[i] = e1.grabImage((2*i)+10, 6, 55, 60);
        }
    }
    public  BufferedImage getGoblinIdleFrame() { return e1.grabImage(0, 0, 50, 60); }
}
