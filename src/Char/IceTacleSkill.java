package Char;

import assets.IceTacleAssets;
import main.Controller;
import states.GameState;
import utils.Animation;
import utils.AudioPlayer;

import java.awt.Graphics;
import java.awt.Rectangle;

public class IceTacleSkill extends GameObject implements Runnable{

    private final GameState game;
    private long attackSpeed_Count = 0;
    public int damage;

    private final Animation animation;

    public IceTacleSkill(int x, int y, int damage, ID id, GameState game) {
        super(x, y, damage, id);
        this.damage = damage;
        this.game = game;

        Thread t = new Thread(this, "Skill3 Thread");

        AudioPlayer audio = new AudioPlayer("res\\Sound\\Effect\\TentacleSkill.wav", false);
        audio.setGain(-20);
        audio.play();

        IceTacleAssets assets = new IceTacleAssets();
        assets.init();

        animation = new Animation(100, assets.ice);
        game.getPlayer().MP -= 25;

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
        //g.drawRect(x+35, y+50, 30, 35);
    }

    public Rectangle getBounds() {
        return new Rectangle(x+35, y+50, 30, 35);
    }

    @Override
    public void getDamage(int damage) {}

    /* Getter & Setter Corner!! */
    public int getX(){ return x; }
    public int getY() { return y; }

}
