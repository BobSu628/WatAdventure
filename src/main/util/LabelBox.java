package main.util;

import main.window.ItemMenu;
import main.window.PausedMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class LabelBox {

    private int x, y;
    private String label;
    private Color color;
    private Font font;
    private int strHeight = 0, strWidth = 0;
    private int fontX, fontY;
    private int width,height;


    public LabelBox(String label, int x, int y, int width, int height, Color color, Font font) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.font = font;
        this.color = color;
        this.width = width;
        this.height = height;
    }

    public void tick() {

    }

    public void render(Graphics g) {
        g.setColor(color);
        g.setFont(font);
        setString(g);
        //g.drawRect(x,y,width,height);
        g.drawString(label,fontX,fontY);
        //System.out.println("render Label");
   }

    public int getX() {
        return x;
    }


    public void setX(int x) {
        this.x = x;
    }


    public int getY() {
        return y;
    }


    public void setY(int y) {
        this.y = y;
    }


    public String getLabel() {
        return label;
    }


    public void setLabel(String label) {
        this.label = label;
    }


    public Color getColor() {
        return color;
    }


    public void setColor(Color color) {
        this.color = color;
    }


    public Font getFont() {
        return font;
    }


    public void setFont(Font font) {
        this.font = font;
    }

    public void setString(Graphics g){
        strHeight = font.getSize();
        strWidth = g.getFontMetrics(font).stringWidth(label);
        fontX = x+(width - strWidth)/2;
        fontY = y+(height - strHeight)/2 + strHeight;
    }

    public void initializeItemMenuDescriptionParameter (){
        int descriptionX = PausedMenu.iM_x;
        int descriptionY = PausedMenu.iM_y;
        int descriptionWidth = PausedMenu.iMWidth;
        int descriptionHeight = ItemMenu.descriptionHeight;
        x = descriptionX;
        y = descriptionY;
        width = descriptionWidth;
        height = descriptionHeight;
    }
}
