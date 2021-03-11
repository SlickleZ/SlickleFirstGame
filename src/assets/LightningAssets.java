package assets;

import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class LightningAssets {

    private SpriteSheet l;
    private int index = 0;

    public BufferedImage[] lightning = new BufferedImage[16];

    public void init() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            BufferedImage test = loader.loadImage("res\\Skill\\magic_pack\\sheets\\lightningclaw.png");
            l = new SpriteSheet(test);

        } catch (IOException e) { e.printStackTrace(); }

        for(int i=0;i<=12;i++){
            for(int j=0;j<=12;j++){
                if(i%4==0 && j%4==0){
                    lightning[index++] = l.grabImage(j, i, 120, 120);
                }
            }
        }
    }
}
