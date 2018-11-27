/* TODO change the active and display state into menu
 */


package main.util;

import main.framework.Game;
import main.MainCanvas;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;


public class Menu extends KeyAdapter{
    public static int BACK = KeyEvent.VK_ESCAPE;
    public static int PAUSE = KeyEvent.VK_P;
    public static int ENTER = KeyEvent.VK_ENTER;
    public static int UP = KeyEvent.VK_W;
    public static int DOWN = KeyEvent.VK_S;
    public static int RIGHT = KeyEvent.VK_D;
    public static int RIGHT2 = KeyEvent.VK_B;
    public static int LEFT = KeyEvent.VK_A;
    public static int LEFT2 = KeyEvent.VK_C;

    protected LinkedList<Button> buttons;
    protected LinkedList<LabelBox> descriptions;

    protected int height = 0;
    protected int width = 0;
    protected int x;
    protected int y;
    protected int button_height = 0;
    protected int button_width = 0;
    //private static int H_UNIT = 0;
    //private static int V_UNIT = 0;

    protected int selectIndex = 0;

    protected Color menuColor = new Color (1,180,120,180);
    protected Color labelColor = new Color(255,255,255);
    protected Font font = new Font("华康少女文字W5",Font.PLAIN,15);

    //private Font font;

    protected MainCanvas mainCanvas;
    protected Game game;

    public boolean active = false;
    public boolean display = false;


    public Menu (MainCanvas mainCanvas, Game game, int x, int y, int width, int height){

        this.mainCanvas = mainCanvas;
        this.game = game;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        //this.font = fnt;
        buttons = new LinkedList<>();
    }

    public void tick(){
        select();
    }

    public void render(Graphics g){
        //System.out.println(display);
        Graphics2D g2d = (Graphics2D) g;
        float thickness = 2.5f;
        BasicStroke stroke = new BasicStroke(thickness,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        g.setColor(menuColor);
        g.fillRect(x,y,width,height);
        g2d.setStroke(stroke);
        g2d.setColor(labelColor);
        g2d.drawRect(x,y,width,height);
        for (int i = 0; i < buttons.size(); i++){
            buttons.get(i).render(g);
        }

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == DOWN){
            //System.out.println(selectIndex);
            selectIndex ++;
            if (selectIndex >= buttons.size()) {
                selectIndex = 0;
            }
        }
        if (key == UP){
            selectIndex --;
            if (selectIndex < 0){
                selectIndex = buttons.size()-1;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == ENTER){
        }
    }

    public void select (){
        for(int i = 0; i < buttons.size();i++){
            if (i != selectIndex){
                buttons.get(i).setSelected(false);
            }
            else {
                buttons.get(i).setSelected(true);
            }
        }
    }


    /*public void addButtonsList (Button button){
        this.buttons.add(button);
    }

    public Button getButtonList (int index){
        return this.buttons.get(index);
    }*/

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

    public int getButton_height() {
        return button_height;
    }

    public void setButton_height(int button_height) {
        this.button_height = button_height;
    }

    public int getButton_width() {
        return button_width;
    }

    public void setButton_width(int button_width) {
        this.button_width = button_width;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public Color getMenuColor() {
        return menuColor;
    }

    public void setMenuColor(Color menuColor) {
        this.menuColor = menuColor;
    }

    public LinkedList<Button> getButtons() {
        return buttons;
    }

    public void setButtons(LinkedList<Button> buttons) {
        this.buttons = buttons;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
