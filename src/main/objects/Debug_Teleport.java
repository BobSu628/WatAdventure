package main.objects;

import main.framework.GameObject;
import main.framework.ID;
import main.window.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Debug_Teleport extends GameObject{

    public Debug_Teleport(float x, float y, ID id, Handler handler){
        super(x, y, id, handler);
        this.width = 32; this.height = 32;
    }

    public void tick(){

    }

    public void render(Graphics g){
        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, width, height);
    }

    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, width, height);
    }
}
