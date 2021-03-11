package states;

import java.awt.*;

public class EndCredit extends State{

    private final WinState winState;

    public EndCredit(WinState winState){
        this.winState = winState;
    }

    @Override
    public void tick() {
        if(toNextState){
            winState.BG.stop();
            State.setState(winState.game.menuState);
            winState.game.menuState.BG.reset();
            winState.game.menuState.BG.play();
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Calibri", Font.BOLD, 48));
        g.drawString("THANK YOU", 410, 70);

        g.setFont(new Font("Calibri", Font.BOLD, 24));
        g.drawString("We have to say \" Thank you \" to you.  Because you're our special guest to play this demo. :)"
                ,70, 120);

        g.setFont(new Font("Calibri", Font.BOLD, 36));
        g.drawString("My team : ", 70, 180);

        g.setFont(new Font("Calibri", Font.BOLD, 24));
        if(checkpoint == 1){
            g.drawString("Kiattikun   Nusaree                         62010078    Graphics Designer", 70, 240);
        }
        if(checkpoint == 2){
            g.drawString("Kiattikun   Nusaree                         62010078    Graphics Designer", 70, 240);
            g.drawString("Thananop Nuntapornniracha     62010391    Sound Manager", 70, 270);
        }
        if(checkpoint == 3){
            g.drawString("Kiattikun   Nusaree                         62010078    Graphics Designer", 70, 240);
            g.drawString("Thananop Nuntapornniracha     62010391    Sound Manager", 70, 270);
            g.drawString("Kitikorn    Phaopun                         62010068     Programming Manager", 70, 300);
        }
        if(checkpoint == 4){
            g.drawString("Kiattikun   Nusaree                         62010078    Graphics Designer", 70, 240);
            g.drawString("Thananop Nuntapornniracha     62010391    Sound Manager", 70, 270);
            g.drawString("Kitikorn    Phaopun                         62010068     Programming Manager", 70, 300);
            g.drawString("I would like to say THANK YOU to them all too. :D", 270, 350);
            g.drawString("This demo has finished with our good cooperation. The inspiration of this game is RO",
                    70, 400);
            g.drawString("(Ragnarok Online).", 70, 430);
            g.drawString("I try to make this game PERFECT but it always have some obstacles", 265, 430);
            g.drawString("such as Lazy, Lagging etc. I think this is a good practice for me!!", 70, 460);
            g.drawString("Thank you Teacher Mayuree that make this game happened.", 70, 500);
            g.drawString("And Finally, I really want to say big THANK YOU to you.", 70, 530);
            g.setFont(new Font("Calibri", Font.BOLD, 36));
            g.drawString("WITH LOVE  <3", 410, 600);
        }
        g.setFont(new Font("Calibri", Font.BOLD, 24));
        g.drawRoundRect(820, 660, 40, 40, 10, 10);
        if(checkpoint <= 3){
            g.drawString("F     Continue", 835, 688);
        }else{
            g.drawString("F     Main Menu", 835, 688);
        }

    }

    public String getName() { return "EndCredit State"; }
}
