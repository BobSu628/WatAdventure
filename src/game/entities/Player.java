package game.entities;

import game.window.PlayerHandler;
import game.framework.Game;
import game.colliders.PlayerCollider;
import game.framework.ID;
import game.framework.Texture;
import game.objects.MeleeAttack;
import game.window.Animation;
import game.framework.Handler;

import java.awt.*;
import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

/*
TODO Crouch
 */

public class Player extends Entity {

    private UUID uuid;
    private String name;

    public enum AttackState implements Serializable {
        Melee(),
        Ranged(),
        Magic()
    }

    private static final int WALK_X = 5;
    private static final int RUN_X = 8;

    private transient Texture texture;
    private transient Animation playerWalk, playerWalkLeft, playerRangeAttackRight, playerRangeAttackLeft, playerMeleeAttackRight, playerMeleeAttackLeft;
    private MeleeAttack meleeAttack = null;
    private boolean attacking = false;
    private boolean canAttack = true;

    private boolean onLadder = false;
    private boolean isClimbing = false, canClimb = false;
    private int attackTimer = 20;
    private transient Random random = new Random();
    private int coin = 0;
    private AttackState attackState;

    private transient PlayerHandler playerHandler;

    //Iterator<Map.Entry> listItems;

    public Player(float x, float y, UUID uuid, ID id, String name, PlayerHandler playerHandler, Handler handler){
        super(x, y, id, handler);
        this.playerHandler = playerHandler;
        this.uuid = uuid; //ID on network
        this.name = name; //user name
        this.width = 32; //width of whole sprite
        this.height = 64; //height of whole sprite
        this.MAX_SPEED = 16;
        this.maxHp = 10;
        this.hp = maxHp;
        this.meleeAtk = 1;
        this.rangeAtk = 2;
        this.knockbackVelX = 5;
        this.knockbackVelY = 8;
        this.attackState = AttackState.Ranged;
        this.jumpVel = -11;
        //define animations for the object
        texture = Game.getInstance();
        playerWalk = new Animation(5, texture.player[1], texture.player[2], texture.player[3], texture.player[4], texture.player[5], texture.player[6]);
        playerWalkLeft = new Animation(5, texture.player[8], texture.player[9], texture.player[10], texture.player[11], texture.player[12], texture.player[13]);
        playerMeleeAttackRight = new Animation(5,texture.player_attack[0],texture.player_attack[1]);
        playerMeleeAttackLeft = new Animation(5,texture.player_attack[2],texture.player_attack[3]);
        playerRangeAttackRight = new Animation(5,texture.player_attack[5]);
        playerRangeAttackLeft = new Animation(5,texture.player_attack[7]);
        //collider
        this.addCollider(new PlayerCollider(this));

    }

    public void reinit(Game game){
        super.reinit();
        texture = Game.getInstance();
        playerHandler = game.getPlayerHandler();
        //redefine animations
        playerWalk = new Animation(5, texture.player[1], texture.player[2], texture.player[3], texture.player[4], texture.player[5], texture.player[6]);
        playerWalkLeft = new Animation(5, texture.player[8], texture.player[9], texture.player[10], texture.player[11], texture.player[12], texture.player[13]);
        playerMeleeAttackRight = new Animation(5,texture.player_attack[0],texture.player_attack[1]);
        playerMeleeAttackLeft = new Animation(5,texture.player_attack[2],texture.player_attack[3]);
        playerRangeAttackRight = new Animation(5,texture.player_attack[4],texture.player_attack[5]);
        playerRangeAttackLeft = new Animation(5,texture.player_attack[6],texture.player_attack[7]);
        random = new Random();
    }

    @Override
    public void destroy() {

    }

    @Override
    public void tick() {
        super.tick();

        //movement
        if(!inKnockBack) {
            if (playerHandler.isRightRun()) {
                facing = 1;
                velX = RUN_X;
            } else if (playerHandler.isRightWalk()) {
                facing = 1;
                velX = WALK_X;
            } else if (!playerHandler.isLeftRun() && playerHandler.isLeftWalk()) velX = 0;

            if (playerHandler.isLeftRun()) {
                facing = -1;
                velX = -RUN_X;
            } else if (playerHandler.isLeftWalk()) {
                facing = -1;
                velX = -WALK_X;
            } else if (!playerHandler.isRightRun() && !playerHandler.isRightWalk()) velX = 0;

            if(onLadder) canClimb = true;
            else canClimb = false;

            if(onLadder && isClimbing){
                velY = -3;
                falling = false;
            }else{
                falling = true;
            }

        }else{
            //if in knockback, player has no control
            playerHandler.setLeftWalk(false);
            playerHandler.setRightWalk(false);
            playerHandler.setLeftRun(false);
            playerHandler.setRightRun(false);
        }

        //attack
        if(this.attacking) {
            if(attackTimer < 0){
                attackTimer = 20;
                attacking = false;
                meleeAttacking = false;
                handler.removeObject(meleeAttack);
            } else{
                attackTimer --;
                if(facing == 1) {
                    this.meleeAttack.setX(x + width);
                }else{
                    this.meleeAttack.setX(x - MeleeAttack.width);
                }
                this.meleeAttack.setY(y + height / 4.0f);
            }
        }
        onLadder = false;

        //animation
        playerWalk.runAnimation();
        playerWalkLeft.runAnimation();
        playerMeleeAttackRight.runAnimation();
        playerMeleeAttackLeft.runAnimation();
        playerRangeAttackRight.runAnimation();
        playerRangeAttackLeft.runAnimation();

        //System.out.println(rangeAttacking);
    }


    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);
        g.drawString(this.name, (int)x, (int)y);
        //g.drawString(String.valueOf(this.hp), (int)x, (int)y);

        //if(equippedItem != null) g.drawString(equippedItem.toString()+" x "+itemMap.get(equippedItem), (int)x+15, (int)y-15);
        //g.drawString("Coin: "+this.coin, (int)x+15, (int)y);

        if(jumping){
            if (meleeAttacking || rangeAttacking){
                attackAnimation(g);
            }
            else if(facing == 1) g.drawImage(texture.player_jump[0], (int)x, (int)y, width, height, null);
            else g.drawImage(texture.player_jump[1], (int)x, (int)y, width, height, null);
        }else{
            if (meleeAttacking) {
                if (facing == 1) {
                    playerMeleeAttackRight.drawAnimation(g, (int) x, (int) y, width, height);
                    //System.out.println("melee");
                } else {
                    playerMeleeAttackLeft.drawAnimation(g, (int) x, (int) y, width, height);
                }
            }
            else if (rangeAttacking) {
                if (facing == 1) {
                    playerRangeAttackRight.drawAnimation(g, (int) x, (int) y, width, height);
                } else {
                    playerRangeAttackLeft.drawAnimation(g, (int) x, (int) y, width, height);
                }
            }
            else if(velX != 0) {
                if(facing == 1) {
                    playerWalk.drawAnimation(g, (int) x, (int) y, width,  height);
                }else{
                    playerWalkLeft.drawAnimation(g, (int)x, (int)y, width, height);
                }
            } else {
                if(facing == 1) {
                    g.drawImage(texture.player[0], (int) x, (int) y, width, height, null);
                }else{
                    g.drawImage(texture.player[7], (int)x, (int)y, width, height, null);
                }
            }
        }

        ////////////
        // Collision mask debug
/*
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);

        g2d.draw(this.getBounds());
        g2d.draw(this.getBoundsTop());
        g2d.draw(this.getBoundsRight());
        g2d.draw(this.getBoundsLeft());
*/
        ////////////
    }

    public void attack(){
        if(this.attackState == AttackState.Melee){
            this.meleeAttack();
            this.meleeAttacking = true;
        }else if(this.attackState == AttackState.Ranged){
            this.rangedAttack();
        }
    }

    /*
     */
    private void rangedAttack(){
        //attacking = true;
        float attackX = x+width, attackY = y+height/4.0f;
        float bulletVelX = Bullet.velXInit + velX;
        float bulletVelY = (random.nextInt(10)-5)/10.0f; //randomize bulletVelY
        if(facing == -1) attackX = x - Bullet.width;

        if(velX < 0) bulletVelX = Bullet.velXInit - velX;


        Bullet bullet = new Bullet(attackX, attackY, 0, ID.Bullet, bulletVelX, bulletVelY, this.facing, this.rangeAtk, this.handler);
        this.handler.addObject(bullet);
    }

    private void meleeAttack(){
        attacking = true;
        float attackX = x+width, attackY = y+height/4;
        if(facing == -1) attackX = x-MeleeAttack.width;

        MeleeAttack meleeAttack = new MeleeAttack(attackX, attackY, ID.Attack, handler);
        this.meleeAttack = meleeAttack;
        handler.addObject(meleeAttack);
    }

    public MeleeAttack getMeleeAttack() {
        return meleeAttack;
    }

    /*
    TODO: integrate all attack types
    set an attack object for player
     */
    public void setMeleeAttack(MeleeAttack meleeAttack) {
        this.meleeAttack = meleeAttack;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setCanAttack(boolean canAttack) {
        this.canAttack = canAttack;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public boolean isCanClimb() {
        return canClimb;
    }

    public void setCanClimb(boolean canClimb) {
        this.canClimb = canClimb;
    }


    public boolean isOnLadder() {
        return onLadder;
    }

    public void setOnLadder(boolean onLadder) {
        this.onLadder = onLadder;
    }

    public boolean isClimbing() {
        return isClimbing;
    }

    public void setClimbing(boolean climbing) {
        isClimbing = climbing;
    }

    public int getCoin() {
        return coin;
    }

    public void addCoin(int coin) {
        this.coin += coin;
    }

    public void switchAttackState(){
        if(attackState == AttackState.Melee) attackState = AttackState.Ranged;
        else if(attackState == AttackState.Ranged) attackState = AttackState.Melee;
    }

    public void attackAnimation (Graphics g){
            if (meleeAttacking) {
                if (facing == 1) {
                    playerMeleeAttackRight.drawAnimation(g, (int) x, (int) y, width, height);
                    //System.out.println("melee");
                } else {
                    playerMeleeAttackLeft.drawAnimation(g, (int) x, (int) y, width, height);
                }
            }
            else if (rangeAttacking) {
                if (facing == 1) {
                    playerRangeAttackRight.drawAnimation(g, (int) x, (int) y, width, height);
                }
                else {
                    playerRangeAttackLeft.drawAnimation(g,(int)x,(int)y,width,height);
            }
        }
        //System.out.println("after");
    }


    public AttackState getAttackState() {
        return attackState;
    }

    public void setAttackState(AttackState attackState) {
        this.attackState = attackState;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public UUID getUUID() {
        return uuid;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
