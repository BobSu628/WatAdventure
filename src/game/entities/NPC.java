package game.entities;

import game.framework.ID;
import game.framework.Handler;

import java.awt.*;

public class NPC extends Entity{
    public NPC(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);

    }

    @Override
    public void reinit() {
        super.reinit();
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int)x, (int)y, width, height);
    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
