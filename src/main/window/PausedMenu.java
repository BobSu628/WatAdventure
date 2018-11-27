//


package main.window;

import main.framework.Game;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.security.PrivateKey;
import java.util.LinkedList;

import main.MainCanvas;
import main.util.Button;
import main.util.ButtonFunction;

public class PausedMenu extends KeyAdapter {
    public static int BACK = KeyEvent.VK_ESCAPE;
    public static int PAUSE = KeyEvent.VK_P;
    public static int ENTER = KeyEvent.VK_ENTER;
    public static int UP = KeyEvent.VK_W;
    public static int DOWN = KeyEvent.VK_S;
    public static int RIGHT = KeyEvent.VK_D;
    public static int RIGHT2 = KeyEvent.VK_B;
    public static int LEFT = KeyEvent.VK_A;
    public static int LEFT2 = KeyEvent.VK_C;
    private static int height = Game.HEIGHT / 4; // 240
    private static int width = Game.WIDTH /7 ;  // 182
    private int x;
    private int y;
    //private static int button_height = height / 6;
    private static int button_height = height / 4;
    private static int button_width = width;
    public static int H_UNIT = Game.WIDTH / 7; // 182
    public static int V_UNIT = Game.HEIGHT / 8; // 120

    //Above is the basic parameter for position and key

    //basic parameter that are passed into menu
    private int selectIndex = 0;

    private Color menuColor;
    private Color buttonColor;

    private String label = "Continue";

    private Font font;

    public LinkedList<Button> buttons;

    private boolean active = false;
    private boolean display = false;

    public Button but_cont, but_item,but_equipment, but_status, but_save, but_quit;

    //subMenu
    private int subDisplayingMenu_x = 3 * H_UNIT / 2;
    private int subDisplayingMenu_y = 2 * V_UNIT + 2 * button_height;
    private ItemMenu itemMenu;
    private SaveMenu saveMenu;
    public static int iM_x = 5 * H_UNIT / 2;
    public static int iM_y = 2 * V_UNIT;
    public static int iMWidth = 3 * H_UNIT;
    public static int iMHeight = 4 * V_UNIT;

    private CoinDisplay coinDisplay;

    private MainCanvas mainCanvas;
    private Game game;


    public PausedMenu(MainCanvas mainCanvas, Game game){
        this.mainCanvas = mainCanvas;
        this.game = game;
        buttons = new LinkedList<>();
        x = 3*H_UNIT;
        y = 3*V_UNIT;
        menuColor = new Color (1,180,120,180);
        font = new Font("Bradley Hand ITC",font.BOLD,height/16);
        buttonColor = new Color(255,255,255,0);
        but_cont = new Button(x,y,button_width,button_height,"Continue",buttonColor,font,but_cont_func);
        but_item = new Button(x,y+button_height,button_width,button_height,"Items",buttonColor,font,but_item_func);
        but_equipment = new Button(x,y+button_height*2,button_width,button_height,"Equipment",buttonColor,font,but_equipment_func);
        but_status = new Button(x,y+button_height*3,button_width,button_height,"Status",buttonColor,font,but_status_func);
        but_save = new Button(x,y+button_height*4,button_width,button_height,"Save",buttonColor,font,but_save_func);
        but_quit = new Button(x,y+button_height*5,button_width,button_height,"Back to Title",buttonColor,font,but_quit_func);
        buttons.add(but_cont);
        buttons.add(but_item);
        //buttons.add(but_equipment);
        //buttons.add(but_status);
        buttons.add(but_save);
        buttons.add(but_quit);
        itemMenu = new ItemMenu(mainCanvas, game,iM_x,iM_y, iMWidth,iMHeight,but_item);

        saveMenu = new SaveMenu(mainCanvas, game,iM_x,iM_y, iMWidth,iMHeight);
        coinDisplay = new CoinDisplay(mainCanvas,game,subDisplayingMenu_x + 25,ItemMenu.buttonY + ItemMenu.listHeight - button_height*3/2,width - 25,button_height*3/2);


    }
    public void tick(){
        select();

        if (but_item.isActive()){
            coinDisplay.tick();
            but_item.tick();
            itemMenu.tick();
        }

        if (but_status.isActive()){

        }

        if (but_equipment.isActive()){

        }

        if (but_save.isActive()){
            coinDisplay.tick();
            saveMenu.tick();
        }

    }



    public void render(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        float thickness = 2.5f;
        Stroke originStroke = g2d.getStroke();
        BasicStroke stroke = new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g.setColor(menuColor);
        g2d.setStroke(stroke);
       if(this.isDisplay()) {
           drawMenu(g,g2d,x,y);
       }
       else {
           if (but_item.isDisplay()) {
               coinDisplay.render(g);
               drawMenu(g, g2d, subDisplayingMenu_x, subDisplayingMenu_y);
               itemMenu.render(g);
           }

           else if (but_status.isDisplay()) {
               coinDisplay.render(g);
               drawMenu(g, g2d, subDisplayingMenu_x, subDisplayingMenu_y);
           }

           else if (but_quit.isDisplay()) {
               coinDisplay.render(g);
               drawMenu(g, g2d, subDisplayingMenu_x, subDisplayingMenu_y);
           }

           else if (but_save.isDisplay()) {
               coinDisplay.render(g);
               drawMenu(g, g2d, subDisplayingMenu_x, subDisplayingMenu_y);
               saveMenu.render(g);
           }
       }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == DOWN){
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
        if (key == BACK || key == PAUSE){
            but_cont_func.Function();
        }

        if (key == ENTER){
            initalizeDrawCoin();
            buttons.get(selectIndex).setActive(true);
            buttons.get(selectIndex).getButtonFunction().Function();
        }
    }

    private void select (){
        for(int i = 0; i < buttons.size();i++){
            if (i != selectIndex){
                buttons.get(i).setSelected(false);
            }
            else {
                buttons.get(i).setSelected(true);
            }
        }
    }

    ButtonFunction but_cont_func = () -> {
        //Game.paused = false;
        this.setActive(false);
        mainCanvas.addKeyListener(game.getKeyInput());
        mainCanvas.removeKeyListener(this);
    };

    ButtonFunction but_item_func = () -> {
        game.getPausedMenu().setDisplay(false);
        game.getPausedMenu().but_item.setDisplay(true);
        mainCanvas.addKeyListener(this.getItemMenu());
        mainCanvas.removeKeyListener(this);
        game.getPausedMenu().getItemMenu().initializeItemButtons();
    };

    ButtonFunction but_status_func = () -> {
        game.getPausedMenu().setDisplay(false);
        game.getPausedMenu().but_status.setDisplay(true);
    };
    ButtonFunction but_equipment_func = () -> {
        game.getPausedMenu().setDisplay(false);
        game.getPausedMenu().but_equipment.setDisplay(true);
    };
    ButtonFunction but_save_func = () -> {
        game.getPausedMenu().setDisplay(false);
        game.getPausedMenu().but_save.setDisplay(true);
        mainCanvas.addKeyListener(this.getSaveMenu());
        mainCanvas.removeKeyListener(this);
    };


    ButtonFunction but_quit_func =() -> {
        mainCanvas.getMainMenu().setDisplay(true);
        mainCanvas.getMainMenu().getLoadMenu().setActive(false);
        mainCanvas.getMainMenu().getLoadMenu().setDisplay(false);
        MainCanvas.state = MainCanvas.STATE.MainMenu;
        mainCanvas.addKeyListener(mainCanvas.getMainMenu());
        mainCanvas.removeKeyListener(this);

    };

    /*public void contGame (){
        Game.paused = false;
        this.setActive(false);
        game.addKeyListener(game.getKeyInput());
        game.removeKeyListener(this);
        return;
    }*/


    public void drawMenu (Graphics g, Graphics2D g2d, int x,int y){
        g.setColor(menuColor);
        g.fillRect(x, y, width, height);
        g2d.setColor(new Color(255, 255, 255));
        g2d.drawRect(x, y, width, height);

        for (int i = 0; i < buttons.size(); i ++){
            buttons.get(i).setX(x);
            buttons.get(i).setY(y + button_height * i);
            buttons.get(i).render(g);
        }

       /* but_cont.setX(x);
        but_cont.setY(y);
        but_item.setX(x);
        but_item .setY(y + button_height);
        but_equipment.setX(x);
        but_equipment.setY(y + button_height * 2);
        but_status.setX(x);
        but_status.setY(y + button_height * 3);
        but_help.setX(x);
        but_help.setY(y + button_height * 4);
        but_quit.setX(x);
        but_quit.setY(y);

        but_cont.render(g);
        but_item.render(g);
        but_equipment.render(g);
        but_status.render(g);
        but_help.render(g);
        but_quit.render(g);*/
    }

    public void initalizeDrawCoin (){
        //coinDisplay.setCoinQuantity(game.getHandler().player.getCoin());
        coinDisplay.setLabel(game.getHandler().player.getCoin());
    }

    public ItemMenu getItemMenu (){return this.itemMenu;}


    public void setActive (boolean active){
        this.active = active;
    }
    public boolean isActive (){
        return this.active;
    }

    public static int getHeight() {
        return height;
    }

    public static int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static int getButton_height() {
        return button_height;
    }

    public static int getButton_width() {
        return button_width;
    }

    public static int gethUnit() {
        return H_UNIT;
    }

    public static int getvUnit() {
        return V_UNIT;
    }

    public Color getMenuColor() {
        return menuColor;
    }

    public Color getButtonColor() {
        return buttonColor;
    }

    public String getLabel() {
        return label;
    }

    public Font getFont() {
        return font;
    }

    public boolean isDisplay() {
        return display;
    }

    public void setDisplay(boolean display) {
        this.display = display;
    }

    public int getSelectIndex() {
        return selectIndex;
    }

    public void setSelectIndex(int selectIndex) {
        this.selectIndex = selectIndex;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public SaveMenu getSaveMenu() {
        return saveMenu;
    }


    public Button getBut_save() {
        return but_save;
    }

    public void setBut_save(Button but_save) {
        this.but_save = but_save;
    }
}



