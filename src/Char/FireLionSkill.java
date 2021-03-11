package Char;

import assets.FireLionAssets;
import main.Controller;
import states.GameState;
import utils.Animation;
import utils.AudioPlayer;

import java.awt.Graphics;
import java.awt.Rectangle;


public class FireLionSkill extends GameObject implements Runnable{


    private final GameState game;
    private long attackSpeed_Count = 0;
    public int damage;

    private final Animation animation;

    public FireLionSkill(int x, int y, int damage, ID id, GameState game) {
        super(x, y, damage, id);
        this.damage = damage;
        this.game = game;

        Thread t = new Thread(this, "Skill2 Thread");

        AudioPlayer audio = new AudioPlayer("res\\Sound\\Effect\\FireLionSkill.wav", false);
        audio.setGain(-20);
        audio.play();

        FireLionAssets assets = new FireLionAssets();
        assets.init();
        animation = new Animation(100, assets.lion);
        game.getPlayer().MP -= 20;
        t.start();
    }

    @Override
    public void run() {
        tick();
    }

    /* This method for updates meteor */
    public void tick() {
        animation.tick();
        collision(game);
        if(animation.isLastFrame()){
            Controller.removeObject(this);
        }
    }

    private void collision(GameState game){
        long attackSpeed = System.currentTimeMillis();
        if (attackSpeed > this.attackSpeed_Count + 800) {
            game.getMap().objectCollisionWithDamage(this);
            this.attackSpeed_Count = attackSpeed;
        }

    }

    /* method for render picture */
    public void render(Graphics g){
        g.drawImage(animation.getCurrentFrame(), x, y, 96, 96, null);
        //g.drawRect(x+25, y+40, 45, 55);
    }

    public Rectangle getBounds() {
        return new Rectangle(x+25, y+40, 45, 55);
    }

    @Override
    public void getDamage(int damage) {}

    /* Getter & Setter Corner!! */
    public int getX(){ return x; }
    public int getY() { return y; }
}
