package game.colliders;

import game.entities.Entity;
import game.framework.GameObject;
import game.framework.ID;
import game.objects.Block;
import game.framework.Handler;

public class EntityCollider extends Collider {

    protected transient Entity entity;

    public EntityCollider(GameObject object) {
        super(object);
        this.entity = (Entity)object;
    }

    @Override
    public void reinit(GameObject object) {
        super.reinit(object);
        entity = (Entity)object;
    }

    @Override
    public void collision(Handler handler) {
        for(int i = 0; i < handler.object.size(); i ++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                Block block = (Block) tempObject;
                this.collideBlock(block);

            }
        }
    }

    protected void collideBlock(Block block){

        //right
        if(entity.getBoundsRight().intersects(block.getBounds())){
            entity.setX(block.getX() - entity.getWidth());
        }
        //left
        if(entity.getBoundsLeft().intersects(block.getBounds())){
            entity.setX(block.getX() + block.getWidth());
        }
        //bottom
        this.fallOnto(block);
        //top
        if(entity.getBoundsTop().intersects(block.getBounds())){
            entity.setY(block.getY() + block.getHeight());
            entity.setVelY(0);
        }
    }

    protected void fallOnto(GameObject obj){ //fall if entity does not stand on obj, motionless otherwise

        if(entity.getBounds().intersects(obj.getBounds())) {
            entity.setY(obj.getY() - entity.getHeight());
            entity.setVelY(0);
            entity.setFalling(false);
            entity.setJumping(false);

            // stop knockback effect
            if(entity.isInKnockBack()) {
                entity.setVelX(0); // suspend entity movement when knockback finishes
                entity.setInKnockBack(false);
            }
        }else{
            entity.setFalling(true);
        }
    }
}
