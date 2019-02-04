/*TODO atk distance
 */

package game.items.Weapon;

import game.framework.ID;
import game.framework.Handler;

import java.awt.*;

public class MechanicalPencil extends Weapon{

    public MechanicalPencil(float x, float y, ID id, Handler handler, int durability, int maxDurabily, int price, int atk){
        super(x,y,id,handler,durability,maxDurabily,price,atk);
        this.width = 32;
        this.height = 32;
        weaponButton.setLabel("Mechanical Pencil");
        this.durability = durability;
        this.maxDurability = maxDurabily;
    }


    @Override
    public void tick() {
        super.tick();
        //itemButton.setNum(quantity);
    }


    @Override
    public void render(Graphics g) {
        g.setColor(Color.green);
        g.fillRect((int)x, (int)y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    public void skill (){

    }
}