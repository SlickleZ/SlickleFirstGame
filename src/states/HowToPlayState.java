package states;

import main.Game;
import utils.BufferedImageLoader;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HowToPlayState extends State{

    Game game;
    private BufferedImage bg;

    public HowToPlayState(Game game){
        this.game = game;
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            bg = loader.loadImage("res\\GUI\\Howtoplay.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(bg, 0, 0, null);
        g.setFont(new Font("Calibri", Font.BOLD, 36));
        g.setColor(Color.RED);
        g.drawString("CAUTION!!!", 400, 600);
        g.setFont(new Font("Calibri", Font.BOLD, 24));
        g.setColor(Color.BLACK);
        g.fillRect(160, 630, 690, 25);
        g.setColor(Color.RED);
        g.drawString("DON'T CAST YOUR MAGIC ON THE SMALL AREA OR NEAR THE WALL.", 160, 650);
    }

    public String getName() { return "HowToPlay State"; }
}
