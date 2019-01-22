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
            ButtonFunction f = () -> {
                timestamp = new Date().toString();
                mainCanvas.saveGame(timestamp, identifier);
                this.label = timestamp;
            };
            this.buttonFunction = f;
        }
        else if (sl == LOAD){
            this.label = label;
            ButtonFunction f = () -> {
                if(!this.label.equals("(Empty)")) {
                    mainCanvas.loadGame(identifier);
                    //this.label = "Saved";
                }
            };
            this.buttonFunction = f;
        }
        this.identifier = identifier;

    }


}
