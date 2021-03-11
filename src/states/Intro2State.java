package states;

import main.Game;
import utils.AudioPlayer;

import java.awt.*;

public class Intro2State extends State{

    Game game;

    public Intro2State(Game game){
        this.game = game;
        BG = new AudioPlayer("res\\Sound\\BGM\\Freddy_HAHA.wav", false);
    }

    @Override
    public void tick(){
        BG.play();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri", Font.BOLD, 72));
        g.drawString("WHERE AM I ??", 300, 390);
        g.setFont(new Font("Calibri", Font.BOLD, 24));
        if(!game.isLoading){
            g.drawRoundRect(820, 660, 40, 40, 10, 10);
            g.drawString("F      Continue", 835, 688);
        }

    }

    public String getName() { return "Intro2 State"; }
}
