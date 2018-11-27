package main.items;

import main.entities.Entity;
import main.framework.GameObject;
import main.framework.ID;
import main.util.ButtonFunction;
import main.util.ItemButton;
import main.util.LabelBox;
import main.window.Handler;
import main.window.ItemMenu;
import main.window.PausedMenu;

import java.awt.*;

public abstract class Item extends GameObject {


    protected Entity owner = null;

    protected transient Font font = new Font("华康少女文字W5",Font.PLAIN,15);

    private transient ButtonFunction itemFunction = () -> {
        use();
    };

    protected transient ItemButton itemButton = new ItemButton(0,0,0,0,null,Color.white,font,itemFunction,0,this);


    /*protected ButtonFunction itemFunction =()->{

    };

    protected ItemButton itemButton = new ItemButton(0,0,0,0,null,Color.white,font,itemFunction,0);*/

    public Item(float x, float y, ID id, Handler handler) {
        super(x, y, id, handler);

    }

    public void reinit(){
        super.reinit();
        font = new Font("华康少女文字W5",Font.PLAIN,15);
        itemFunction = () -> use();
        itemButton = new ItemButton(0,0,0,0,null,Color.white,font,itemFunction,0,this);

    }

    public abstract void use();

    public void assignTo(Entity owner){
        this.owner = owner;
    }

    public void decreaseAfterUse (int decrement){
        owner.getItemMap().put(this, owner.getItemMap().get(this)-decrement);
        /*if (owner.getItemMap().get(this) <= 0){
            owner.getItemMap().remove(this);
        }*/
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode() == obj.hashCode();
    }


    public ItemButton getItemButton() {
        return itemButton;
    }


    /*public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increseQuantity (int increasement){
        this.quantity += increasement;
    }*/

    /*public ButtonFunction getItemFunction() {
        return itemFunction;
    }

    public void setItemFunction(ButtonFunction itemFunction) {
        this.itemFunction = itemFunction;
    }*/
}
