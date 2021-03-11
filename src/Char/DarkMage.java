package Char;

import assets.DarkMageAssets;
import utils.Animation;
import utils.AudioPlayer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class DarkMage extends GameObject implements Runnable{

    private final Player player;

    private final DarkMageAssets assets;
    private final Animation aUp, aDown, aLeft, aRight, aAttDown, aDie;

    private int HP = 800;
    private final int exp;
    private boolean attack;
    private long attackSpeed_Count = 0;

    private final Ellipse2D.Double sightRadius;

    private final HashMap<String, AudioPlayer> sfx;

    public DarkMage(Player player, int x, int y, int damage, ID id) {
        super(x, y, damage,id);

        this.player = player;
        HP += (player.level * 200);
        exp = (50+(player.level*2));
        super.damage += (player.level*10);

        Thread t = new Thread(this, "Imp Thread");

        sfx = new HashMap<>();
        sfx.put("Die", new AudioPlayer("res\\Sound\\DarkMage\\Die.wav", false));
        sfx.put("Attack", new AudioPlayer("res\\Sound\\DarkMage\\Attack.wav", false));
        sfx.put("Damaged", new AudioPlayer("res\\Sound\\DarkMage\\Damaged.wav", false));

        sfx.get("Die").setGain(-10);
        sfx.get("Attack").setGain(-20);
        sfx.get("Damaged").setGain(-20);

        assets = new DarkMageAssets();
        assets.init();

        aUp = new Animation(75, assets.eUp);
        aDown = new Animation(75, assets.eDown);
        aLeft = new Animation(75, assets.eLeft);
        aRight = new Animation(75, assets.eRight);
        aDie = new Animation(75, assets.eDie);
        aAttDown = new Animation(225, assets.eAttDown);

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
            if(attack){ aAttDown.tick();}
            aRight.tick();
            aLeft.tick();

            move(player);

            x += velX;
            y += velY;

            sightRadius.setFrame((x + 30) - (300 / 2.0d), (y + 50) - (300 / 2.0d), 300, 300);

            /* This for prevent the sprite's off the screen */
            if (x <= -10)
                x = -10;
            if (x >= 1312)
                x = 1312;
            if (y <= -20)
                y = -20;
            if (y >= 885)
                y = 885;
            //System.out.println("DM HP : "+ HP);
        }else{
            HP = 0;
            sfx.get("Die").play();
            aDie.tick();
            if(aDie.isLastFrame()) {
                dieFlag = 1;
                player.exp += this.exp;
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
            if(attackSpeed > attackSpeed_Count + 2000){
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
        }else{ setVelX(0); setVelY(0); }
    }

    @Override
    public void render(Graphics g) {
//        Graphics2D g2d = (Graphics2D)g;
//        g2d.draw(sightRadius);
//        g.drawRect(x+15, y+10, 40, 55);
        g.drawImage(getCurrentAnimationFrame(), x, y, null);
        g.setColor(Color.WHITE);
        g.drawString("Dream Mage", x, y+75);
        g.drawString(Integer.toString(HP), x+23, y+90);
    }

    public void playerGetBlocked(){
        setVelX(0);
        setVelY(0);
    }

    public boolean isAlive(){
        return HP > 0;
    }

    @Override
    public void getDamage(int damage) {
        sfx.get("Damaged").playWithStart();
        HP -= damage;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+15, y+10, 40, 55);
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
                return assets.getDarkMageIdleFrame();
        }
    }
}

