package game.window;

import game.framework.Game;
import game.MainCanvas;
import game.items.Item;
import game.util.Button;
import game.util.ButtonFunction;
import game.util.ItemButton;
import game.util.Menu;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

public class UseMenu extends Menu {
    private int superSelectIndex;
    private LinkedList<Button> superButtons;

    private Button but_use,but_throw,but_equip;
    private Font font = new Font("华康少女文字W5",Font.PLAIN,15);


    public UseMenu(MainCanvas mainCanvas, Game game, int x, int y, int width, int height){
        super(mainCanvas, game, x,y,width,height);
        this.superSelectIndex = game.getPausedMenu().getItemMenu().getSelectIndex();
        this.superButtons = game.getPausedMenu().getItemMenu().getButtons();
        but_use = new Button (x,y,width,height/3,"Use", Color.WHITE,font,but_use_function);
        but_equip = new Button (x,y+height/3,width,height/3,"Equip",Color.WHITE,font,but_equip_function);
        but_throw = new Button (x,y+height/3*2,width,height/3,"Throw",Color.WHITE,font,but_throw_function);
        buttons.add(but_use);
        buttons.add(but_equip);
        buttons.add(but_throw);
    }

    @Override
    public void tick(){
        super.tick();
        if (buttons.get(selectIndex).isActive()){
            buttons.get(selectIndex).tick();
        }
    }

    @Override
    public void render(Graphics g){
        super.render(g);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == BACK){
            back();
        }

        if (key == ENTER){
            buttons.get(selectIndex).setActive(true);
            ItemButton tempButton = (ItemButton) superButtons.get(superSelectIndex);
            /*if ( tempButton.getNum() <= 1){
                buttons.get(selectIndex).getButtonFunction().Function();
                superButtons.clear();
                game.getPausedMenu().getItemMenu().initializeItemButtons();
                back();
            }
            else {*/
                buttons.get(selectIndex).getButtonFunction().Function();
                superButtons.clear();
                game.getPausedMenu().getItemMenu().initializeItemButtons();

        }
    }

    public void back (){
        game.getPausedMenu().getItemMenu().setDisplayUseMenu(false);
        game.getPausedMenu().getItemMenu().setActiveUseMenu(false);
        mainCanvas.addKeyListener(game.getPausedMenu().getItemMenu());
        mainCanvas.removeKeyListener(this);
        superButtons.clear();
        game.getPausedMenu().getItemMenu().initializeItemButtons();
    }

    /*public void backAfterUsedUp (){
        ItemButton tempItemButton = (ItemButton) superButtons.get(superSelectIndex);
        if (tempItemButton.getNum() <= 0){
            back();
        }
    }*/

    ButtonFunction but_use_function = () -> {
        ItemButton tempButton = (ItemButton) superButtons.get(superSelectIndex);
        if (tempButton.getNum() == 1){
            superButtons.get(superSelectIndex).getButtonFunction().Function();
            back();
        }
        else superButtons.get(superSelectIndex).getButtonFunction().Function();
    };

    ButtonFunction but_equip_function =() -> {
        ItemButton tempItemButton = (ItemButton) superButtons.get(superSelectIndex);
        Item tempItem = tempItemButton.getItem();
        game.getPlayerHandler().myPlayer.setEquippedItem(tempItem);
        back();

    };

    ButtonFunction but_throw_function =() -> {

        ItemButton tempButton = (ItemButton) superButtons.get(superSelectIndex);
        if (tempButton.getNum() <= 1) {
            ItemButton tempItemButton = (ItemButton) superButtons.get(superSelectIndex);
            Item tempItem = tempItemButton.getItem();
            tempItemButton.setNum(tempItemButton.getNum() - 1);
            tempItem.decreaseAfterUse(1);
            back();
        }
        else{
            ItemButton tempItemButton = (ItemButton) superButtons.get(superSelectIndex);
            Item tempItem = tempItemButton.getItem();
            tempItemButton.setNum(tempItemButton.getNum() - 1);
            tempItem.decreaseAfterUse(1);
        }
        /*if (game.getHandler().player.getItemMap().get(this) <= 0){
            game.getHandler().player.getItemMap().remove(this);
        }*/
    };
}
