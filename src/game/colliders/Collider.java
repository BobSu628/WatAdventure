package game.colliders;

import game.entities.Entity;
import game.framework.GameObject;
import game.window.Handler;

import java.awt.*;
import java.io.Serializable;

public abstract class Collider implements Serializable {

    protected transient GameObject object;

    public Collider (GameObject object){
        this.object = object;
    }

    public void reinit(GameObject object){
        this.object = object;
    }

    public abstract void collision(Handler handler);

    /*
    Utility method: check if one bound intersects a target entity at all
     */
    public static boolean intersectsEntity(Rectangle bound, Entity target){
        return bound.intersects(target.getBoundsRight())
                || bound.intersects(target.getBoundsLeft())
                || bound.intersects(target.getBoundsTop())
                || bound.intersects(target.getBounds());
    }

    public static boolean intersectsEntity(Entity subject, Entity object){
        return intersectsEntity(subject.getBounds(), object) ||
               intersectsEntity(subject.getBoundsLeft(), object) ||
               intersectsEntity(subject.getBoundsRight(), object) ||
               intersectsEntity(subject.getBoundsTop(), object);
    }

    /*
    Utility method: check if one bound intersects an object at all
     */
    public static boolean intersectsGameObject(Rectangle bound, GameObject object){
        if(object instanceof Entity) return intersectsEntity(bound, (Entity)object);

        return bound.intersects(object.getBounds());
    }

    /*
    Utility method: knockback an entity
     */
    public static void hit(Entity subject, Entity object, int facing){
        object.damage(subject.getMeleeAtk());
        object.setInKnockBack(true);
        object.setVelX(facing*object.getKnockbackVelX());
        object.setVelY(-object.getKnockbackVelY());
        if (subject.getEquippedWeapon() != null){
            subject.getEquippedWeapon().decreaseDurability(1);
            if (subject.getEquippedWeapon().getDurability() <= 0){
                subject.destoryWeapon(subject.getEquippedWeapon());
            }
        }
    }
}
