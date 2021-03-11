package assets;

// Boss setup : test.grabImage(16, 0, 55, 62)
// Up : row = 0 ; col 0 > 16 only even
// Left : row = 2 ;
// Down : row = 4 ;
// Right : row = 6 ;

// Die : row = 0 ; another file, col 0 > 10, only even

// Cast Up : row = 0 ; col 0 > 12, only even
// Cast Left : row = 2 ;
// Cast Down : row = 4 ;
// Cast Right: row = 6 ;

import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class DarkMageAssets {

    private SpriteSheet Boss;
    private SpriteSheet BossAtt;
    private SpriteSheet BossDie;

    public  BufferedImage[] eDown = new BufferedImage[9];
    public  BufferedImage[] eUp = new BufferedImage[9];
    public  BufferedImage[] eRight = new BufferedImage[9];
    public  BufferedImage[] eLeft = new BufferedImage[9];

    public  BufferedImage[] eAttDown = new BufferedImage[7];
    public  BufferedImage[] eAttUp = new BufferedImage[7];
    public  BufferedImage[] eAttRight = new BufferedImage[7];
    public  BufferedImage[] eAttLeft = new BufferedImage[7];

    public  BufferedImage[] eDie = new BufferedImage[6];

    public void init() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            BufferedImage image = loader.loadImage("res\\Sprites\\Enemies\\Boss\\mage walking poses sheet copy.png");
            BufferedImage image2 = loader.loadImage("res\\Sprites\\Enemies\\Boss\\Mage spell casting sheet copy.png");
            BufferedImage image3 = loader.loadImage("res\\Sprites\\Enemies\\Boss\\mage fall down sheet copy.png");

            Boss = new SpriteSheet(image);
            BossAtt = new SpriteSheet(image2);
            BossDie = new SpriteSheet(image3);
        } catch (IOException e){ e.printStackTrace(); }

        for (int i = 0;i <= 8;i++){
            eDown[i] = Boss.grabImage(2*i, 4, 55, 62);
            eUp[i] = Boss.grabImage(2*i, 0, 55, 62);
            eLeft[i] = Boss.grabImage(2*i, 2, 55, 62);
            eRight[i] = Boss.grabImage(2*i, 6, 55, 62);
        }

        for(int i = 0; i<=6; i++){
            eAttDown[i] = BossAtt.grabImage(2*i, 4, 55, 62);
            eAttUp[i] = BossAtt.grabImage(2*i, 0, 55, 62);
            eAttLeft[i] = BossAtt.grabImage(2*i, 2, 55, 62);
            eAttRight[i] = BossAtt.grabImage(2*i, 6, 55, 62);
        }

        for(int i = 0; i<=5; i++){
            eDie[i] = BossDie.grabImage(2*i, 0, 55, 62);
        }
    }
    public BufferedImage getDarkMageIdleFrame(){ return eDown[0];}
}
