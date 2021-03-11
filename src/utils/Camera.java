package utils;


import Char.Player;
import main.Game;

public class Camera implements Runnable{

    private final float xOffsetMax;
    private final float yOffsetMax;
    float yOffsetMin = 0;
    float xOffsetMin = 0;
    private float camX;
    private float camY;
    private final Player player;


    /* - Method Constructor
        * <var>xOffsetMax</var> is a half of the width of the screen
        * <var>yOffsetMax</var> is a half of the height of the screen
    * */

    public Camera(Game game, Player player){
        Thread t = new Thread(this, "Camera Thread");
        this.player = player;
         xOffsetMax = game.getWidth() / 2f;
         yOffsetMax = game.getHeight() / 2f;
         t.start();
    }

    public void run(){

        /* those <var>camX, camY</var> are the coordinates that sprite should appeared
        *  @Formula appropriate coordinate = player Coordinate - camera coordinate;
        */

        /* make camera move with player */
        camX = player.getX() - (xOffsetMax);
        camY = player.getY() - (yOffsetMax);

        /* For Debugging Mode */
//        System.out.println("OffsetMaxX, OffsetMaxY : " + this.xOffsetMax + "," + this.yOffsetMax);
//       System.out.println("CamX, CamY : " + this.camX + "," + this.camY);

        /* This <syntax>if-else if</syntax> will make your camera don't get off the screen */
        /* This kept camera in the (0, 0) */
        if(camX < xOffsetMin) camX = xOffsetMin;
        if (camY < yOffsetMin) camY = yOffsetMin;

        /* These value use for 1366*690 pixel map(s) */
        /* camera is not move when player is at the edge of the map */
        if(camX >= 330.0f) camX = 330f;
        if(camY >= 220.0f) camY = 220.0f;
        else if (camY <= -370.0f) camY = -370.0f;
    }

    /* Getter Corner!! */
    public float getCamX() { return camX; }
    public float getCamY() { return camY; }

}