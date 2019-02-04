package game.items.Weapon;

import game.entities.Entity;
import game.framework.GameObject;
import game.framework.ID;
import game.util.ButtonFunction;
import game.util.WeaponButton;
import game.framework.Handler;

import java.awt.*;

public abstract class Weapon extends GameObject {

    protected Entity owner = null;

    protected transient Font font = new Font("华康少女文字W5",Font.PLAIN,15);
    protected int durability;
    protected int maxDurability;
    protected int price;
    protected int atk;

    protected transient ButtonFunction weaponFunction = () -> { //just for equipping in menu
        owner.equipWeapon(this);
    };
    protected transient WeaponButton weaponButton = new WeaponButton(0,0,0,0,null,Color.white,font,weaponFunction,this);

    public Weapon(float x, float y, ID id, Handler handler, int durability, int maxDurabily, int price, int atk){
        super(x,y,id,handler);
        this.durability = durability;
        this.maxDurability = maxDurabily;
        this.atk = atk;
        this.price = price;
    }

    public void reinit(){
        super.reinit();
        font = new Font("华康少女文字W5",Font.PLAIN,15);
        this.weaponFunction = () -> owner.equipWeapon(this);
        this.weaponButton = new WeaponButton(0,0,0,0,null,Color.white,font,weaponFunction,this);
    }

    public abstract void skill();

    public void assignTo (Entity owner){
        this.owner = owner;
    }

    public void decreaseDurability (int decrement){
        this.durability -= decrement;
    }

    public void repair (){
        this.durability = maxDurability;
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


    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public int getMaxDurability() {
        return maxDurability;
    }

    public void setMaxDurability(int maxDurability) {
        this.maxDurability = maxDurability;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }
}
