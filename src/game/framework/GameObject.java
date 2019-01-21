package game.framework;

import game.window.Handler;

import java.awt.*;
import java.io.Serializable;

public abstract class GameObject implements Serializable {

    protected float x, y;
    protected ID id;
    protected int width, height;
    protected Handler handler;

    public GameObject(float x, float y, ID id, Handler handler){
        this.x = x;
        this.y = y;
        this.id = id;
        this.handler = handler;
    }
    public void reinit(){

    }

    public void destroy(){
        handler.removeObject(this);
    }

    public abstract void tick();
    public abstract void render(Graphics g);
    public abstract Rectangle getBounds();

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public ID getId(){
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
