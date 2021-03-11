package map;

import Char.ID;
import Char.Player;
import item.*;
import utils.AudioPlayer;
import utils.BufferedImageLoader;
import Char.GameObject;
import utils.SpriteSheet;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class EdenVille extends Map implements Runnable{

    private BufferedImage crossEmo;
    private BufferedImage map01;
    private final ID id;
    private final Player player;

    private boolean respawnFlag = true;
    private long respawnTime_count = 0;

    private final LinkedList<Item> listOfItems = new LinkedList<>();

    /* ArrayList for each blocks */
    private final LinkedList<Rectangle> blocks = new LinkedList<>();

    /* Declare x,y,width height of each blocks */
    private final int[] x={0,0,0,360,550,650,715,550,0,550,1060,1060,1255,1170};
    private final int[] y={0,245,410,245,170,245,265,375,725,670,160,350,160,390};
    private final int[] width={550,305,500,140,100,65,50,570,550,800,145,55,100,180};
    private final int[] height={170,165,250,165,150,20,110,240,25,50,190,25,230,225};

    public EdenVille(Player player, ID id) {
        this.player = player;
        this.id = id;
        Thread t = new Thread(this, "Map1 Thread");

        BG = new AudioPlayer("res\\Sound\\BGM\\OST 100_ Abyss.wav", true);
        BG.setGain(-40);
        BG.stop();

        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            map01 = loader.loadImage("res\\TileSet\\Eden Village.jpg");
            BufferedImage emo = loader.loadImage("res\\GUI\\Emote\\emote_cross.png");
            SpriteSheet ss = new SpriteSheet(emo);
            crossEmo = ss.grabImage(0, 0, 32, 38);
        }catch (IOException e){
            e.printStackTrace();
        }
        t.start();
    }

    @Override
    public void run() {
        addBlock(); addItem();
    }

    private void addBlock(){
        for(int i=0;i<=13;i++){
            blocks.add(new Rectangle(x[i], y[i],width[i],height[i]));
        }
    }

    public void tick(GameObject player) {
        BG.play();
        mapCollision(player);
        listOfItems.removeIf(Item::collision);
        checkRespawn();
    }

    public boolean mapCollision(GameObject obj){
        for (Iterator<Rectangle> it = blocks.iterator(); it.hasNext();){
            Rectangle rec = it.next();
            if(obj.getBounds().intersects(rec)){
                obj.playerGetBlocked();
                return true;
            }
        }return false;
    }

    public void render(Graphics g) {
        g.drawImage(map01, 0, 0, null);
        for(Iterator<Item> iter = listOfItems.iterator(); iter.hasNext();){
            Item item = iter.next();
            item.render(g);
        }
        if(noEntry()){
            g.drawImage(crossEmo, player.getX()+15, player.getY()-30, null);
        }

//        /* Home */
//        g.drawRect(0,0, 550, 170);
//        g.drawRect(0, 245, 305, 165);
//        g.drawRect(0, 410, 500, 250);
//        g.drawRect(360, 245, 140, 165);
//        g.drawRect(550, 170, 100, 150);
//        g.drawRect(650, 245, 65, 20);
//        /* Forest */
//        g.drawRect(715, 265, 50, 110);
//        g.drawRect(550, 375, 570, 240);
//        g.drawRect(0, 725, 550, 25);
//        g.drawRect(550, 670,800, 50);
//
        /* Behind the cave */
//        g.drawRect(1060, 160, 145, 190);
//        g.drawRect(1060, 350, 55, 25);
//        g.drawRect(1255, 160, 100, 230);
//        g.drawRect(1170, 390, 180, 225);
    }

    private void addItem(){
        listOfItems.add(new MpPotion(470, 160, player));
        listOfItems.add(new HpPotion(280, 160, player));
        listOfItems.add(new FullMpPotion(660, 280, player));
        listOfItems.add(new AttPotion( 50, 200, player));
    }

    public void checkRespawn(){
        if(listOfItems.isEmpty()) {
            if(respawnFlag){
                respawnTime_count = System.currentTimeMillis();
                respawnFlag = false;
            }
            long now = System.currentTimeMillis();
            if ((now) - (respawnTime_count) >= 30000){
                addItem();
                respawnFlag = true;
            }
        }
    }

    public boolean noEntry(){
        if((player.getX() >= 12 && player.getX() <= 40) && (player.getY() <= 132)){
            return true;
        }else if((player.getX() >= 252 && player.getX() <= 276) && (player.getY() <= 132)){
            return true;
        }else if((player.getX() >= 436 && player.getX() <= 468) && (player.getY() <= 132)){
            return true;
        }else return (player.getX() >= 640 && player.getX() <= 664) && (player.getY() <= 240);
    }


    public boolean toNextMap(){ return (player.getX() <= -10 && player.getY() >= 620); }
    public boolean toNextMap1(){ return (player.getX()>=1180 && player.getY() <= 145); }
    public boolean toNextMap2() { return (player.getX()>=1312 && player.getY() >= 577); }

    public void setObjectPosition(int x, int y, GameObject player){
        player.setX(x);
        player.setY(y);
    }

    public ID getID(){ return id; }
}
