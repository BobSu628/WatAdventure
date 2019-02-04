package game.entities;

import game.colliders.BulletCollider;
import game.colliders.Collider;
import game.framework.Game;
import game.framework.ID;
import game.framework.Texture;
import game.framework.Handler;

import java.awt.*;
import java.util.Random;

public class Bullet extends Entity {

    private int type;
    private int lifespan = 1*60;
    public static int width = 16, height = 16, velXInit = 8;
    private transient Random random = new Random();
    private transient Texture texture;

    public Bullet(float x, float y, int type, ID id, float velX, float velY, int facing, int atk, Handler handler){
        super(x, y, id, handler);
        this.type = random.nextInt(5);
        this.texture = Game.getInstance();
        this.velX = facing*velX;
        this.velY = velY;
        this.meleeAtk = atk;
        //this.player = player;
        this.gravity = 0;
        this.facing = facing;

        Collider c = new BulletCollider(this);
        this.addCollider(c);

    }

    public void reinit(){

        random = new Random();
        texture = Game.getInstance();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        lifespan -= 1;
        if(lifespan < 0) this.destroy();

        collider.collision(handler);
    }

    @Override
    public void render(Graphics g) {
        /*
        /////////////
        //Object Debug
        g.setColor(Color.blue);
        g.fillRect((int)x, (int)y, width, height);
        /////////////

        /////////////
        //Collider Debug
        g.setColor(Color.red);
        g.drawRect((int)x, (int)y, width, height);
        /////////////
        */

        Image image = texture.iClicker[type];
        g.drawImage(image,(int)x, (int)y, width, height,null);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

}
