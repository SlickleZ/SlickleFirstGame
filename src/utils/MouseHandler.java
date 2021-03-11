package utils;

import main.Game;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseHandler extends MouseAdapter {

    private final Game game;

    public MouseHandler(Game game){
        this.game = game;
    }

    @Override
    public void mouseClicked(MouseEvent e) { game.mouseClicked(e); }
}
