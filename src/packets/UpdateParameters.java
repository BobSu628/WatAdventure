package packets;

import java.io.Serializable;

public class UpdateParameters implements Serializable {

    public float x, y;
    public int facing;
    public boolean leftMoving, rightMoving, jumping;

    public UpdateParameters(float x, float y, int facing, boolean leftMoving, boolean rightMoving, boolean jumping){
        this.x = x;
        this.y = y;
        this.facing = facing;
        this.leftMoving = leftMoving;
        this.rightMoving = rightMoving;
        this.jumping = jumping;
    }

}
