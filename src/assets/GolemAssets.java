package assets;

import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

// Up : row = 0; col 0 > 12, Only even
// Left : row = 2;
// Down : row = 4;
// Right : row = 6;

// Die : row = 2; col 0 > 12, Only even

// col 0 > 12, Only even
// row 1 = up attack
// row 4 = left attack
// row 7 = down attack
// row 10 = right attack

/* This class for prepare arrays of animation frame, It'll make you can use it with utils.Animation easily! */

public class GolemAssets {

    private SpriteSheet b1, b2, b3;

    public BufferedImage[] bUp = new BufferedImage[7];
    public BufferedImage[] bDown = new BufferedImage[7];
    public BufferedImage[] bLeft = new BufferedImage[7];
    public BufferedImage[] bRight = new BufferedImage[7];

    public BufferedImage[] bDie = new BufferedImage[7];

    public BufferedImage[] bAttUp = new BufferedImage[7];
    public BufferedImage[] bAttDown = new BufferedImage[7];
    public BufferedImage[] bAttLeft = new BufferedImage[7];
    public BufferedImage[] bAttRight = new BufferedImage[7];

    public void init(){
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            BufferedImage golem = loader.loadImage("res\\Sprites\\Enemies\\Golem\\golem-walk.png");
            BufferedImage golem2 = loader.loadImage("res\\Sprites\\Enemies\\Golem\\golem-atk.png");
            BufferedImage golem3 = loader.loadImage("res\\Sprites\\Enemies\\Golem\\golem-die.png");
            b1 = new SpriteSheet(golem);
            b2 = new SpriteSheet(golem2);
            b3 = new SpriteSheet(golem3);
        }catch(IOException e){
            e.printStackTrace();
        }

        for(int i= 0;i<=6;i++){
            bAttDown[i] = b2.grabImage(2*i, 7, 55, 60);
            bDown[i] = b1.grabImage(2*i, 4, 55, 60);
            bAttUp[i] = b2.grabImage(2*i, 1, 55, 60);
            bUp[i] = b1.grabImage(2*i, 0, 55, 60);
            bAttLeft[i] = b2.grabImage(2*i, 4, 55, 60);
            bLeft[i] = b1.grabImage(2*i, 2, 55, 60);
            bAttRight[i] = b2.grabImage(2*i, 10, 55, 60);
            bRight[i] = b1.grabImage(2*i, 6, 55, 60);
            bDie[i] = b3.grabImage(2*i, 2, 55, 60);
        }
    }

    public BufferedImage getGolemIdleFrame() { return b1.grabImage(0, 4, 55, 60); }
}
