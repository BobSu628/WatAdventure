package game.objects;

import game.framework.GameObject;
import game.framework.ID;
import game.framework.Handler;

import java.awt.*;

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
