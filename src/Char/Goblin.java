package Char;

import assets.GoblinAssets;
import utils.Animation;
import utils.AudioPlayer;


import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class Goblin extends GameObject implements Runnable{

    /* This player will be followed */
    private final Player player;

    private int HP = 200;
    private boolean attack;
    private final int exp;

    private long attackSpeed_Count = 0;

    private final Ellipse2D.Double sightRadius;

    /* for render animations */
    private final GoblinAssets assets;
    private final Animation aDown, aUp, aRight, aLeft, aAttDown,aDie;

    private final HashMap<String, AudioPlayer> sfx;


    public Goblin(Player player, int x, int y, int damage, ID id) {
        super(x, y, damage,id);

        this.player = player;
        HP += (player.level * 40);
        exp = (15+(player.level));
        super.damage += (player.level);

        Thread t = new Thread(this, "Goblin Thread");

        sfx = new HashMap<>();
        sfx.put("Die", new AudioPlayer("res\\Sound\\Goblin\\Die.wav", false));
        sfx.put("Attack", new AudioPlayer("res\\Sound\\Goblin\\Attack.wav", false));
        sfx.put("Damaged", new AudioPlayer("res\\Sound\\Goblin\\Damaged.wav", false));

        sfx.get("Die").setGain(-30);
        sfx.get("Attack").setGain(-30);
        sfx.get("Damaged").setGain(-30);

        assets = new GoblinAssets();
        assets.init();

        aDown = new Animation(50, assets.eDown);
        aAttDown = new Animation(210, assets.eAttDown);
        aRight = new Animation(50, assets.eRight);
        aUp = new Animation(50, assets.eUp);
        aLeft = new Animation(50, assets.eLeft);
        aDie = new Animation(230, assets.eDie);

        sightRadius = new Ellipse2D.Double();
        t.start();
    }

    @Override
    public void run() {
        tick();
    }

    @Override
    public void tick() {

        if(isAlive()) {
            dieFlag = 0;

            collision();

            aDown.tick();
            aUp.tick();
            aRight.tick();
            aLeft.tick();
            if(attack){ aAttDown.tick();}

            move(player);

            x += velX;
            y += velY;

            sightRadius.setFrame((x + 30) - (200 / 2.0d), (y + 30) - (200 / 2.0d), 250, 200);

            /* This for prevent the sprite's off the screen */
            if (x <= -10)
                x = -10;
            if (x >= 1312)
                x = 1312;
            if (y <= -20)
                y = -20;
            if (y >= 885)
                y = 885;

        }else{
            HP = 0;
            sfx.get("Die").play();
            aDie.tick();
            if(aDie.isLastFrame()){
                player.exp += this.exp;
                dieFlag = 1;
            }

            setVelX(0); setVelY(0);
        }
    }

    /* Collision System for this player */
    private void collision(){
        attack = false;
        if(this.getBounds().intersects(player.getBounds())){
            attack = true;
            long attackSpeed = System.currentTimeMillis();
            if(attackSpeed > attackSpeed_Count + 1300){
                sfx.get("Attack").reset();
                sfx.get("Attack").play();
                player.getDamage(damage);
                attackSpeed_Count = attackSpeed;
            }
            playerGetBlocked();
        }
    }

    private void move(Player player){

        if(sightRadius.intersects(player.getBounds())) {
            if (y > player.getY()+1) {
                setVelY(-2);
            }if (y < player.getY()-1) {
                setVelY(2);
            }
            else if (x > player.getX()+1) {
                setVelX(-2);
            }
            else if (x < player.getX()-1) {
                setVelX(2);
            }
        }else{
            setVelX(0); setVelY(0);
        }
    }

    @Override
    public void render(Graphics g) {
        //Graphics2D g2d = (Graphics2D)g;
        //g2d.draw(sightRadius);
        g.drawImage(getCurrentAnimationFrame(), x, y, null);
        g.setColor(Color.WHITE);
        g.drawString("Goblin", x+15, y+70);
        g.drawString(Integer.toString(HP), x+22, y+85);
        //g.drawRect(x+15, y+20, 35, 35);
    }

    public void playerGetBlocked(){
        setVelX(0);
        setVelY(0);
    }

    @Override
    public void getDamage(int damage) {
        sfx.get("Damaged").reset();
        sfx.get("Damaged").play();
        HP -= damage;
    }

    public boolean isAlive(){
        return HP > 0;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+15, y+20, 35, 35);
    }

    private BufferedImage getCurrentAnimationFrame(){

        if(!isAlive()){
            if(dieFlag == 0)
                return aDie.getCurrentFrame();
            else
                return null;
        }
        else{
            if(attack)
                return aAttDown.getCurrentFrame();
            if(getVelX() > 0)
                return aRight.getCurrentFrame();
            else if (getVelX() < 0)
                return aLeft.getCurrentFrame();
            else if(getVelY() > 0)
                return aDown.getCurrentFrame();
            else if(getVelY() < 0)
                return aUp.getCurrentFrame();
            else
                return assets.getGoblinIdleFrame();
        }
    }
}
