package assets;

import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cloud implements Runnable{

    private int x;
    private final int y;
    private final int speed;
    private long move = 0;
    private final BufferedImage cloud;

    public Cloud(int x, int y, int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;

        Thread t = new Thread(this, "Cloud Thread");

        BufferedImageLoader loader = new BufferedImageLoader();
        BufferedImage img = null;
        try {
            img = loader.loadImage("res\\GUI\\startmenu.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SpriteSheet image = new SpriteSheet(img);
        cloud = image.grabImage(0, 12, 565, 150);
        t.start();
    }

    @Override
    public void run() {
        tick();
    }

    public void tick(){
        long move_cd = System.currentTimeMillis();
        if(move_cd > move + speed){
            x+=5;
            move = move_cd;
        }
    }

    public void render(Graphics g){
        g.drawImage(cloud, x, y, null);
    }
    public int getX() { return x; }
}
