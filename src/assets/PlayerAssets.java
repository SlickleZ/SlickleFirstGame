package assets;


import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

/* This class existed for prepare the animation for all sprites */

// att down test.grabImage(10, 28, 72, 62) col 0 > 10, Only even
// die test.grabImage(0, 40, 72, 62) col: 0 > 10, Only even

public class PlayerAssets{

    private  SpriteSheet p1;

    public BufferedImage[] pDown = new BufferedImage[9];
    public BufferedImage[] pUp = new BufferedImage[9];
    public BufferedImage[] pLeft = new BufferedImage[9];
    public BufferedImage[] pRight = new BufferedImage[9];
    public BufferedImage[] pAttackDown = new BufferedImage[6];
    public BufferedImage[] pDie = new BufferedImage[6];
    public BufferedImage[] pSkill1 = new BufferedImage[6];

    public void init() {
        try {
            /* Tools for crop image */
            BufferedImageLoader loader = new BufferedImageLoader();

            /* Load image for each sprites */
            BufferedImage player = loader.loadImage("res\\Sprites\\Male\\universal-lpc-sprite_male_01_full1.png");
            p1 = new SpriteSheet(player);

        } catch (IOException e) {
            e.printStackTrace();
        }
        /* Initial animation for player */
        for (int i = 0; i <= 5; i++) {
            pSkill1[i] = p1.grabImage(2 * i, 4, 55, 62);
            pDie[i] = p1.grabImage(2 * i,40, 72, 62);
            pAttackDown[i] = p1.grabImage(2 * i, 28, 72, 62);
        }

        for (int i = 0; i <= 8; i++) {
            pDown[i] = p1.grabImage(2 * i, 20, 55, 62);
            pUp[i] = p1.grabImage(2 * i, 16, 55, 62);
            pLeft[i] = p1.grabImage(2 * i, 18, 55, 62);
            pRight[i] = p1.grabImage(2 * i, 22, 55, 62);
        }

    }

    public BufferedImage getpIdleFrame(){
        return p1.grabImage(0, 20, 55, 62);
    }
}
