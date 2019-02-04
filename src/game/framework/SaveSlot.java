package game.framework;

import java.io.Serializable;

public class SaveSlot implements Serializable {
    //serializable object used to make a save file
    public String timeStamp;
    public Game game;

    public SaveSlot(String timeStamp, Game game){
        this.timeStamp = timeStamp;
        this.game = game;
    }

}
