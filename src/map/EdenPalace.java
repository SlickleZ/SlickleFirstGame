package map;

import Char.*;
import item.*;
import utils.AudioPlayer;
import utils.BufferedImageLoader;
import utils.SpriteSheet;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;


public class EdenPalace extends Map implements  Runnable{

    private BufferedImage crossEmo;
    private BufferedImage image;
    private BufferedImage map00;


    private final ID id;
    private final Player player;
    private boolean respawnFlag = true;
    private long respawnTime_count = 0;

    /* ArrayList for each blocks */
    private final LinkedList<Rectangle> blocks = new LinkedList<>();
    private final LinkedList<Item> listOfItems = new LinkedList<>();

    /*  Declare x,y,width height of each blocks  */
    private final int[] x={0,240,0,236,328,130,1130,380,585,775,775,380,425,590,680,880,975,
                    1190,650,640,525};
    private final int[] y={0,0,500,500,400,850,740,480,480,410,640,250,0,160,0,0,160,270,
                    410,270,335};
    private final int[] width={160,140,130,144,30,1000,230,130,190,340,165,45,80,320,200,450,
                    350,150,90,90,50};
    private final int[] height={400,400,420,280,100,100,110,300,370,235,130,200,270,110,170,150,
                    110,390,80,60,50};

    public EdenPalace(Player player, ID id){
        this.player = player;
        this.id = id;
        Thread t = new Thread(this, "Map0 Thread");

        BG = new AudioPlayer("res\\Sound\\BGM\\OST 35_ TeMPoison.wav", true);
        BG.setGain(-35);
        BG.stop();

        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            map00 = loader.loadImage("res\\TileSet\\Eden Palace.png");
            BufferedImage aura = loader.loadImage("res\\Skill\\Aura38.png");
            SpriteSheet ss = new SpriteSheet(aura);

            /* Emoticon for no entry area */
            BufferedImage emo = loader.loadImage("res\\GUI\\Emote\\emote_cross.png");
            SpriteSheet ss2 = new SpriteSheet(emo);
            crossEmo = ss2.grabImage(0, 0, 32, 38);

            /* Grab sub image from sprite sheet */
            image = ss.grabImage(0,0,110,110);
        }catch (IOException e){
            e.printStackTrace();
        }
        t.start();
    }

    @Override
    public void run() { addBlock();  addItem(); }

    @Override
    public boolean mapCollision(GameObject obj){
        for (Iterator<Rectangle> itr = blocks.iterator(); itr.hasNext();){
            Rectangle rec = itr.next();
            if(obj.getBounds().intersects(rec)){
                obj.playerGetBlocked();
                return true;
            }
        }return false;
    }

    private void addBlock(){
        for(int i=0;i<=20;i++){
            blocks.add(new Rectangle(x[i], y[i], width[i], height[i]));
        }
    }

    public void checkRespawn(){
        if(listOfItems.isEmpty()) {
            if(respawnFlag){
                respawnTime_count = System.currentTimeMillis();
                respawnFlag = false;
            }
            long now = System.currentTimeMillis();
            if ((now) - (respawnTime_count) >= 30000) {
                addItem();
                respawnFlag = true;
            }
        }
    }

    private void addItem(){
        listOfItems.add(new MpPotion(945, 710, player));
        listOfItems.add(new HpPotion(805, 810, player));
        listOfItems.add(new AttPotion(928, 150, player));
        listOfItems.add(new FullHpPotion(500, 150, player));
        listOfItems.add(new FullMpPotion(520, 120, player));
    }

    public void tick(GameObject player) {
        BG.play();
        mapCollision(player);
        listOfItems.removeIf(Item::collision);
        checkRespawn();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(map00,0,0, null);
        g.drawImage(image,143,8,null);
        for(Iterator<Item> iter = listOfItems.iterator(); iter.hasNext();){
            Item item = iter.next();
            item.render(g);
        }
        if(noEntry()){
            g.drawImage(crossEmo, player.getX()+15, player.getY()-30, null);
        }
//        /* Castle */
//        g.drawRect(0, 0, 160, 400);
//        g.drawRect(240, 0, 140, 400);
//        g.drawRect(0, 500, 130, 420);
//        g.drawRect(236, 500, 144,280);
//        g.drawRect(328, 400, 30 , 100);
//
//        /* path and forest */
//        g.drawRect(130, 850, 1000, 100);
//        g.drawRect(1130, 740, 230, 110);
//        g.drawRect(380, 480, 130, 300);
//        g.drawRect(585, 480, 190, 370);
//        g.drawRect(775, 410, 340, 235);
//        g.drawRect(775, 640, 165, 130);
//
//        /* Beside the castle */
//        g.drawRect(380, 250, 45, 200);
//        g.drawRect(425, 0, 80, 270);
//        g.drawRect(590, 160, 320, 110);
//        g.drawRect(680, 0, 200, 170);
//        g.drawRect(880, 0, 450, 150);
//        g.drawRect(975, 160, 350, 110);
//        g.drawRect(1190, 270, 150, 390);
//        g.drawRect(650, 410, 90,80);
//        g.drawRect(640, 270, 90, 60);
//        g.drawRect(525, 335, 50, 50);
    }


    public boolean noEntry(){
        return (player.getX() >= 825 && player.getX() <= 860) && (player.getY() == 730);
    }

    @Override
    public void setObjectPosition(int x, int y, GameObject player) {
        player.setX(x);
        player.setY(y);
    }

    public boolean toNextMap(){
        return player.getX() >= 1312 && player.getY() >= 630;
    }

    public ID getID(){ return id; }
}
