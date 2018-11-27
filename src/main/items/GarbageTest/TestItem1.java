package main.items.GarbageTest;

import main.framework.ID;
import main.items.Item;
import main.window.Handler;

import java.awt.*;

public class TestItem1 extends Item {


/*   private ButtonFunction itemFunction = () -> {
        System.out.println(1111);
        use();
    };

    protected ItemButton itemButton = new ItemButton(0,0,0,0,null,Color.white,font,itemFunction,0);
*/
    public TestItem1(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);
        this.width = 32;
        this.height = 32;
        itemButton.setLabel("TestItem1");
        super.itemButton = this.itemButton;

    }

    @Override
    public void tick() {
        super.tick();
        //itemButton.setNum(quantity);
    }

    @Override
    public void use() {
        try{
            this.owner.setHp(this.owner.getMaxHp());
            decreaseAfterUse(1);
        }catch (NullPointerException e){

        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.pink);
        g.fillRect((int)x, (int)y, width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, width, height);
    }

    @Override
    public int hashCode() {
        return 11;
    }

    @Override
    public String toString() {
        return "Elixir";
    }

}
