/*
    TODO item里面newbutton，itemmenu里面刷新显示状态和位置
    TODO item里面不同的页面
 */


package game.window;

import game.framework.Game;
import game.MainCanvas;
import game.entities.Player;
import game.items.Item;
import game.util.Button;
import game.util.Menu;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Map;

public class ItemMenu extends Menu {
    public static int BACK = KeyEvent.VK_ESCAPE;
    //public ItemButton itembuttons;
    public Button button;
    public Menu description;
    public static int descriptionHeight;
    public static int buttonY;
    public static int buttonX;
    public static int buttonWidth;
    public static int buttonHeight;
    public static int listWidth;
    public static int listHeight;

    public int index = 0;

    private int tempItemNum;
    private UseMenu useMenu;
    private static int useMenuX, useMenuY, useMenuHeight,useMenuWidth;
    private boolean displayUseMenu = false, activeUseMenu = false;


    //public LinkedList<ItemButton> itemButtons = new LinkedList<>();

    //Iterable <Map.Entry> listItem = game..entrySet


    private Font fnt;
    private Color descriptionColor = new Color(0,51,152,130);

    public ItemMenu(MainCanvas mainCanvas, Game game, int x, int y, int width, int height, Button button) {
        super(mainCanvas, game, x, y, width, height);
        this.button = button;
        //this.itembutton = itembutton;
        descriptionHeight = 2 * PausedMenu.getButton_height();
        listHeight = height - descriptionHeight;
        buttonY = y + descriptionHeight;
        buttonX = x;
        buttonWidth = width;
        buttonHeight = listHeight / 10;

        listWidth = width;
        listHeight = height - descriptionHeight;

        menuColor = new Color(72,192,163,200);
    }

    public void tick() {
        select();

        if (activeUseMenu){
            useMenu.tick();
        }

    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        float thickness = 2.5f;
        Stroke originStroke = g2d.getStroke();
        BasicStroke stroke = new BasicStroke(thickness,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);
        g.setColor(menuColor);
        g.fillRect(x,y,width,height);
        g2d.setStroke(stroke);
        g2d.setColor(new Color(255,255,255));
        g2d.drawRect(x,y,width,height);
        for (int i = 0; i < buttons.size(); i++){
            buttons.get(i).render(g);
        }

        thickness = 1.5f;
        stroke = new BasicStroke(thickness, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2d.setStroke(stroke);

        g2d.setColor(descriptionColor);
        g2d.drawLine(x, y+descriptionHeight, x+width, y+descriptionHeight);

        /*Player player = game.getHandler().player;
        Iterator<Map.Entry<Item, Integer>> listItems = player.getItemMap().entrySet().iterator();
        while (listItems.hasNext()) {
            Item tempItem = listItems.next().getKey();
            tempItem.getItemButton().render(g);
        }*/
        for (int i = 0; i < buttons.size(); i ++){
            buttons.get(i).render(g);
        }

        if (displayUseMenu){
            useMenu.render(g);
        }

    }


    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == BACK) {
            if (buttons.size() > 0) {
                game.getPausedMenu().buttons.get(selectIndex).setActive(false);
            }
            game.getPausedMenu().setDisplay(true);
            game.getPausedMenu().but_item.setDisplay(false);
            mainCanvas.addKeyListener(game.getPausedMenu());
            mainCanvas.removeKeyListener(this);
            buttons.clear();
        }

        if (key == ENTER){
            if (buttons.size() > 0) {
                initializeUseMenu();
                displayUseMenu = true;
                activeUseMenu = true;
                mainCanvas.addKeyListener(useMenu);
                mainCanvas.removeKeyListener(this);
            }
        }
    }

    public void initializeItemButtons() {
        Player player = game.getHandler().player;
        Iterator<Map.Entry<Item, Integer>> listItems = player.getItemMap().entrySet().iterator();
        index = 0;
        while (listItems.hasNext()) {
            index++;
            Map.Entry tempEntry = listItems.next();
            Item tempItem = (Item) tempEntry.getKey();
            tempItemNum =(int) tempEntry.getValue();
            tempItem.getItemButton().setNum(tempItemNum);
            //buttons.add(tempItem.getItemButton());
            if (tempItemNum > 0) {
                buttons.add(tempItem.getItemButton());
            }
        }
        for (int i = 0; i < buttons.size(); i++) {
            //ItemButton tempItemButton = (ItemButton) buttons.get(i);
            //tempItemButton.getDescriptionLabelBox().initializeItemMenuDescriptionParameter();
            buttons.get(i).getDescriptionLabelBox().initializeItemMenuDescriptionParameter();
            buttons.get(i).setX(buttonX);
            buttons.get(i).setY(buttonY + i * buttonHeight);
            buttons.get(i).setWidth(buttonWidth);
            buttons.get(i).setHeight(buttonHeight);
        }
        index = 0;
    }

    public void initializeUseMenu(){
        useMenuX = x + width;
        useMenuY = buttonY + selectIndex * buttonHeight;
        useMenuWidth = 90;
        useMenuHeight = 120;
        useMenu = new UseMenu(mainCanvas,game, useMenuX,useMenuY,useMenuWidth,useMenuHeight);
    }

   /* @Override
    public void select() {
        Player player = game.getHandler().player;
        Iterator<Map.Entry<Item, Integer>> listItems = player.getItemMap().entrySet().iterator();
        while (listItems.hasNext()) {
            index++;
            Item tempItem = listItems.next().getKey();
            if (index == selectIndex) {
                tempItem.getItemButton().setSelected(true);
            } else {
                tempItem.getItemButton().setSelected(false);
            }
        }
    }*/


    public boolean isDisplayUseMenu() {
        return displayUseMenu;
    }

    public void setDisplayUseMenu(boolean displayUseMenu) {
        this.displayUseMenu = displayUseMenu;
    }

    public boolean isActiveUseMenu() {
        return activeUseMenu;
    }

    public void setActiveUseMenu(boolean activeUseMenu) {
        this.activeUseMenu = activeUseMenu;
    }


}
