package map;

import Char.*;
import assets.LavaAssets;
import assets.WarpAssets;
import item.*;
import utils.AudioPlayer;
import utils.BufferedImageLoader;
import utils.Animation;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class DeadlyLava extends Map implements Runnable{

    private final ID id;
    private final Player player;
    private int change = 0;

    private boolean respawnFlag = true;
    private long respawnTime_count = 0;

    /* for render map */
    private BufferedImage map03;

    /* Animation for each animation */
    private final Animation warp, lava1, lava2, lava3, lava4, lava5, lava6;

    /* LinkedList for each item in this map */
    private final LinkedList<Item> listOfItems = new LinkedList<>();

    /* LinkedList for each blocks */
    public final LinkedList<Rectangle> blocks = new LinkedList<>();

    /* LinkedList for each objects in this map */
    public final LinkedList<GameObject> objects = new LinkedList<>();

    /*  Declare x,y,width height of each blocks  */
    private final int[] x={-10,240,790,860,990,-10,890,240};
    private final int[] y={380,220,280,220,350,530,520,590};
    private final int[] width={250,550,70,130,50,250,100,650};
    private final int[] height={70,160,40,130,170,60,70,50};

    public DeadlyLava(Player player,ID id){
        this.id = id;
        this.player = player;
        Thread t = new Thread(this, "Deadly Lava Thread");

        BG = new AudioPlayer("res\\Sound\\BGM\\OST 40_ Fear....wav", true);
        BG.setGain(-35);
        BG.stop();

        WarpAssets warpAssets = new WarpAssets();
        LavaAssets lavaAssets = new LavaAssets();

        /* initial animation assets for render animation */
        warpAssets.init();
        lavaAssets.init();

        /* Create instances for animation with its speed */
        warp  = new Animation(50, warpAssets.warp);
        lava1 = new Animation(250, lavaAssets.Lava1);
        lava2 = new Animation(250, lavaAssets.Lava2);
        lava3 = new Animation(250, lavaAssets.Lava1);
        lava4 = new Animation(250, lavaAssets.Lava1);
        lava5 = new Animation(250, lavaAssets.Lava2);
        lava6 = new Animation(250, lavaAssets.Lava1);

        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            map03 = loader.loadImage("res\\TileSet\\Deadly Lava.jpg");
        }catch (IOException e){
            e.printStackTrace();
        }

        objects.add(new DarkMage(player, 860, 400, 20, ID.ENEMY));

        t.start();
    }


    @Override
    public void run() {
        addBlock(); addItem();
    }

    @Override
    public void tick(GameObject player) {
        checkPlayerWin();
        BG.play();
        mapCollision(player);

        if(change == 3){ warp.tick(); }

        lava1.tick();
        lava2.tick();
        lava3.tick();
        lava4.tick();
        lava5.tick();
        lava6.tick();

        /* Check collision for enemies */
        for(Iterator<GameObject> itr = objects.iterator(); itr.hasNext();)
        {
            GameObject enemy = itr.next();
            mapCollision(enemy);
            enemy.tick();
        }
        listOfItems.removeIf(Item::collision);
        checkRespawn();
        checkDie();
    }

    public boolean mapCollision(GameObject obj){
        for (Iterator<Rectangle> itr = blocks.iterator(); itr.hasNext();){
            Rectangle rec = itr.next();
            if(obj.getBounds().intersects(rec)){
                obj.playerGetBlocked();
                return true;
            }
        }return false;
    }

    @Override
    public boolean objectCollisionWithDamage(GameObject obj) {
        for(Iterator<GameObject> itr = objects.iterator(); itr.hasNext();){
            GameObject enemy = itr.next();
            if (obj.getBounds().intersects(enemy.getBounds())) {
                enemy.getDamage(obj.damage);
                return true;
            }
        }return false;
    }

    public void checkDie(){
        for (Iterator<GameObject> itr = objects.iterator(); itr.hasNext();){
            GameObject enemy = itr.next();
            if (enemy.dieFlag == 1)
            {
                objects.remove(enemy);
                change++;
            }
        }
        if(change == 1){
            listOfItems.add(new AttPotion(780, 450, player));
            objects.add(new Imp(player, 860, 400, 30, ID.ENEMY));
            change++;
        }
    }

    public void checkRespawn(){
        if(listOfItems.isEmpty()) {
            if(respawnFlag){
                respawnTime_count = System.currentTimeMillis();
                respawnFlag = false;
            }
            long now = System.currentTimeMillis();
            if ((now) - (respawnTime_count) >= 15000){
                addItem();
                respawnFlag = true;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(map03, 0, 0, null);

        if(change == 3){
            g.drawImage(warp.getCurrentFrame(), 800, 300, 60, 60, null);
        }

        for(Iterator<Item> iter = listOfItems.iterator(); iter.hasNext();){
            Item item = iter.next();
            item.render(g);
        }

        for(Iterator<GameObject> itr = objects.iterator(); itr.hasNext();){
            GameObject enemy = itr.next();
            enemy.render(g);
        }

        g.drawImage(lava1.getCurrentFrame(), 110, 530, null);
        g.drawImage(lava2.getCurrentFrame(), 40, 190, null);
        g.drawImage(lava3.getCurrentFrame(), 540, 100, null);
        g.drawImage(lava4.getCurrentFrame(), 500, 750, null);
        g.drawImage(lava5.getCurrentFrame(), 1120, 350, null);
        g.drawImage(lava6.getCurrentFrame(), 800, 120, null);

//        g.drawRect(-10, 380, 250, 70);
//        g.drawRect(240, 220, 550, 160);
//        /* Between Warp portal */
//        g.drawRect(790, 280, 70, 40);
//        g.drawRect(860, 220, 130, 130);
//        g.drawRect(990, 350, 50, 170);
//
//
//        g.drawRect(-10, 530, 250, 60);
//        g.drawRect(890, 520, 100, 70);
//        g.drawRect(240,590, 650, 50);

    }

    private void checkPlayerWin(){
        if(change==3 && ((player.getX()>= 778 && player.getX()<=818) && (player.getY()<=306))){
            player.win = true;
        }
    }

    private void addBlock(){
        for(int i=0;i<=7;i++){
            blocks.add(new Rectangle(x[i], y[i], width[i], height[i]));
        }
    }

    private void addItem(){
        listOfItems.add(new FullMpPotion(470, 370, player));
        listOfItems.add(new HpPotion(480, 560, player));
        listOfItems.add(new HpPotion(600, 370, player));
        listOfItems.add(new MpPotion(620, 570, player));
    }

    public boolean toNextMap(){ return player.getX() == -10 && player.getY() >= 422; }
    public ID getId() {
        return id;
    }

}
