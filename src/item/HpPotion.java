package item;

import Char.Player;
import utils.AudioPlayer;
import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

// potion : col 0 > 3 row 0 > 3 32x32
//render g.drawImage(test, 177, 200,24,24,null);

public class HpPotion extends Item implements Runnable{

    private BufferedImage potion;
    private final AudioPlayer audio;

    public HpPotion(int x, int y, Player player) {
        super(x, y, player);

        Thread t = new Thread(this, "HP Potion Thread");

        audio = new AudioPlayer("res\\Sound\\Effect\\Potion.wav", false);
        audio.setGain(-30);


        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            BufferedImage image = loader.loadImage("res\\TileSet\\Potions.png");
            SpriteSheet ss = new SpriteSheet(image);
            potion = ss.grabImage(0, 0, 32, 32);
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
        g.drawImage(potion, x, y, 24, 24,  null);
        //g.drawRect(x, y, 24, 24);
    }

    @Override
    public boolean collision() {
        if(this.getBounds().intersects(player.getBounds())){
            audio.play();
            if(player.HP + (25+(player.level*5)) > player.MAX_HP){
                player.HP = player.MAX_HP;
            }else{
                player.HP += 25+(player.level*5);
            }
            return true;
        }return false;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x, y, 24, 24);
    }
}
