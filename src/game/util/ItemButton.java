

package game.util;

import game.items.Item;

import java.awt.*;

public class ItemButton extends Button{


    /*private int x,y,width,height;
    private String label;
    private ButtonFunction buttonFunction;*/

    private int numX;
    private int num;
    private int numY;
    private String stringItemNumber;
    private Item item;
    //private UseMenu useMenu;


   /* protected int descriptionX = PausedMenu.iM_x;
    protected int descriptionY = PausedMenu.iM_y;
    protected int descriptionWidth = PausedMenu.iMWidth;
    protected int descriptionHeight = ItemMenu.descriptionHeight;

    protected LabelBox descriptionLabelBox = new LabelBox("aaa",descriptionX,descriptionY,descriptionWidth,descriptionHeight,Color.WHITE,descriptionFont);
*/


    public ItemButton (int x, int y, int width, int height, String label, Color color, Font font, ButtonFunction buttonFunction, int num, Item item){
        super(x,y,width,height,label,color,font,buttonFunction);
        this.num = num;
        this.item = item;
        //useMenu = new UseMenu(game,x,y,width,height);
    }

    @Override
    public void tick(){}

    @Override
    public void render(Graphics g){
        setString(g);
        g.setColor(color);
        if (!selected) {setColor(new Color(255,255,255));}
        else{
            setColor(new Color(138,43,226,255));
            font = new Font("华康少女文字W5",Font.BOLD,15);
            descriptionLabelBox.render(g);
        }
        g.setFont(font);
        g.drawString(label,fontX,fontY);
        g.drawString(stringItemNumber,numX,numY);
        g.drawString(label,fontX,fontY);
        g.setColor(new Color(255,255,255,buttonAlpha()));
        g.fillRect(x+5,y+5,width-10,height-10);


    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
        stringItemNumber = "X" + this.num;
    }

    @Override
    public void setString(Graphics g){
        strHeight = font.getSize();
        strWidth = g.getFontMetrics(font).stringWidth(stringItemNumber);
        fontX = (x+20);
        fontY = y+(height - strHeight)/2 + strHeight;
        numX = x+ width - strWidth - 5;
        numY = fontY;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
