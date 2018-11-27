package main.window;

import main.MainCanvas;
import main.framework.Game;
import main.framework.SaveSlot;
import main.util.ButtonFunction;
import main.util.Menu;
import main.util.SLButton;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.*;

public class LoadMenu extends Menu {
    private SLButton save1, save2, save3;

    public LoadMenu(MainCanvas mainCanvas, Game game, int x, int y, int width, int height){
        super(mainCanvas,game,x,y,width,height);

        button_height = height / 10 * 3;
        button_width = width;

        String[] timeStamps = mainCanvas.timeStamps;
        save1 = new SLButton (mainCanvas,x,y,button_width,button_height,timeStamps[1],labelColor,font,1, SLButton.LOAD);
        save2 = new SLButton (mainCanvas,x,y +button_height,button_width,button_height,timeStamps[2],labelColor,font,2,SLButton.LOAD);
        save3 = new SLButton (mainCanvas,x,y + button_height * 2,button_width,button_height,timeStamps[3],labelColor,font,3,SLButton.LOAD);

        buttons.add(save1);
        buttons.add(save2);
        buttons.add(save3);
    }

    public void updateSLButtons(){
        String[] timeStamps = mainCanvas.timeStamps;
        //System.out.println(timeStamps[1]);
        //save1 = new SLButton (mainCanvas,x,y,button_width,button_height,timeStamps[1],labelColor,font,1, SLButton.LOAD);
        save1.setLabel(timeStamps[1]);
        save2.setLabel(timeStamps[2]);
        save3.setLabel(timeStamps[3]);
    }

    public void setSLButtons(){

    }

    @Override
    public void tick(){
        //System.out.println(1);
        select();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == ENTER){
            this.display = false;
            this.active = false;
            mainCanvas.getMainMenu().setDisplay(true);
            buttons.get(selectIndex).getButtonFunction().Function();

        }

        if (key == BACK){
            mainCanvas.addKeyListener(mainCanvas.getMainMenu());
            mainCanvas.removeKeyListener(this);
            mainCanvas.getMainMenu().display = true;
            this.active = false;
            this.display = false;
            this.selectIndex = 0;
        }
    }



}
