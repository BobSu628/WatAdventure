package server;

import packets.UpdateParameters;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.UUID;

public class PlayerData {
    private ObjectOutputStream out;
    private float x, y;
    private int facing;
    private boolean isLeftMoving, isRightMoving, jumping;

    private UUID uuid;
    private String name;

    public PlayerData(ObjectOutputStream out, float x, float y, UUID uuid, String name){
        this.out = out;
        this.x = x; this.y = y;
        this.uuid = uuid;
        this.name = name;
    }

    public void send(Object packet) throws IOException {
        this.out.writeObject(packet);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFacing() {
        return facing;
    }

    public void setFacing(int facing) {
        this.facing = facing;
    }

    public boolean isLeftMoving() {
        return isLeftMoving;
    }

    public void setLeftMoving(boolean leftMoving) {
        isLeftMoving = leftMoving;
    }

    public boolean isRightMoving() {
        return isRightMoving;
    }

    public void setRightMoving(boolean rightMoving) {
        isRightMoving = rightMoving;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }
}
