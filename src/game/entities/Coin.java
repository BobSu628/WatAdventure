package game.entities;

import game.colliders.CoinCollider;
import game.colliders.Collider;
import game.framework.Game;
import game.framework.ID;
import game.framework.Texture;
import game.window.Animation;
import game.window.Handler;

import java.awt.*;

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
