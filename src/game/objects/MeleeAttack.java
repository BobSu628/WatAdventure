package game.objects;

import game.framework.GameObject;
import game.framework.ID;
import game.window.Handler;

import java.awt.*;

public class MeleeAttack extends GameObject {

    public static int width = 10, height = 32;

    public MeleeAttack(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);

    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        /////////////
        // GameObject Debug
/*        g.setColor(Color.red);
        g.fillRect((int)x, (int)y, width, height);
*/        /////////////
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

}
