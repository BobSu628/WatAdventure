package game.util;

import game.MainCanvas;

import java.awt.*;
import java.util.Date;

public class SLButton extends Button {

    private int identifier;
    private String timestamp;
    public static boolean SAVE = true, LOAD = false;



    public SLButton(MainCanvas mainCanvas, int x, int y, int width, int height, String label, Color color, Font fnt, int identifier, boolean sl){
        super(x,y,width,height,label,color,fnt,null);

        if (sl == SAVE) {
            this.label = mainCanvas.timeStamps[identifier];
            this.buttonFunction = () -> {
                timestamp = new Date().toString();
                mainCanvas.saveGame(timestamp, identifier);
                this.label = timestamp;
                return true;
            };
        }
        else if (sl == LOAD){
            this.label = label;
            this.buttonFunction = () -> {
                if(!this.label.equals("(Empty)")) {
                    mainCanvas.loadGame(identifier);
                    return true;
                }
                return false;
            };
        }
        this.identifier = identifier;

    }


}
