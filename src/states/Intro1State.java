package states;

import main.Game;
import utils.AudioPlayer;
import utils.BufferedImageLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Intro1State extends State{

    private BufferedImage diary;
    private final Intro2State intro2;

    public Intro1State(Game game){

        intro2 = new Intro2State(game);

        BG = new AudioPlayer("res\\Sound\\BGM\\Freddy.wav", true);
        BG.setGain(-30);
        BG.play();

        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            diary = loader.loadImage("res\\GUI\\1stDiary.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        if(toNextState){
            BG.stop();
            State.setState(intro2);
        }
    }

    @Override
    public void render(Graphics g) {
        if(checkpoint == 0){
            g.drawImage(diary, 250, 0, null);
            g.setColor(Color.WHITE);
            g.drawRoundRect(820, 660, 40, 40, 10, 10);
            g.setFont(new Font("Calibri", Font.BOLD, 24));
            g.drawString("F      Continue", 835, 688);
        }

    }

    public String getName() { return "Intro1 State"; }
}
