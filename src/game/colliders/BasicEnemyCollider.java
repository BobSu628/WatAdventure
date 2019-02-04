package game.colliders;

import game.window.PlayerHandler;
import game.entities.Entity;
import game.entities.TA;
import game.entities.Player;
import game.framework.GameObject;
import game.objects.Block;
import game.window.Handler;

public class BasicEnemyCollider extends EntityCollider{


    transient TA teachingAsistant;

    public BasicEnemyCollider(Entity entity){
        super(entity);
        this.teachingAsistant = (TA) entity;
    }

    public void reinit(GameObject object) {
        super.reinit(object);
        teachingAsistant = (TA) object;
    }

    @Override
    public void collision(Handler handler) {
        super.collision(handler);

    }

    public void collisionPlayer(PlayerHandler playerHandler){
        this.collidePlayer(playerHandler.myPlayer);
    }

    protected void collideBlock(Block block){

        //right
        if (teachingAsistant.getBoundsRight().intersects(block.getBounds())) {
            // if teachingAsistant is in knockback, treat as a rag doll
            if (!teachingAsistant.isInKnockBack() && teachingAsistant.state == TA.State.Patrol) {
                teachingAsistant.setX(block.getX() - teachingAsistant.getWidth());
                teachingAsistant.setVelX(-teachingAsistant.getNormalVel());
                teachingAsistant.setFacing(-teachingAsistant.getFacing());
            } else {
                teachingAsistant.setX(block.getX() - teachingAsistant.getWidth());
            }
        }
        //left
        if (teachingAsistant.getBoundsLeft().intersects(block.getBounds())) {

            if (!teachingAsistant.isInKnockBack() && teachingAsistant.state == TA.State.Patrol) {
                teachingAsistant.setX(block.getX() + block.getWidth());
                teachingAsistant.setVelX(-teachingAsistant.getNormalVel());
                teachingAsistant.setFacing(-teachingAsistant.getFacing());
            } else {
                teachingAsistant.setX(block.getX() + block.getWidth());
            }
        }
        //bottom
        this.fallOnto(block);
        //top
        if (teachingAsistant.getBoundsTop().intersects(block.getBounds())) {
            teachingAsistant.setY(block.getY() + block.getHeight());
            teachingAsistant.setVelY(0);
        }

        if(teachingAsistant.state == TA.State.Attack) {
            //jump right
            if (teachingAsistant.getJumpBoundRight().intersects(block.getBounds()) && !teachingAsistant.getNoJumpBoundRight().intersects(block.getBounds())) {
                teachingAsistant.jump();
            }
            //jump left
            if(teachingAsistant.getJumpBoundLeft().intersects(block.getBounds()) && !teachingAsistant.getNoJumpBoundLeft().intersects(block.getBounds())){
                teachingAsistant.jump();
            }
        }

    }

    protected void fallOnto(GameObject obj){ //fall if teachingAsistant does not stand on obj, motionless otherwise

        if(teachingAsistant.getBounds().intersects(obj.getBounds())) {
            teachingAsistant.setY(obj.getY() - teachingAsistant.getHeight());
            teachingAsistant.setVelY(0);
            teachingAsistant.setFalling(false);
            teachingAsistant.setJumping(false);

            // stop knockback effect, recover teachingAsistant movement
            if(teachingAsistant.isInKnockBack()) {
                teachingAsistant.setVelX(teachingAsistant.getFacing()* teachingAsistant.getNormalVel());
                teachingAsistant.setInKnockBack(false);
            }
        }else{
            teachingAsistant.setFalling(true);
        }
    }

    /*
    * Check if this object collides with a player
     */
    private void collidePlayer(Player player){
        //left
        if(intersectsEntity(teachingAsistant.getBoundsLeft(), player)){
            hit(teachingAsistant, player, -1);
        }
        //right
        if(intersectsEntity(teachingAsistant.getBoundsRight(), player)){
            hit(teachingAsistant, player, 1);
        }
    }

}
