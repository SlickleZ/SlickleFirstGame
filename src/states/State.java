package states;

import utils.AudioPlayer;

import java.awt.Graphics;

public abstract class State {

    private static State currentState = null;

    public AudioPlayer BG;

    /* for state that have to use */
    public int checkpoint;

    public boolean toNextState = false;

    public static void setState(State state) { currentState = state; }
    public static State getState(){ return currentState; }
    public String getName() { return ""; }

    public abstract void  tick();
    public abstract void render(Graphics g);

}
