package states;

import main.Game;
import utils.AudioPlayer;
import utils.BufferedImageLoader;

import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WinState extends State{

    private BufferedImage diary;
    private final EndCredit end;
    Game game;

    public WinState(Game game){
        this.game = game;

        game.flipped.reset();
        game.write.reset();

        end = new EndCredit(this);

        BG = new AudioPlayer("res\\Sound\\BGM\\OST 53_ White Christmas.wav", true);
        BG.setGain(-25);
        BG.stop();

        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            diary = loader.loadImage("res\\GUI\\2ndDiary.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {
        game.flipped.play();
        game.write.play();
        BG.play();
        if(toNextState){
            State.setState(end);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(diary, 250, 0, null);
        g.setColor(Color.WHITE);
        g.drawRoundRect(820, 660, 40, 40, 10, 10);
        g.setFont(new Font("Calibri", Font.BOLD, 24));
        g.drawString("F      Continue", 835, 688);
    }

    public String getName() { return "Win State"; }
}
