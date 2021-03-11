package states;


import assets.Cloud;
import assets.TitleAssets;
import main.Game;
import utils.Animation;
import utils.AudioPlayer;
import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class MenuState extends State implements Runnable{


    Game game;
    private final LinkedList<Cloud> clouds = new LinkedList<>();
    private BufferedImage bg, start, howTo, exit;
    private Animation title;


    public MenuState(Game game){
        this.game = game;

        Thread t = new Thread(this, "Menu Thread");

        BG = new AudioPlayer("res\\Sound\\BGM\\MENU.wav", true);
        BG.setGain(-25);
        BG.play();

        BufferedImageLoader loader = new BufferedImageLoader();
        TitleAssets assets = new TitleAssets();
        assets.init();
        try {
            bg = loader.loadImage("res\\GUI\\Menu.png");
            BufferedImage img = loader.loadImage("res\\GUI\\startmenu.png");
            SpriteSheet image = new SpriteSheet(img);

            start = image.grabImage(0, 0, 510, 150);
            howTo = image.grabImage(0, 5, 510, 150);
            exit = image.grabImage(0, 9, 510, 150);

            title = new Animation(250, assets.title);
        } catch (IOException e) {
            e.printStackTrace();
        }

        addCloud();
        t.start();
    }

    @Override
    public void run() {
        tick();
    }

    public void addCloud(){
        clouds.add(new Cloud(-500, 300, 250));
        clouds.add(new Cloud(-420, 400, 150));
        clouds.add(new Cloud(-340, 500, 200));
    }

    @Override
    public void tick() {

        title.tickWithNoRst();
        for(Iterator<Cloud> it = clouds.iterator(); it.hasNext();){
            Cloud cloud = it.next();
            cloud.tick();
            if(cloud.getX() >= 965){
                it.remove();
            }
        }
        checkCloud();
    }

    public void checkCloud() {
        if(clouds.isEmpty()){
            addCloud();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bg, 0, 0, null);
        for(Iterator<Cloud> it = clouds.iterator(); it.hasNext();){
            Cloud cloud = it.next();
            cloud.render(g);
        }
        g.drawImage(title.getCurrentFrame(), 150, 70, null);
        g.drawImage(start, 300, 150, 457, 150,null);
        g.drawImage(howTo, 260, 300, 457, 150, null);
        g.drawImage(exit, 300, 420, 457, 150, null);

    }
    public String getName() { return "Menu State"; }

}
