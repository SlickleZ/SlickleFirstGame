package Char;

import assets.PlayerAssets;
import states.GameState;
import utils.Animation;
import utils.AudioPlayer;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;


public class Player extends GameObject implements Runnable{

    public int HP = 195, MAX_HP;
    public int MP = 100, MAX_MP;
    public long skill1_count = 0, skill2_count = 0, skill3_count = 0, skill4_count = 0, attackSpeed_Count = 0, regen_count = 0;
    public boolean skill1 = false, skill2 = false, skill3 = false, skill4 = false, win = false;

    private final GameState game;
    private final AudioPlayer lvlUp;

    private final HashMap<String, AudioPlayer> sfx;

    public int level = 1, exp=0, lvl_exp;
    public boolean attack = false, attackFlag = false;

    private final Animation aDown, aUp, aRight, aLeft, aDie, aAttDown, aSkill;
    private BufferedImage lastFrame;

    public Player(int x, int y, int damage, ID id, GameState game){
        super(x, y, damage,id);
        this.damage += (level/3);

        this.MAX_HP = HP + (level*5);
        this.HP = MAX_HP;
        this.MAX_MP = MP + (level*2);
        this.MP = MAX_MP;
        this.game = game;
        Thread t = new Thread(this, "Player Thread");

        sfx = new HashMap<>();
        sfx.put("Attack", new AudioPlayer("res\\Sound\\Effect\\Player_Attack.wav", false));

        lvlUp = new AudioPlayer("res\\Sound\\Effect\\Level Up Sound Effect.wav", false);
        lvlUp.setGain(-20);

        /* for render animations */
        PlayerAssets assets = new PlayerAssets();
        assets.init();
        /* for animation */
        aDown = new Animation(50, assets.pDown);
        aUp = new Animation(50, assets.pUp);
        aRight = new Animation(50, assets.pRight);
        aLeft = new Animation(50, assets.pLeft);
        aDie = new Animation(150, assets.pDie);
        aAttDown = new Animation(105, assets.pAttackDown);
        aSkill = new Animation(90, assets.pSkill1);
        lastFrame = assets.getpIdleFrame();
        t.start();
    }

    @Override
    public void run() {
        tick();
    }

    /* method for update */
    public void tick(){

        if(isAlive()) {
            dieFlag = 0;
            lvl_exp = (level * 20);
            checkLevelUp();
            /* For update animation of player */
            aDown.tick();
            aUp.tick();
            aLeft.tick();
            aRight.tick();
            aAttDown.tick();
            aSkill.tick();

            /* Check for collision with the other block */
            collision(game);

            if(this.MP <= 0){
                this.MP = 0;
            }

            x += velX;
            y += velY;

            /* This for prevent the sprite's off the screen */
            if (x <= -10)
                x = -10;
            if (x >= 1312)
                x = 1312;
            if (y <= -20)
                y = -20;
            if (y >= 885)
                y = 885;
            MpRegen();
        }else{
            HP = 0;
            aDie.tick();
            if(aDie.isLastFrame()){
                dieFlag = 1;
            }
            setVelX(0); setVelY(0);
        }
    }

    /* Collision System for this player */
    private void collision(GameState game){
        attackFlag = attack;
        long attackSpeed = System.currentTimeMillis();
        if(attackFlag){
            if (attackSpeed > attackSpeed_Count + 700) {

                if(game.getMap().objectCollisionWithDamage(this)){
                    sfx.get("Attack").setGain(-30);
                    sfx.get("Attack").reset();
                    sfx.get("Attack").play();
                }
                attackSpeed_Count = attackSpeed;
            }
        }
    }

    public void MpRegen(){
        long regen = System.currentTimeMillis();
        if(regen > regen_count + 2000){
            if(MP >= MAX_MP){
                MP = MAX_MP;
            }else{
                MP += 2+(level/2);
            }
            regen_count = regen;
        }
    }

    /* method for render */
    public void render(Graphics g){
        g.drawImage(getCurrentAnimationFrame(), x, y, null);
        //g.drawRect(x+12, y+40, 38, 20);

    }

    public void checkLevelUp(){
        if(this.exp >= this.lvl_exp){
            lvlUp.reset();
            lvlUp.play();
            level += 1;
            this.damage += (level/5);
            this.MAX_HP += (level*5);
            this.MAX_MP += (level*5);
            exp = 0;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(x+12, y+40, 38, 20);
    }


    /*  Getter Setter corner!!  */
    public int getX() {return x;}
    public int getY() {return y;}

    public void setVelX(double velX){ this.velX = velX; }
    public void setVelY(double velY){ this.velY = velY; }

    private BufferedImage getCurrentAnimationFrame(){

        if(!isAlive()){
            if(dieFlag == 0)
                return aDie.getCurrentFrame();
            else
                return null;
        }else{
            if(attackFlag) {
                lastFrame = aAttDown.getCurrentFrame();
                return aAttDown.getCurrentFrame();
            }else if (velX > 0) {
                lastFrame = aRight.getCurrentFrame();
                return aRight.getCurrentFrame();
            } else if (velX < 0) {
                lastFrame = aLeft.getCurrentFrame();
                return aLeft.getCurrentFrame();
            } else if (velY > 0) {
                lastFrame = aDown.getCurrentFrame();
                return aDown.getCurrentFrame();
            } else if (velY < 0) {
                lastFrame = aUp.getCurrentFrame();
                return aUp.getCurrentFrame();
            }else if (skill1) {
                return aSkill.getCurrentFrame();
            }else if (skill2) {
                return aSkill.getCurrentFrame();
            }else if (skill3) {
                return aSkill.getCurrentFrame();
            }else if (skill4) {
                return aSkill.getCurrentFrame();
            }else{
                return lastFrame;
            }
        }
    }

    public void getDamage(int damage) { HP -= damage; }
    public boolean isAlive(){ return HP > 0; }
}
