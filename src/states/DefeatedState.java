package states;

import main.Game;
import utils.AudioPlayer;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;

public class DefeatedState extends State implements Runnable{

    Game game;

    public DefeatedState(Game game){
        this.game = game;
        BG = new AudioPlayer("res\\Sound\\BGM\\Defeated.wav", false);
        BG.setGain(-30);
        BG.stop();
    }

    public void run() {

    }

    @Override
    public void tick() {
        BG.play();
    }

    @Override
    public void render(Graphics g) {
        g.setFont(new Font("Calibri", Font.BOLD, 50));
        g.setColor(Color.WHITE);
        g.drawString("YOU'VE BEEN LOST IN THIS NIGHTMARE !!!", 70, 350);
        g.setFont(new Font("Calibri", Font.BOLD, 32));
        g.drawString("Press F to continue ...", 400, 600);
    }

    public String getName() { return "Defeated State"; }
}
