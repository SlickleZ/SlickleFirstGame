package item;

import Char.Player;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class Item {

    protected final int x;
    protected final int y;
    public Player player;

    public Item(int x, int y, Player player) {
        this.x = x;
        this.y = y;
        this.player = player;
    }

    public abstract void render(Graphics g);
    public abstract boolean collision();

    public Rectangle getBounds() { return null; }


    public int getX() { return x; }
    public int getY() { return y; }
}
