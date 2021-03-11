package assets;

import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class TitleAssets {

    // t.grabImage(0, 2, 715, 105); col : 0, 24 row : 2, 6, 10, 14, 18
    private SpriteSheet image;
    private int index = 0;
    private final int[] col ={0, 24};
    private final int[] row ={2, 6};

    public BufferedImage[] title = new BufferedImage[4];

    public void init() {
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            BufferedImage img = loader.loadImage("res\\GUI\\Abyss_odyssey.png");
            image = new SpriteSheet(img);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i = 0; i <= 1; i++) {
            for(int j = 0;j<=1; j++) {
                title[index++] = image.grabImage(col[j], row[i], 715, 105);
            }
        }
    }
}
