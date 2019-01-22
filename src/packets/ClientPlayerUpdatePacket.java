package packets;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class ClientPlayerUpdatePacket implements Serializable {

    public HashMap<UUID, UpdateParameters> players;

    public ClientPlayerUpdatePacket(){
        players = new HashMap<>();
    }

    public void addPlayer(UUID uuid, UpdateParameters parameters){
        this.players.put(uuid, parameters);
    }

}
