package map;

import Char.Goblin;
import Char.Golem;
import Char.ID;
import Char.Player;
import item.*;
import utils.AudioPlayer;
import utils.BufferedImageLoader;
import Char.GameObject;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;

public class MysteriesCave extends Map implements Runnable {

    private BufferedImage map02;
    private final Player player;

    private final ID id;
    private long respawnTime_count, respawnItemTime_count;
    private boolean respawnFlag = true, respawnItemFlag = true;

    private final LinkedList<Item> listOfItems = new LinkedList<>();
    public LinkedList<GameObject> mapObjects = new LinkedList<>();
    private final LinkedList<Rectangle> blocks = new LinkedList<>();


    /*  Declare x,y,width height of each blocks  */
    private final int[] x={180,240,290,430,765,1275,960,935,1070,1325,1205,240,410,620};
    private final int[] y={0,100,100,260,820,600,130,130,130,130,130,920,920,130};
    private final int[] width={60,50,1070,335,510,80,95,20,20,20,45,20,20,105};
    private final int[] height={950,60,30,650,30,250,520,40,40,40,50,30,30,30};


    public MysteriesCave(Player player, ID id){
        this.id = id;
        this.player = player;
        Thread t = new Thread(this, "Map2 Thread");

        BG = new AudioPlayer("res\\Sound\\BGM\\OST 19_ Under the ground.wav", true);
        BG.setGain(-35);
        BG.stop();

        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            map02 = loader.loadImage("res\\TileSet\\Mysterious Cave.png");
        }catch (IOException e){
            e.printStackTrace();
        }
        t.start();
    }

    public void run(){
        addBlock(); addEnemy(); addItem();
    }


    public void tick(GameObject player) {
        BG.play();
        mapCollision(player);
        /* Check collision for enemies */
        for(Iterator<GameObject> itr = mapObjects.iterator(); itr.hasNext();) {
            GameObject enemy = itr.next();
            mapCollision(enemy);
            enemy.tick();
        }
        listOfItems.removeIf(Item::collision);
        checkRespawn();
        checkEnemyRespawn();
        checkDie();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(map02, 0,0, null);
        for(Iterator<GameObject> itr = mapObjects.iterator(); itr.hasNext();){
            GameObject enemy = itr.next();
            enemy.render(g);
        }
        for(Iterator<Item> iter = listOfItems.iterator(); iter.hasNext();){
            Item item = iter.next();
            item.render(g);
        }
    }

    private void addBlock(){
        for(int i=0;i<=13;i++){
            blocks.add(new Rectangle(x[i], y[i], width[i], height[i]));
        }
    }

    @Override
    public boolean mapCollision(GameObject obj) {
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
        for(Iterator<GameObject> itr = mapObjects.iterator(); itr.hasNext();){
            GameObject enemy = itr.next();
            if (obj.getBounds().intersects(enemy.getBounds())) {
                enemy.getDamage(obj.damage);
                return true;
            }
        }return false;
    }

    public void checkDie(){
        mapObjects.removeIf(enemy -> enemy.dieFlag == 1);
    }

    public void checkEnemyRespawn(){
        if(mapObjects.isEmpty()) {
            if(respawnFlag){
                respawnTime_count = System.currentTimeMillis();

                listOfItems.add(new FullHpPotion(1170, 241, player));
                listOfItems.add(new FullMpPotion(1190, 255, player));
                listOfItems.add(new AttPotion(1200, 230, player));

                respawnFlag = false;
            }
            long now = System.currentTimeMillis();
            if ((now) - (respawnTime_count) >= 25000) {
                addEnemy();
                respawnFlag = true;
            }
        }
    }

    public void checkRespawn(){
        if(listOfItems.isEmpty()) {
            if(respawnItemFlag){
                respawnItemTime_count = System.currentTimeMillis();
                respawnItemFlag = false;
            }
            long now = System.currentTimeMillis();
            if ((now) - (respawnItemTime_count) >= 20000){
                addItem();
                respawnItemFlag = true;
            }
        }
    }

    private void addItem(){
        listOfItems.add(new MpPotion(700, 150, player));
        listOfItems.add(new HpPotion(620, 150, player));
        listOfItems.add(new HpPotion(580, 140, player));
    }

    private void addEnemy(){
        mapObjects.add(new Goblin(player, 228, 481, 2, ID.ENEMY));
        mapObjects.add(new Goblin(player, 260, 170, 2, ID.ENEMY));
        mapObjects.add(new Goblin(player, 355, 340, 2, ID.ENEMY));
        mapObjects.add(new Golem(player, 800, 270, 10, ID.ENEMY));
        mapObjects.add(new Goblin(player, 1016, 640, 2, ID.ENEMY));
        mapObjects.add(new Goblin(player, 1052, 340, 2, ID.ENEMY));
        mapObjects.add(new Goblin(player, 1150, 430, 2, ID.ENEMY));
        mapObjects.add(new Goblin(player, 1276, 269, 2, ID.ENEMY));
        mapObjects.add(new Goblin(player, 1268, 380, 2, ID.ENEMY));
        mapObjects.add(new Golem(player, 1168, 241, 10, ID.ENEMY));
    }

    @Override
    public void setObjectPosition(int x, int y, GameObject obj) {
        obj.setX(x);
        obj.setY(y);
    }

    public boolean toNextMap(){
        return player.getX() >= 248 && player.getY() >= 875;
    }


    public ID getID() { return id; }
}
