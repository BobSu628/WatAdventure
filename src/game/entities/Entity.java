/*  TODO hashMap -> set
* */

package game.entities;

import game.colliders.Collider;
import game.framework.GameObject;
import game.framework.ID;
import game.items.Item;
import game.items.Weapon.Weapon;
import game.framework.Handler;

import java.awt.*;
import java.util.*;

/*
 */

public abstract class Entity extends GameObject {

    float velX, velY;
    boolean falling = true, jumping = false;
    boolean meleeAttacking = false, rangeAttacking = false;
    Collider collider;
    Item equippedItem;
    Weapon equippedWeapon;
    Map<Item, Integer> itemMap = new HashMap<>();
    LinkedList<Weapon> weaponLinkedList = new LinkedList<>();
    int hp, meleeAtk, rangeAtk, maxHp;
    int normalVel, jumpVel;
    float gravity = 0.5f;
    int facing;
    boolean inKnockBack = false;
    int knockbackVelX;
    int knockbackVelY;
    transient Point sightPoint;

    float MAX_SPEED = 20;

    Entity(float x, float y, ID id, Handler handler){
        super(x, y, id, handler);
        this.facing = 1;
        sightPoint = new Point((int)x+width/2, (int)y+height/8);
    }

    public void reinit(){
        sightPoint = new Point((int)x+width/2, (int)y+height/8);
        collider.reinit(this);
        Iterator<Map.Entry<Item, Integer>> it = itemMap.entrySet().iterator();
        while (it.hasNext()){
            Item tempItem = it.next().getKey();
            tempItem.reinit();
        }
    }

    public void tick(){
        if(this.hp <= 0) this.destroy();

        x += velX;
        y += velY;
        sightPoint.setLocation(x+width/2, y+height/8);

        if (falling || jumping) {
            velY += gravity;

            if (velY > MAX_SPEED) {
                velY = MAX_SPEED;
            }
        }

        collider.collision(handler);
    }

    public abstract void render(Graphics g);

    public void destroy(){
        super.destroy();
    }

    void addCollider(Collider c){
        this.collider = c;
    }

    public void jump(){
        if(!this.inKnockBack && !jumping) {
            this.velY = jumpVel;
            this.jumping = true;
        }
    }

    public void addItem(Item item){
        if(itemMap.containsKey(item)){
            //System.out.println(1);
            //itemMap.entrySet
            itemMap.put(item, itemMap.get(item)+1);
        }else{
            itemMap.put(item, 1);
        }
        this.equippedItem = item;
        handler.removeObject(item);
    }

    public void useItem(){
        if(this.equippedItem != null && itemMap.get(equippedItem) > 0) {

            this.equippedItem.use();
            itemMap.put(equippedItem, itemMap.get(equippedItem) - 1);

            if(itemMap.get(equippedItem) <= 0){
                this.equippedItem = null;
            }

        }
    }

    public void addWeapon (Weapon weapon){
        weaponLinkedList.add(weapon);
        handler.removeObject(weapon);
        meleeAtk += weapon.getAtk();
        equipWeapon(weapon);
    }

    public void destoryWeapon (Weapon weapon){
        if (this.equippedWeapon == weapon){
            weaponLinkedList.remove(weapon);
            meleeAtk -= weapon.getAtk();
            equippedWeapon = null;
        }
    }

    public void unequipWeapon (){
        equippedWeapon = null;
        meleeAtk -= equippedWeapon.getAtk();
    }

    public void equipWeapon (Weapon weapon){
        this.equippedWeapon = weapon;
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)(x+width/4), (int)(y+height/2), width-width/2, height/2);
    }

    public Rectangle getBoundsRight() {
        return new Rectangle((int)(x+width-width/8), (int)(y+height/4), width/8, height-height/2);
    }

    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x, (int)(y+height/4), width/8, height-height/2);
    }

    public Rectangle getBoundsTop() {
        return new Rectangle((int)(x+width/4), (int)y, width-width/2, height/2);
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public int getFacing() {
        return facing;
    }

    public boolean isInKnockBack() {
        return inKnockBack;
    }

    public void setInKnockBack(boolean inKnockBack) {
        this.inKnockBack = inKnockBack;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public int getMeleeAtk() {
        return meleeAtk;
    }

    public int getRangeAtk() {
        return rangeAtk;
    }

    public int getKnockbackVelX() {
        return knockbackVelX;
    }

    public int getKnockbackVelY() {
        return knockbackVelY;
    }

    public int getNormalVel() {
        return normalVel;
    }

    public void damage(int dmg){
        if(!inKnockBack) this.hp -= dmg;
    }

    public Map<Item, Integer> getItemMap() {
        return itemMap;
    }

    public Item getEquippedItem() {
        return equippedItem;
    }

    public void setEquippedItem(Item equippedItem) {
        this.equippedItem = equippedItem;
    }
    public Point getSightPoint() {
        return sightPoint;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public void setEquippedWeapon(Weapon equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public LinkedList<Weapon> getWeaponLinkedList() {
        return weaponLinkedList;
    }

    public void setRangeAttacking(boolean rangeAttacking) {
        this.rangeAttacking = rangeAttacking;
    }
}
