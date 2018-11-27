package main.window;

import main.framework.Game;
import main.MainCanvas;
import main.framework.Texture;
import main.util.Menu;

import java.awt.*;

public class CoinDisplay extends Menu {

    public Texture texture = Game.getInstance();
    public Animation coinDisplay;
    public int coinQuantity = 0;

    private int strHeight = 0, strWidth = 0;
    private int labelX, labelY;
    private Font font = new Font("华康少女文字W5",Font.BOLD,15);
    private String label = "24132154323";

    public CoinDisplay (MainCanvas mainCanvas, Game game, int x, int y, int width, int height){
        super(mainCanvas, game, x,y,width,height);
        coinDisplay = new Animation(5,texture.coin[0],texture.coin[1],texture.coin[2],texture.coin[3],texture.coin[4]);
        labelX = x + height;
        labelY = y;
    }

    @Override
    public void tick(){
        coinDisplay.runAnimation();
        //coinQuantity = game.getHandler().player.getCoin();
    }

    @Override
    public void render(Graphics g){
        super.render(g);
        coinDisplay.drawAnimation(g,x,y,height,height);

        g.setFont(font);
        g.setColor(Color.white);
        setStringPosition(g);
        g.drawString(label,labelX,labelY);
    }

    public void setStringPosition(Graphics g){
        strHeight = font.getSize();
        strWidth = g.getFontMetrics(font).stringWidth(label);
        labelX = x + width - strWidth;
        labelY = y + (height - strHeight)/2 + strHeight;
    }


    public void setCoinQuantity (int coinQuantity){
        this.coinQuantity = coinQuantity;
    }

    public void setLabel (int coinQuantity){
        label = coinQuantity + " ";
    }
}
