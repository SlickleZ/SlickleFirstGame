package assets;

import states.GameState;
import utils.BufferedImageLoader;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class UI implements Runnable{

    private final GameState gameState;
    private BufferedImage ui, blood;

    public UI(GameState gameState){
        this.gameState = gameState;
        Thread t = new Thread(this, "UI Thread");
        BufferedImageLoader loader = new BufferedImageLoader();
        try {
            ui = loader.loadImage("res\\GUI\\gui1.png");
            blood = loader.loadImage("res\\GUI\\screen-red-vignette.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
        t.start();
    }

    @Override
    public void run() {

    }

    public void render(Graphics g){
        if(gameState.getPlayer().getX() > 124){
            g.drawImage(ui, (int)gameState.getCamera().getCamX(), (int)gameState.getCamera().getCamY(), null);
            g.setColor(Color.WHITE);
            g.setFont(new Font("Calibri", Font.BOLD, 12));

            g.drawString("Slickle", (int)gameState.getCamera().getCamX()+50, (int)gameState.getCamera().getCamY()+35);

            g.drawString("Level : "+gameState.getPlayer().level,
                    (int)gameState.getCamera().getCamX()+22, (int)gameState.getCamera().getCamY()+165);
            g.drawString("EXP : "+gameState.getPlayer().exp+" / "+gameState.getPlayer().lvl_exp,
                    (int)gameState.getCamera().getCamX()+72, (int)gameState.getCamera().getCamY()+165);

            g.drawString("ATK : "+gameState.getPlayer().damage,
                    (int)gameState.getCamera().getCamX()+53, (int)gameState.getCamera().getCamY()+108);
            g.drawString("HP :  "+ gameState.getPlayer().HP+" / "+gameState.getPlayer().MAX_HP,
                    (int)gameState.getCamera().getCamX()+43, (int)gameState.getCamera().getCamY()+180);
            g.drawString("SP  :  "+ gameState.getPlayer().MP+" / "+gameState.getPlayer().MAX_MP,
                    (int)gameState.getCamera().getCamX()+43, (int)gameState.getCamera().getCamY()+195);
        }
        if(gameState.getPlayer().HP <= 20*gameState.getPlayer().MAX_HP/100){
            g.drawImage(blood, (int)gameState.getCamera().getCamX(), (int)gameState.getCamera().getCamY(), null);
        }
    }

}
