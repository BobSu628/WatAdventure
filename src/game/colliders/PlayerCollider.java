package game.colliders;

import game.entities.Coin;
import game.entities.Entity;
import game.entities.TA;
import game.entities.Player;
import game.framework.GameObject;
import game.framework.ID;
import game.items.Elixir;
import game.items.Grenade;
import game.items.Item;
import game.items.Weapon.Weapon;
import game.objects.Block;
import game.objects.DestroyableBlock;
import game.objects.Ladder;
import game.window.Handler;

public class PlayerCollider extends EntityCollider {

    private transient Player player;

    public PlayerCollider(Entity entity){
        super(entity);
        player = (Player) entity;
    }

    @Override
    public void reinit(GameObject object) {
        super.reinit(object);
        player = (Player) entity;
    }

    public void collision(Handler handler){
        for(int i = 0; i < handler.object.size(); i ++) {
            GameObject tempObject = handler.object.get(i);

            if (tempObject.getId() == ID.Block) {
                Block block = (Block) tempObject;
                this.collideBlock(block);

            } else if (tempObject.getId() == ID.Flag) {
                //switch levels
                if (entity.getBounds().intersects(tempObject.getBounds()) || entity.getBoundsLeft().intersects(tempObject.getBounds()) || entity.getBoundsRight().intersects(tempObject.getBounds())) {
                    entity.getHandler().switchLevel();
                }
            } else if(tempObject.getId() == ID.Mathiant){
                this.collideEnemy((TA)tempObject);
            }else if(tempObject.getId() == ID.Elixir){
                this.collideItem((Elixir) tempObject);
            }else if(tempObject.getId() == ID.Grenade){
                this.collideItem((Grenade) tempObject);
            }else if(tempObject.getId() == ID.Coin){
                this.collideCoin((Coin) tempObject);
            } else if(tempObject.getId() == ID.MechanicalPencil){
                this.collideWeapon((Weapon) tempObject);
            }else if(tempObject.getId() == ID.DestroyableBlock){
                this.collideDestroyableBlock((DestroyableBlock) tempObject);
            }else if(tempObject.getId() == ID.Ladder){
                this.collideLadder((Ladder) tempObject);
            }
        }

    }

    private void collideEnemy(Entity entity){
        if(player.isAttacking()) {
            if (intersectsEntity(player.getMeleeAttack().getBounds(), entity)) {
                hit(player, entity, player.getFacing());
            }
        }
    }

    private void collideItem(Item item){
        if(intersectsEntity(item.getBounds(), player)) {
            player.addItem(item);
            item.assignTo(player);
        }
    }

    private void collideCoin(Coin coin){
        if(intersectsEntity(coin, player)){
            player.addCoin(coin.getValue());
            coin.destroy();
        }
    }

    private void collideWeapon (Weapon weapon){
        if(intersectsEntity(weapon.getBounds(),player)){
            player.addWeapon(weapon);
            weapon.assignTo(player);
        }
    }

    private void collideDestroyableBlock(DestroyableBlock destroyableBlock){
        if(player.getBounds().intersects(destroyableBlock.getBounds())){
            if(!destroyableBlock.isActive()){
                destroyableBlock.setActive(true);
            }
        }
        collideBlock(destroyableBlock);
    }

    private void collideLadder(Ladder ladder){
        if(player.getBounds().intersects(ladder.getBounds())){
            player.setOnLadder(true);
        }
    }
}
