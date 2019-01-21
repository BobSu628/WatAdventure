package game.framework;

import java.awt.*;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CommandArchive {

    private LinkedList<Command> commands = new LinkedList<>();
    private final int MAX_SIZE = 10;

    public int size(){
        return commands.size();
    }

    public Command getFirst(){
        try {
            return commands.getFirst();
        }catch (NoSuchElementException e){
            return new Command("", 0, Game.HEIGHT-Command.HEIGHT);
        }
    }

    public void addCommand(Command c){
        for(int i = 0; i < commands.size(); i ++){
            Command temp = commands.get(i);
            temp.setY(temp.getY()-Command.HEIGHT);
        }
        commands.add(c);
        if(commands.size() > MAX_SIZE){
            commands.remove(commands.getFirst());
        }

    }

    public void render(Graphics g, FontMetrics fm){
        for(int i = 0; i < commands.size(); i ++){
            commands.get(i).render(g);
        }
    }

}
