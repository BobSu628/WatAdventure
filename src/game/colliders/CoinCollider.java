package game.colliders;

import game.entities.Entity;
import game.framework.GameObject;
import game.window.Handler;

public class CoinCollider extends EntityCollider {

    public CoinCollider(Entity entity) {
        super(entity);
    }

    @Override
    public void collision(Handler handler) {
        super.collision(handler);
    }

    protected void fallOnto(GameObject obj){ //fall if entity does not stand on obj, motionless otherwise

        if(entity.getBounds().intersects(obj.getBounds())) {
            entity.setY(obj.getY() - entity.getHeight());
            entity.setVelY(0);
            entity.setFalling(false);
            entity.setVelX(0);
        }else{
            entity.setFalling(true);
        }
    }
}
