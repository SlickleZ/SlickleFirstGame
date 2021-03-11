package assets;

// Imp set-up : ss.grabImage(6,6,62,60); render : w = 80, h = 80
// Imp Up & att Up : row : 0, col : 0 > 6
// Imp Left & att Left row : 2, col : 0 > 6
// Imp Down & att Down row : 4, col : 0 > 6
// Imp Right & att Right row : 6, col : 0 > 6
// Imp Die row : 0, col 0 > 12, Only even

import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImpAssets {

    private SpriteSheet Imp, ImpAtt, ImpDie;

    public BufferedImage[] up = new BufferedImage[4];
    public BufferedImage[] down = new BufferedImage[4];
    public BufferedImage[] left = new BufferedImage[4];
    public BufferedImage[] right = new BufferedImage[4];

    public BufferedImage[] attUp = new BufferedImage[4];
    public BufferedImage[] attDown = new BufferedImage[4];
    public BufferedImage[] attLeft = new BufferedImage[4];
    public BufferedImage[] attRight = new BufferedImage[4];

    public BufferedImage[] die = new BufferedImage[7];

    public void init(){

        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            BufferedImage image = loader.loadImage("res\\Sprites\\Enemies\\LPC Imp 2\\red\\walk - pitchfork.png");
            BufferedImage image2 = loader.loadImage("res\\Sprites\\Enemies\\LPC Imp 2\\red\\attack - pitchfork.png");
            BufferedImage image3 = loader.loadImage("res\\Sprites\\Enemies\\LPC Imp 2\\red\\imp death.png");
            Imp = new SpriteSheet(image);
            ImpAtt = new SpriteSheet(image2);
            ImpDie = new SpriteSheet(image3);

        } catch (IOException e){ e.printStackTrace(); }

        for(int i=0; i<=3; i++){
            up[i] = Imp.grabImage(2*i, 0, 62, 60);
            attUp[i] = ImpAtt.grabImage(2*i, 0, 62, 60);
            down[i] = Imp.grabImage(2*i, 4, 62, 60);
            attDown[i] = ImpAtt.grabImage(2*i, 4, 62, 60);
            left[i] = Imp.grabImage(2*i, 2, 62, 60);
            attLeft[i] = ImpAtt.grabImage(2*i, 2, 62, 60);
            right[i] = Imp.grabImage(2*i, 6, 62, 60);
            attRight[i] = ImpAtt.grabImage(2*i, 6, 62, 60);
        }
        for (int i=0;i<=6;i++){
            die[i] = ImpDie.grabImage(2*i, 0, 62, 60);
        }
    }

    public BufferedImage getImpIdleFrame(){ return down[0]; }
}
