package main.items;

import main.framework.ID;
import main.window.Handler;

import java.awt.*;

public class Grenade extends Item {


 /*   private ButtonFunction itemFunction = () -> {
        //System.out.println(1111);
        use();
    };

    protected ItemButton itemButton = new ItemButton(0,0,0,0,null,Color.white,font,itemFunction,0);
*/

    public Grenade(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
        this.width = 32;
        this.height = 32;
        itemButton.setLabel("Grenade");
        itemButton.getDescriptionLabelBox().setLabel("This is a description for Grenade");

    }

    public void reinit(){
        super.reinit();
        itemButton.setLabel("Grenade");
        itemButton.getDescriptionLabelBox().setLabel("This is a description for Grenade");
    }

    @Override
    public void tick() {
        super.tick();
        //itemButton.setNum(quantity);
    }

    @Override
    public void use() {
        decreaseAfterUse(1);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.magenta);
        g.fillRect((int)x, (int)y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "Grenade";
    }

}
