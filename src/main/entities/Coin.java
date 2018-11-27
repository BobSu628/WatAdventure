package main.entities;

import main.colliders.CoinCollider;
import main.colliders.Collider;
import main.entities.Entity;
import main.framework.Game;
import main.framework.GameObject;
import main.framework.ID;
import main.framework.Texture;
import main.window.Animation;
import main.window.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Coin extends Entity {

    private int value;
    private transient Texture texture;
    private transient Animation coinDisplay;

    public Coin(float x, float y, float velX, float velY, int facing, int value, ID id, Handler handler) {
        super(x, y, id, handler);
        this.value = value;
        this.MAX_SPEED = 4;
        this.velX = facing*velX;
        this.velY = velY;
        this.width = 32;
        this.height = 32;
        this.hp = Integer.MAX_VALUE; //hp cannot change
        this.texture = Game.getInstance();
        coinDisplay = new Animation(5,texture.coin[0],texture.coin[1],texture.coin[2],texture.coin[3],texture.coin[4]);

        Collider c = new CoinCollider(this);
        this.addCollider(c);
    }

    public void reinit(){
        this.texture = Game.getInstance();
        coinDisplay = new Animation(5,texture.coin[0],texture.coin[1],texture.coin[2],texture.coin[3],texture.coin[4]);
    }

    @Override
    public void tick() {
        super.tick();
        coinDisplay.runAnimation();
    }

    @Override
    public void render(Graphics g) {
        /*g.setColor(Color.yellow);
        g.drawRect((int)x, (int)y, width, height);

        ////////////
        // Collision mask debug

        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);

        g2d.draw(this.getBounds());
        g2d.draw(this.getBoundsTop());
        g2d.draw(this.getBoundsRight());
        g2d.draw(this.getBoundsLeft());

        ////////////
        */
        coinDisplay.drawAnimation(g,(int)x,(int)y,width,height);
    }

    public int getValue() {
        return value;
    }
}
