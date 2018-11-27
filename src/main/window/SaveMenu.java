package main.window;

import main.MainCanvas;
import main.framework.Game;
import main.util.Button;
import main.util.ButtonFunction;
import main.util.Menu;
import main.util.SLButton;

import java.awt.event.KeyEvent;

public class SaveMenu extends Menu {

    private SLButton save1, save2, save3;

    public SaveMenu(MainCanvas mainCanvas, Game game, int x, int y, int width, int height){
        super(mainCanvas,game,x,y,width,height);
        button_height = height / 10 * 3;
        button_width = width;

        save1 = new SLButton (mainCanvas,x,y,button_width,button_height,"",labelColor,font,1, SLButton.SAVE);
        save2 = new SLButton (mainCanvas,x,y +button_height,button_width,button_height,"",labelColor,font,2,SLButton.SAVE);
        save3 = new SLButton (mainCanvas,x,y + button_height * 2,button_width,button_height,"",labelColor,font,3,SLButton.SAVE);

        buttons.add(save1);
        buttons.add(save2);
        buttons.add(save3);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == ENTER){
            buttons.get(selectIndex).getButtonFunction().Function();
        }

        if (key == BACK){
            mainCanvas.addKeyListener(mainCanvas.getGame().getPausedMenu());
            mainCanvas.removeKeyListener(this);
            game.getPausedMenu().getBut_save().setActive(false);
            game.getPausedMenu().getBut_save().setDisplay(false);
            game.getPausedMenu().setDisplay(true);
            this.selectIndex = 0;
        }
    }

}
