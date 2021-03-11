package states;

import Char.ID;
import Char.Player;
import assets.UI;
import main.Controller;
import main.Game;
import map.MapManager;

import utils.Camera;

import java.awt.Graphics2D;
import java.awt.Graphics;

public class GameState extends State{

    private final DefeatedState defeatState;
    private final WinState winState;

    /* Map */
    MapManager mapManager;

    /* Camera for this game */
    private final Camera camera;
    private final UI ui;

    /* Initialize the player and meteor controller */
    private final Player p1;
    private final Controller ctrl;

    public GameState(Game game){

        defeatState = new DefeatedState(game);
        winState = new WinState(game);

        p1 = new Player(0, 0, 10, ID.PLAYER, this);
        mapManager = new MapManager(p1, ID.BLOCK);
        ctrl = new Controller();
        camera = new Camera(game, p1);
        ui = new UI(this);

    }


    @Override
    public void tick() {
        camera.run();
        p1.tick();
        mapManager.tick(p1);
        ctrl.tick(mapManager);
        if(p1.dieFlag == 1){
            mapManager.getCurrentMap().BG.stop();
            State.setState(defeatState);
        }
        if(p1.win){
            mapManager.getCurrentMap().BG.stop();
            State.setState(winState);
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;

        /* translate camera coordinate to the current coordinate system */
        g2d.translate((int)-camera.getCamX(), (int)-camera.getCamY());

        mapManager.render(g);
        ctrl.render(g);  /* Game object render */
        p1.render(g);
        ui.render(g);

    }
    public Player getPlayer() { return p1; }
    public Camera getCamera() { return camera; }
    public MapManager getMap() { return mapManager; }
    public String getName() { return "Game State"; }

}
