package main.framework;

import java.io.Serializable;

public class SaveSlot implements Serializable {
    public String timeStamp;
    public Game game;

    public SaveSlot(String timeStamp, Game game){
        this.timeStamp = timeStamp;
        this.game = game;
    }

}
