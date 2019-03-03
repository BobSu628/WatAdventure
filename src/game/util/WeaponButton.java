package game.util;

import game.items.Weapon.Weapon;

import java.awt.*;

public class WeaponButton extends Button{

    private int numX;
    //private int num;
    private int numY;
    private String stringItemNumber;
    private ButtonFunction buttonFunction;
    private int durability;
    private int maxDurability;
    private int atk;

    private int buyPrice;


    public WeaponButton (int x, int y, int width, int height, String label, Color color, Font font, ButtonFunction buttonFunction,Weapon weapon){
        super(x,y,width,height,label,color,font,buttonFunction);
        this.buttonFunction = buttonFunction;
        this.durability = weapon.getDurability();
        this.maxDurability = weapon.getMaxDurability();
        this.atk = weapon.getAtk();
        this.buyPrice = weapon.getPrice();
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
            font = new Font("Times New Roman",Font.BOLD,15);
            descriptionLabelBox.render(g);
        }
        g.setFont(font);
        g.drawString(label,fontX,fontY);
        g.drawString(stringItemNumber,numX,numY);
        g.drawString(label,fontX,fontY);
        g.setColor(new Color(255,255,255,buttonAlpha()));
        g.fillRect(x+5,y+5,width-10,height-10);


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


    public int getNumX() {
        return numX;
    }

    public void setNumX(int numX) {
        this.numX = numX;
    }

    public int getNumY() {
        return numY;
    }

    public void setNumY(int numY) {
        this.numY = numY;
    }

    public String getStringItemNumber() {
        return stringItemNumber;
    }

    public void setStringItemNumber(String stringItemNumber) {
        this.stringItemNumber = stringItemNumber;
    }

    @Override
    public ButtonFunction getButtonFunction() {
        return buttonFunction;
    }

    @Override
    public void setButtonFunction(ButtonFunction buttonFunction) {
        this.buttonFunction = buttonFunction;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }
}
