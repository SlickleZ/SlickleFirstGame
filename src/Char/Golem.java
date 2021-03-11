package Char;


import assets.GolemAssets;
import utils.Animation;
import utils.AudioPlayer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class Golem extends GameObject implements Runnable{

    private final Player player;
    private final assets.GolemAssets assets;
    private final Animation aUp,aDown, aRight, aLeft, aDie, aAttDown;
    private final Ellipse2D.Double sightRadius;

    private int HP = 500;
    private boolean attack;
    private final int exp;
    private long attackSpeed_Count = 0;

    private final HashMap<String, AudioPlayer> sfx;

    public Golem(Player player, int x, int y, int damage, ID id) {
        super(x, y, damage, id);

        this.player = player;
        HP += (player.level * 40);
        exp = 25+(player.level);
        super.damage += (player.level*1.5);

        Thread t = new Thread(this, "Golem Thread");

        sfx = new HashMap<>();
        sfx.put("Die", new AudioPlayer("res\\Sound\\Golem\\Die.wav", false));
        sfx.put("Attack", new AudioPlayer("res\\Sound\\Golem\\Attack.wav", false));
        sfx.put("Damaged", new AudioPlayer("res\\Sound\\Golem\\Damaged.wav", false));

        sfx.get("Die").setGain(-20);
        sfx.get("Attack").setGain(-20);
        sfx.get("Damaged").setGain(-30);


        assets = new GolemAssets();
        assets.init();

        aUp = new Animation(200, assets.bUp);
        aDown = new Animation(200, assets.bDown);
        aRight = new Animation(200, assets.bRight);
        aLeft = new Animation(200, assets.bLeft);
        aAttDown = new Animation(260, assets.bAttDown);
        aDie = new Animation(200, assets.bDie);

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

            sightRadius.setFrame((x + 30) - (250 / 2.0d), (y + 30) - (250 / 2.0d), 250, 250);

            /* This for prevent the sprite's off the screen */
            if (x <= -10)
                x = -10;
            if (x >= 1312)
                x = 1312;
            if (y <= -20)
                y = -20;
            if (y >= 885)
                y = 885;
            //System.out.println("Golem HP : " + HP);
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

    private void move(Player player){
        if(sightRadius.intersects(player.getBounds())) {
            if (y > player.getY()+1) {
                setVelY(-1);
            }if (y < player.getY()-1) {
                setVelY(1);
            }
            else if (x > player.getX()+1) {
                setVelX(-1);
            }
            else if (x < player.getX()-1) {
                setVelX(1);
            }
        }else{ setVelX(0); setVelY(0); }
    }


    /* Collision System for this player */
    private void collision(){
        attack = false;
        if(this.getBounds().intersects(player.getBounds())){
            attack = true;
            playerGetBlocked();
            long attackSpeed = System.currentTimeMillis();
            if(attackSpeed > attackSpeed_Count + 2400){
                sfx.get("Attack").reset();
                sfx.get("Attack").play();
                player.getDamage(damage);
                attackSpeed_Count = attackSpeed;
            }
        }
    }

    @Override
    public void render(Graphics g) {
//        Graphics2D g2d = (Graphics2D)g;
        //g2d.draw(sightRadius);
        g.drawImage(getCurrentAnimationFrame(), x, y, 72, 72, null);
        g.setColor(Color.WHITE);
        g.drawString("Golem", x+23, y+85);
        g.drawString(Integer.toString(HP), x+31, y+100);
        //g.drawRect(x+10, y+15, 45, 45);
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
        return new Rectangle(x+10, y+15, 45, 45);
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
                return assets.getGolemIdleFrame();
        }
    }
}
