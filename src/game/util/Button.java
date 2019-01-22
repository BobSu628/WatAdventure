package game.util;

import java.awt.*;


public class Button {

    protected int x,y,height,width;
    protected String label;
    protected Color color;
    protected Font font;
    protected int strWidth;
    protected int strHeight;
    protected int fontX;
    protected int fontY;
    protected boolean selected = false;

    protected ButtonFunction buttonFunction;

    protected boolean active;
    protected boolean display;

    protected Color notSelectedColor = new Color(255,255,255);
    protected Color selectedColor = new Color(109,90,205,255);
    protected Font descriptionFont = new Font ("华康少女文字W5",Font.PLAIN,20);
    protected LabelBox descriptionLabelBox = new LabelBox("aaa",0,0,0,0,Color.WHITE,descriptionFont);

    public Button(int x, int y, int width, int height, String label, Color color, Font fnt, ButtonFunction buttonFunction) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.label = label;
        this.font = fnt;
        this.color = color;
        this.buttonFunction = buttonFunction;
        this.active = false;
        this.display = false;
        //this.notSelectedColor = color;
    }

    public void tick(){
       //buttonFunction.Function();
    }

    public void render (Graphics g){
        g.setColor(new Color(255,255,255,buttonAlpha()));
        //System.out.println(isSelected(isSelect));
        g.fillRect(x+5,y+5,width-10,height-10);
        setString(g);
        g.setFont(font);
        g.setColor(color);
        //g.drawRect(x,y,width,height);
        if (!selected) {setColor(notSelectedColor);}
        else{setColor(selectedColor);}
        g.drawString(label,fontX,fontY);
        descriptionLabelBox.render(g);
        //System.out.println("render button 12");
    }

    /*public void bind(Consumer<Menu> f){
        this.buttonFunction = f;
    }*/

    public int buttonAlpha (){
        if (selected) return 100;
        else return 0;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    /*public void performAction(){
        this.buttonFunction
    }*/

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

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public ButtonFunction getButtonFunction() {
        return buttonFunction;
    }

    public void setButtonFunction(ButtonFunction buttonFunction) {
        this.buttonFunction = buttonFunction;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }


    public LabelBox getDescriptionLabelBox() {
        return descriptionLabelBox;
    }

    public void setDescriptionLabelBox(LabelBox descriptionLabelBox) {
        this.descriptionLabelBox = descriptionLabelBox;
    }

    public void setString(Graphics g){
        strHeight = font.getSize();
        strWidth = g.getFontMetrics(font).stringWidth(label);
        fontX = x+(width - strWidth)/2;
        fontY = y+(height - strHeight)/2 + strHeight;
    }

    public Color getNotSelectedColor() {
        return notSelectedColor;
    }

    public void setNotSelectedColor(Color notSelectedColor) {
        this.notSelectedColor = notSelectedColor;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }
}
