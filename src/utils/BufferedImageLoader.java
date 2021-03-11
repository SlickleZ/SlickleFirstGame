package utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BufferedImageLoader {

    /* Load the image from the specific path */
    public BufferedImage loadImage(String path) throws IOException {
        File obj = new File(path);
        return ImageIO.read(obj);
    }
}
