package map;

import Char.ID;
import Char.Player;
import Char.GameObject;

import java.awt.Graphics;
import java.util.LinkedList;

public class MapManager extends Map implements Runnable{

    private final LinkedList<Map> listOfMaps = new LinkedList<>();

    private final Player player;

    private int currentMap;

    public MapManager(Player player, ID id){

        Thread t = new Thread(this, "Map Manager Thread");
        this.player = player;

        /* Init map manager with all maps of the game */
        listOfMaps.add(new EdenPalace(player, id));
        listOfMaps.add(new EdenVille(player, id));
        listOfMaps.add(new MysteriesCave(player, id));
        listOfMaps.add(new DeadlyLava(player, id));
        currentMap = 0;
        setObjectPosition(177, 50, player);
        //setObjectPosition(300, 857, player);
        t.start();
    }

    @Override
    public void run() {
    }


    public void tick(GameObject obj) {
        listOfMaps.get(currentMap).tick(obj);

            /* Check for move to next map */
            if(currentMap == 0){
                if(listOfMaps.get(currentMap).toNextMap()){

                    listOfMaps.get(currentMap).BG.stop();
                    listOfMaps.get(currentMap).BG.reset();
                    currentMap = 1;
                    setObjectPosition(0, 640, player);
                }
            }else if (currentMap == 1){
                if(listOfMaps.get(currentMap).toNextMap()){

                    listOfMaps.get(currentMap).BG.stop();
                    listOfMaps.get(currentMap).BG.reset();
                    currentMap = 0;
                    setObjectPosition(1270, 654, player);
                }else if(listOfMaps.get(currentMap).toNextMap1()){

                    listOfMaps.get(currentMap).BG.stop();
                    listOfMaps.get(currentMap).BG.reset();
                    currentMap = 2;
                    setObjectPosition(300, 857, player);
                }else if(listOfMaps.get(currentMap).toNextMap2()){

                    listOfMaps.get(currentMap).BG.stop();
                    listOfMaps.get(currentMap).BG.reset();
                    currentMap = 3;
                    setObjectPosition(10, 450, player);
                }
            }else if (currentMap == 2){
                if(listOfMaps.get(currentMap).toNextMap())
                {
                    listOfMaps.get(currentMap).BG.stop();
                    listOfMaps.get(currentMap).BG.reset();
                    currentMap = 1;
                    setObjectPosition(1196, 186, player);
                }
            }else if(currentMap == 3){
                if(listOfMaps.get(currentMap).toNextMap()){

                    listOfMaps.get(currentMap).BG.stop();
                    listOfMaps.get(currentMap).BG.reset();
                    currentMap = 1;
                    setObjectPosition(1305, 580, player);
                }
            }
    }

    public void render(Graphics g){ listOfMaps.get(currentMap).render(g); }

    public boolean mapCollision(GameObject obj){
        return listOfMaps.get(currentMap).mapCollision(obj);
    }
    public boolean objectCollisionWithDamage(GameObject obj){ return listOfMaps.get(currentMap).objectCollisionWithDamage(obj); }

    public Map getCurrentMap() { return listOfMaps.get(currentMap); }

}
