package utils;

import java.awt.image.BufferedImage;

public class SpriteSheet {
    private final BufferedImage image;

    public SpriteSheet(BufferedImage image){
        this.image = image;
    }

    /* Grab image from the sprite sheet (grab by sub image) */
    public BufferedImage grabImage(int col, int row, int width, int height){
        return image.getSubimage((col*32), (row * 32), width, height);
    }
}
