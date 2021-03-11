package map;

import Char.GameObject;
import utils.AudioPlayer;

import java.awt.Graphics;


public abstract class Map {

    public AudioPlayer BG;

    public abstract void tick(GameObject obj);
    public abstract void render(Graphics g);


    public void setObjectPosition(int x, int y, GameObject obj){
        obj.setX(x);
        obj.setY(y);
    }

    public boolean toNextMap(){ return false; }
    public boolean mapCollision(GameObject obj){ return false; }
    public boolean objectCollisionWithDamage(GameObject obj){ return false; }
    public boolean toNextMap1()
    {
        return false;
    }
    public boolean toNextMap2()
    {
        return false;
    }

}
