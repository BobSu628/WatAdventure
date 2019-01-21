package game.objects;

import game.framework.Game;
import game.framework.GameObject;
import game.framework.ID;
import game.framework.Texture;
import game.window.Handler;

import java.awt.*;

public class Ladder extends GameObject {
    transient Texture texture = Game.getInstance();

    public Ladder(float x, float y, ID id, Handler handler){
        super(x, y, id, handler);
        this.width = 32; this.height = 32;
    }

    public void reinit(){
        texture = Game.getInstance();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(texture.ladder, (int)x, (int)y, null);

        /////////////////
        //Collider debug
        /*
        g.setColor(Color.red);
        g.drawRect((int)x, (int)y, width, height);
        */

        /////////////////

    }

    public Rectangle getBounds(){
        return new Rectangle((int)x, (int)y, width, height);
    }
}
