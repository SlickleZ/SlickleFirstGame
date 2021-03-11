package item;

import Char.Player;
import utils.AudioPlayer;
import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class FullMpPotion extends Item implements Runnable{

    private BufferedImage potion;
    private final AudioPlayer audio;

    public FullMpPotion(int x, int y, Player player) {
        super(x, y, player);

        Thread t = new Thread(this, "HP Potion Thread");

        audio = new AudioPlayer("res\\Sound\\Effect\\Potion.wav", false);
        audio.setGain(-30);

        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            BufferedImage image = loader.loadImage("res\\TileSet\\Potions.png");
            SpriteSheet ss = new SpriteSheet(image);
            potion = ss.grabImage(2, 2, 32, 32);
        } catch (IOException e) {
            e.printStackTrace();
        }
        t.start();
    }

    @Override
    public void run() {
        collision();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(potion, x, y, 30, 30,  null);
        //g.drawRect(x, y, 24, 24);
    }

    @Override
    public boolean collision() {
        if(this.getBounds().intersects(player.getBounds())){
            audio.play();
            player.MP = player.MAX_MP;
            return true;
        }return false;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 30, 30);
    }
}
