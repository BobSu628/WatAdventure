package game.entities;

import game.window.PlayerHandler;
import game.colliders.BasicEnemyCollider;
import game.colliders.Collider;
import game.framework.Game;
import game.framework.GameObject;
import game.framework.ID;
import game.framework.Texture;
import game.objects.Block;
import game.window.Animation;
import game.window.Handler;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;

public class TA extends Entity{

    public enum State implements Serializable {
        Patrol(),
        Confused(),
        Attack()
    }

    //Random random = new Random();

    private PlayerHandler playerHandler;
    private transient Texture texture;

    private transient Animation enemyWalk, enemyWalkLeft;
    transient Line2D.Float vision;
    int attackVel;
    int confusedTimer;
    public State state;

    public TA(float x, float y, ID id, PlayerHandler playerHandler, Handler handler){
        super(x, y, id, handler);
        this.playerHandler = playerHandler;
        this.width = 32;
        this.height = 64;
        this.normalVel = 2;
        this.attackVel = 3;
        this.velX = normalVel;
        this.hp = 10;
        this.meleeAtk = 1;
        this.knockbackVelX = 5;
        this.knockbackVelY = 8;
        this.jumpVel = -6;
        this.state = State.Patrol;
        float x1 = sightPoint.x, x2 = (float)playerHandler.myPlayer.getSightPoint().getX(), y1 = sightPoint.y, y2 = (float)playerHandler.myPlayer.getSightPoint().getY();
        this.vision = new Line2D.Float(x1, y1, x2, y2);
        
        texture = Game.getInstance();
        enemyWalk = new Animation(5, texture.enemy[1], texture.enemy[2], texture.enemy[3], texture.enemy[4], texture.enemy[5], texture.enemy[6]);
        enemyWalkLeft = new Animation(5, texture.enemy[8], texture.enemy[9], texture.enemy[10], texture.enemy[11], texture.enemy[12], texture.enemy[13]);
        //enemyMeleeAttackRight = new Animation(5, texture.enemy[0], texture.enemy[1]);
        //enemyMeleeAttackLeft = new Animation(5, texture.enemy[2], texture.enemy[3]);

        Collider c = new BasicEnemyCollider(this);
        this.addCollider(c);
    }
    
    public void reinit(){
        super.reinit();
        texture = Game.getInstance();
        enemyWalk = new Animation(5, texture.enemy[1], texture.enemy[2], texture.enemy[3], texture.enemy[4], texture.enemy[5], texture.enemy[6]);
        enemyWalkLeft = new Animation(5, texture.enemy[8], texture.enemy[9], texture.enemy[10], texture.enemy[11], texture.enemy[12], texture.enemy[13]);
        //enemyMeleeAttackRight = new Animation(5, texture.enemy[0], texture.enemy[1]);
        //enemyMeleeAttackLeft = new Animation(5, texture.enemy[2], texture.enemy[3]);
    }

    @Override
    public void destroy() {
        super.destroy();
        this.dropLoot(2);

    }

    @Override
    public void tick() {
        super.tick();
        //collider.collisionPlayer(playerHandler);
        ((BasicEnemyCollider)collider).collisionPlayer(playerHandler);
        state = identifyState();

        if(state == State.Attack && !inKnockBack){
            Point playerPosition = playerHandler.myPlayer.getSightPoint();
            if(playerPosition.getX() > this.sightPoint.getX()) {
                facing = 1;
                velX = attackVel;
            } else {
                facing = -1;
                velX = -attackVel;
            }
        }else if(state == State.Confused){
            velX = 0;
            confusedTimer --;
        } else if(state == State.Patrol){
            velX = facing*normalVel;
        }

        enemyWalk.runAnimation();
        enemyWalkLeft.runAnimation();

    }

    private State identifyState(){
        float x1 = sightPoint.x, x2 = (float)playerHandler.myPlayer.getSightPoint().getX(), y1 = sightPoint.y, y2 = (float)playerHandler.myPlayer.getSightPoint().getY();
        vision = new Line2D.Float(x1, y1, x2, y2);

        boolean noAttack = false;
        if(lengthSquared(x1, y1, x2, y2) > 500*500){
            noAttack = true;
        }
        //tooFar = false;
        if(!noAttack) {
            for (int i = 0; i < handler.object.size(); i++) {
                GameObject tempObject = handler.object.get(i);
                if (tempObject instanceof Block) {
                    if (vision.intersects(tempObject.getBounds())) {
                        noAttack = true;
                    }
                }
            }
        }

        if(noAttack){
            if(state == State.Attack){
                confusedTimer = 2*60;
                return State.Confused;
            } else if(state == State.Confused && confusedTimer > 0) {
                return State.Confused;
            }
            return State.Patrol;
        }

        return State.Attack;
    }

    private float lengthSquared(float x1, float y1, float x2, float y2){
        return (x1-x2)*(x1-x2)+(y1-y2)*(y1-y2);
    }

    @Override
    public void render(Graphics g) {

        ////////////
        // GameObject Debug
        //g.setColor(Color.green);
        //g.fillRect((int)x, (int)y, width, height); //display object
        //g.setColor(Color.white);
        //if(state == State.Confused) g.drawString("?", (int)x, (int) y);
        //g.drawString("facing: "+this.facing, (int)x, (int)y+15); //display facing
        //if(state == State.Patrol) g.drawString("patrol", (int)x, (int)y); //display patrol
        //else if(state == State.Attack) g.drawString("attack", (int)x, (int)y); //display attack
        //if(tooFar) g.drawString("Too Far", (int)x+15, (int)y); //display tooFar

        ////////////

        if(jumping){
            if(facing == 1) g.drawImage(texture.enemy_jump[0], (int)x, (int)y, width, height, null);
            else g.drawImage(texture.enemy_jump[1], (int)x, (int)y, width, height, null);
        }else{
            if(velX != 0) {
                if(facing == 1) {
                    enemyWalk.drawAnimation(g, (int) x, (int) y, width,  height);
                }else{
                    enemyWalkLeft.drawAnimation(g, (int)x, (int)y, width, height);
                }
            } else {
                if(facing == 1) {
                    g.drawImage(texture.enemy[0], (int) x, (int) y, width, height, null);
                }else{
                    g.drawImage(texture.enemy[7], (int)x, (int)y, width, height, null);
                }
            }
        }

        ////////////
        // Collider debug
        /*
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);

        g2d.draw(this.getBounds());
        g2d.draw(this.getBoundsTop());
        g2d.draw(this.getBoundsRight());
        g2d.draw(this.getBoundsLeft());
        g2d.draw(this.getJumpBoundLeft());
        g2d.draw(this.getJumpBoundRight());
        g2d.draw(this.getNoJumpBoundLeft());
        g2d.draw(this.getNoJumpBoundRight());
        */
        //g2d.draw(vision);

        ////////////
    }

    private void dropLoot(int qty){
        for(int i = 0; i < qty; i ++) {
            createLoot();
        }
    }

    private void createLoot(){
        float velX = (float)Math.random()+1;
        int facing = Math.random()>0.5?1:-1;
        handler.addObject(new Coin(x, y, velX, -5, facing, 1, ID.Coin, handler));

    }

    public Rectangle getJumpBoundLeft(){
        return new Rectangle((int)x-2*width/8, (int)y+height/2, width/8, height/4);
    }

    public Rectangle getJumpBoundRight(){
        return new Rectangle((int)x+width+width/8, (int)y+height/2, width/8, height/4);
    }

    public Rectangle getNoJumpBoundLeft(){
        return new Rectangle((int)x-2*width/8, (int)y+height/4, width/8, height/4);
    }

    public Rectangle getNoJumpBoundRight(){
        return new Rectangle((int)x+width+width/8, (int)y+height/4, width/8, height/4);
    }

}

