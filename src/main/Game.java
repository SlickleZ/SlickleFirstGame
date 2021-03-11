package main;

import Char.*;


import states.*;

import utils.AudioPlayer;
import utils.KeyInput;
import utils.MouseHandler;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Game extends Canvas implements Runnable{

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 720;
    public final String TITLE = "Abyss Odyssey";

    private boolean running = false;
    public boolean isLoading = false;
    private Thread thread;
    public AudioPlayer mouseClicked, cast, flipped, write;

    private final BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

    /* State for this game */
    private GameState gameState;
    public MenuState menuState;
    private HowToPlayState HowToState;

    /* method for initialize everything in the game  */
    public void init(){

        requestFocus();

        addKeyListener(new KeyInput(this));
        addMouseListener(new MouseHandler(this));

        mouseClicked = new AudioPlayer("res\\Sound\\Effect\\MouseClicked.wav", false);
        mouseClicked.setGain(-20);

        flipped = new AudioPlayer("res\\Sound\\Effect\\FlippingPages.wav", false);
        mouseClicked.setGain(-30);

        cast = new AudioPlayer("res\\Sound\\Effect\\Cast.wav", false);
        cast.setGain(-30);

        write = new AudioPlayer("res\\Sound\\Effect\\Writing.wav", false);
        write.setGain(-10);

        gameState = new GameState(this);
        menuState = new MenuState(this);
        HowToState = new HowToPlayState(this);

        State.setState(menuState);
    }

    /* method start when game is start to running */
    private synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this, "Game Thread");
        thread.start();
    }

    /* method for stop when the game is not running */
    private synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    /* Key Input for this player */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_A){
            // Increase velocity instead of increase x, y directly
            gameState.getPlayer().setVelX(-4);
        }else if(key == KeyEvent.VK_D){
            gameState.getPlayer().setVelX(4);
        }else if(key == KeyEvent.VK_S){
            gameState.getPlayer().setVelY(4);
        }else if(key == KeyEvent.VK_W){
            gameState.getPlayer().setVelY(-4);
        }else if(key == KeyEvent.VK_1){
            cast.play();
            gameState.getPlayer().skill1 = true;
        }else if(key == KeyEvent.VK_2){
            cast.play();
            gameState.getPlayer().skill2 = true;
        }else if(key == KeyEvent.VK_3){
            cast.play();
            gameState.getPlayer().skill3 = true;
        }else if(key == KeyEvent.VK_4){
            cast.play();
            gameState.getPlayer().skill4 = true;
        }else if (key == KeyEvent.VK_SPACE){
            gameState.getPlayer().attack = true;
        }else if(key == KeyEvent.VK_F){
            if(State.getState().getName().equals("Defeated State")){
                State.getState().BG.stop();
                State.setState(menuState);
                menuState.BG.reset();
                menuState.BG.play();
            }
            if(State.getState().getName().equals("Intro1 State")){
                flipped.reset();
                flipped.play();
                State.getState().toNextState = true;
            }
            if(State.getState().getName().equals("Intro2 State")){
                isLoading = true;
                gameState = new GameState(this);
                State.setState(gameState);
                isLoading = false;
            }
            if(State.getState().getName().equals("Win State")){
                flipped.reset();
                flipped.play();
                State.getState().toNextState = true;
            }if(State.getState().getName().equals("EndCredit State")){
                State.getState().checkpoint++;
                if(State.getState().checkpoint == 5){
                    State.getState().toNextState = true;
                }
            }
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_A){
            gameState.getPlayer().setVelX(0);
        }else if(key == KeyEvent.VK_D){
            gameState.getPlayer().setVelX(0);
        }else if(key == KeyEvent.VK_S){
            gameState.getPlayer().setVelY(0);
        }else if(key == KeyEvent.VK_W){
            gameState.getPlayer().setVelY(0);
        }else if(key == KeyEvent.VK_1){
            cast.reset();
            gameState.getPlayer().skill1 = false;
        }else if(key == KeyEvent.VK_2){
            cast.reset();
            gameState.getPlayer().skill2 = false;
        }else if(key == KeyEvent.VK_3){
            cast.reset();
            gameState.getPlayer().skill3 = false;
        }else if(key == KeyEvent.VK_4){
            cast.reset();
            gameState.getPlayer().skill4 = false;
        }else if(key == KeyEvent.VK_SPACE) {
            gameState.getPlayer().attack = false;
        }
    }

    /* For input from mouse */
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {

            if(State.getState().getName().equals("Game State")) {
                if (gameState.getPlayer().skill1) {
                    /* set CD for skill 1 : Meteor Strike! */
                    long skill1_cd = System.currentTimeMillis();
                    /* CD about 5 seconds! */
                    if (skill1_cd > gameState.getPlayer().skill1_count + 5000) {
                        gameState.getPlayer().skill1 = true;
                        if (gameState.getPlayer().MP >= 10) {
                            Controller.addObject(new LightningClawSkill((e.getX() + (int) gameState.getCamera().getCamX()) - 45, (e.getY() + (int) gameState.getCamera().getCamY() - 30)
                                    , 15 + (gameState.getPlayer().damage / 3), ID.SKILL, gameState));
                            gameState.getPlayer().skill1_count = skill1_cd;
                        }

                    }
                } else if (gameState.getPlayer().skill2) {
                    long skill2_cd = System.currentTimeMillis();
                    if (skill2_cd > gameState.getPlayer().skill2_count + 7000) {
                        gameState.getPlayer().skill2 = true;
                        if (gameState.getPlayer().MP >= 20) {
                            Controller.addObject(new FireLionSkill((e.getX() + (int) gameState.getCamera().getCamX()) - 40, (e.getY() + (int) gameState.getCamera().getCamY() - 60)
                                    , 25 + (int) (gameState.getPlayer().damage / 5.5), ID.SKILL, gameState));
                            gameState.getPlayer().skill2_count = skill2_cd;
                        }
                    }
                } else if (gameState.getPlayer().skill3) {
                    long skill3_cd = System.currentTimeMillis();
                    if (skill3_cd > gameState.getPlayer().skill3_count + 7000) {
                        gameState.getPlayer().skill3 = true;
                        if (gameState.getPlayer().MP >= 25) {
                            Controller.addObject(new IceTacleSkill((e.getX() + (int) gameState.getCamera().getCamX()) - 40, (e.getY() + (int) gameState.getCamera().getCamY() - 60)
                                    , 30 + (gameState.getPlayer().damage / 6), ID.SKILL, gameState));
                            gameState.getPlayer().skill3_count = skill3_cd;
                        }

                    }
                } else if (gameState.getPlayer().skill4) {
                    long skill4_cd = System.currentTimeMillis();
                    if (skill4_cd > gameState.getPlayer().skill4_count + 6000) {
                        gameState.getPlayer().skill4 = true;
                        if (gameState.getPlayer().MP >= 20) {
                            Controller.addObject(new TornadoSkill((e.getX() + (int) gameState.getCamera().getCamX()) - 40, (e.getY() + (int) gameState.getCamera().getCamY() - 60)
                                    , 10 + (gameState.getPlayer().damage / 4), ID.SKILL, gameState));

                            Controller.addObject(new TornadoSkill((e.getX() + (int) gameState.getCamera().getCamX()) - 40, (e.getY() + (int) gameState.getCamera().getCamY() - 10)
                                    , 10 + (gameState.getPlayer().damage / 4), ID.SKILL, gameState));

                            gameState.getPlayer().skill4_count = skill4_cd;
                        }

                    }
                }
            }
            //System.out.println("Clicked " + e.getX() + ", "+ e.getY());

            if(State.getState().getName().equals("Menu State")){
                mouseClicked.reset();
                mouseClicked.play();
                if((e.getX() >= 430 && e.getX() <= 600) && (e.getY() >= 260 && e.getY() <= 305) ){
                    State.setState(new Intro1State(this));
                    menuState.BG.stop();
                    flipped.play();
                    flipped.reset();
                    write.play();
                }if((e.getX() >= 330 && e.getX() <= 675) && (e.getY() >= 345 && e.getY() <= 405) ) {
                    State.setState(HowToState);
                }if((e.getX() >= 450 && e.getX() <= 565) && (e.getY() >= 450 && e.getY() <= 500) ){
                    System.exit(0);
                }
            }
            if(State.getState().getName().equals("HowToPlay State")){
                mouseClicked.reset();
                mouseClicked.play();
                if((e.getX() >= 48 && e.getX() <= 176) && (e.getY() >= 659 && e.getY() <= 706) ){
                    State.setState(menuState);
                }
            }
        }
    }

    public void run() {
        init();
        long lastTime = System.nanoTime();

        /* 60 Frame per sec game */
        final double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        int updates = 0;
        int frames = 0;
        long timer = System.currentTimeMillis();

        while (running){
            /* display the FPS on the console */
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            if(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            /* Update frame every second */
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println(updates + " Ticks, FPS " + frames);

                /* reset them if we not to reset the m the FPS will go increase continuously */
                updates = 0;
                frames = 0;
            }
            /* For debugging */
            //System.out.println("x : " + gameState.getPlayer().getX() + " y : " + gameState.getPlayer().getY()); // Size 1036*732
        }
        /* when the game isn't running let the thread stop */
        stop();
    }

    /* method to be everything that updated in the game */
    private void tick(){
        if(State.getState() != null){
            State.getState().tick();
        }
    }

    /* method to be everything that rendered in the game */
    private void render(){

        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            /* have 3 buffered for increase speed of buffering */
            createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                            /* --------- Draw time ! ------- */
        if(State.getState() != null){
            State.getState().render(g);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri", Font.BOLD, 24));

        if(isLoading)
             g.drawString("Loading...", 900, 100);

                             /* ----------------------------- */

        /* to release the resource */
        g.dispose();
        /* show everything that we rendered */
        bs.show();
    }

    public static void main(String[] args) {
        /*
           @Set the window size!
            * In this case we gonna set the fixed size of the window
        */

        javax.swing.SwingUtilities.invokeLater(() -> {
            Game game = new Game();
            game.setPreferredSize(new Dimension(WIDTH, HEIGHT));

            /* let the thread start! */
            game.start();

        /*
            @Configuration the window!

            * .add() add window to this class
            * .pack() set the window visible (visible the size of the window, we can see the window title)
            * .setDefaultCloseOperation() makes event when we exit (There are many Option, Read docs!)
            * .setResizeable()  if false, we can't adjust about size of window else we can!
            * .setLocationRelativeTo() make window appeared on the center of the screen
            * .setVisible() set the window visible ( if false, we can't see anything, else we can!)
        */
            File obj = new File("res\\ico.png");

            JFrame frame = new JFrame(game.TITLE);
            try {
                frame.setIconImage(ImageIO.read(obj));
            } catch (IOException e) {
                e.printStackTrace();
            }
            frame.add(game);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
