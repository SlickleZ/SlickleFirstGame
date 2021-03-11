package main;
import Char.GameObject;
import Char.ID;
import map.Map;

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/* Class for control skill */

public class Controller{

    /* Create LinkedList for object of skill in the game. */
    public static LinkedList<GameObject> b = new LinkedList<>();

    /* method for update skill in the game */
    public void tick(Map map) {
        /* let each element in LinkedList */
        try {
            for (Iterator<GameObject> iter = b.iterator();iter.hasNext();) {
                GameObject obj = iter.next();
                /* Check if player used skill, It always deleted from linked list */
                if (obj.getID() == ID.SKILL) {
                    /* this will set if <var>meteor</var> is hit, Remove it! */
                    if (map.mapCollision(obj)) {
                        iter.remove();
                    }
                }obj.tick();
            }

        }catch(ConcurrentModificationException e){
            System.out.println("ConcurrentModificationException is catch! ");
            System.err.println(e.toString() + " : " + e.getMessage());
            e.printStackTrace();
        }
        catch(NoSuchElementException e1){
            System.out.println("NoSuchElementException  is catch! ");
            System.err.println(e1.toString() + " : " + e1.getMessage());
            e1.printStackTrace();
        }
    }

    /* method for render */
    public void render(Graphics g) {
        try{
            for (GameObject obj : b) {
                obj.render(g);
            }
        }catch(ConcurrentModificationException e){
            System.err.println(e.toString() + " : " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void addObject(GameObject m){ b.add(m); }
    public static void removeObject(GameObject m){ b.remove(m); }

}
