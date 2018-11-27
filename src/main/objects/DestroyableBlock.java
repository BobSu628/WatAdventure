package main.objects;

import main.framework.GameObject;
import main.framework.ID;
import main.window.Handler;

import java.awt.*;

public class DestroyableBlock extends Block {

    private float velY;
    private boolean active = false, falling = false;
    private int timer = 30;

    public DestroyableBlock(float x, float y, int type, ID id, Handler handler) {
        super(x, y, type, id, handler);
    }

    @Override
    public void reinit() {
        super.reinit();
    }

    @Override
    public void tick() {
        super.tick();
        y += velY;
        if(active && !falling){
            timer --;
            //System.out.println(timer);
            if(timer < 0) falling = true;
        }

        if(falling){
            this.velY = 3;
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        //g.setColor(Color.red);
        //g.drawRect((int)x, (int)y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return super.getBounds();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
