package Char;


import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
    public int damage, dieFlag = 0;
    protected int x,y;
    protected double velX = 0, velY = 0;
    private final ID id;

    public GameObject(int x, int y, int damage, ID id){
        this.x = x;
        this.y = y;
        this.damage = damage;
        this.id = id;
    }
    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public void playerGetBlocked(){
        x += velX * -1;
        y += velY * -1;
    }

    public abstract void getDamage(int damage);

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() { return y; }

    public void setY(int y) {
        this.y = y;
    }

    public double getVelX() {
        return velX;
    }

    public void setVelX(double velX) {
        this.velX = velX;
    }

    public double getVelY() {
        return velY;
    }

    public void setVelY(double velY) {
        this.velY = velY;
    }

    public ID getID(){ return id; }
}
