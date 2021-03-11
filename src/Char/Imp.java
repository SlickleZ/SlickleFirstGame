package Char;

import assets.ImpAssets;
import utils.Animation;
import utils.AudioPlayer;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;


public class Imp extends GameObject implements Runnable{

    private final Player player;

    private final ImpAssets assets;
    private final Animation aUp, aDown, aLeft, aRight, aAttDown, aDie;

    private int HP = 1200;
    private boolean attack;
    private long attackSpeed_Count = 0;

    private final Ellipse2D.Double sightRadius;

    private final HashMap<String, AudioPlayer> sfx;

    public Imp(Player player, int x, int y, int damage, ID id) {
        super(x, y, damage,id);

        this.player = player;
        HP += (player.level * 50);
        super.damage += (player.level*2);

        Thread t = new Thread(this, "Imp Thread");

        sfx = new HashMap<>();
        sfx.put("Die", new AudioPlayer("res\\Sound\\Imp\\Die.wav", false));
        sfx.put("Attack", new AudioPlayer("res\\Sound\\Imp\\Attack.wav", false));
        sfx.put("Damaged", new AudioPlayer("res\\Sound\\Imp\\Damaged.wav", false));

        sfx.get("Die").setGain(-30);
        sfx.get("Attack").setGain(-10);
        sfx.get("Damaged").setGain(-15);

        assets = new ImpAssets();
        assets.init();

        aUp = new Animation(75, assets.up);
        aDown = new Animation(75, assets.down);
        aLeft = new Animation(75, assets.left);
        aRight = new Animation(75, assets.right);
        aDie = new Animation(75, assets.die);
        aAttDown = new Animation(420, assets.attDown);

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

            sightRadius.setFrame((x + 45) - (300 / 2.0d), (y + 50) - (300 / 2.0d), 300, 300);

            /* This for prevent the sprite's off the screen */
            if (x <= -10)
                x = -10;
            if (x >= 1312)
                x = 1312;
            if (y <= -20)
                y = -20;
            if (y >= 885)
                y = 885;
           // System.out.println("Imp HP : "+ HP);
        }else{
            HP = 0;
            sfx.get("Die").play();
            aDie.tick();
            if(aDie.isLastFrame())
                dieFlag = 1;

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
//        g.drawRect(x+15, y+10, 55, 60);
        g.drawImage(getCurrentAnimationFrame(), x, y, 80, 80, null);
        g.setColor(Color.WHITE);
        g.drawString("Lord of Dream", x+8, y+80);
        g.drawString(Integer.toString(HP), x+27, y+95);
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
        sfx.get("Damaged").reset();
        sfx.get("Damaged").play();
        HP -= damage;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+15, y+10, 55, 60);
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
                return assets.getImpIdleFrame();
        }
    }

}
