package game.window;

import game.framework.Game;
import game.framework.GameObject;

import java.io.Serializable;

public class Camera implements Serializable {

    private float x, y;

    public Camera(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void tick(GameObject player){
        x = - player.getX() + Game.WIDTH / 2;
        y = - player.getY() + Game.HEIGHT / 2;
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

    public float getX(){
        return x;
    }
}
