package client;

import game.entities.Player;
import game.framework.ID;
import game.window.Handler;
import packets.ClientAddPlayerPacket;
import packets.ClientPlayerUpdatePacket;
import packets.ClientRemovePlayerPacket;

import java.util.UUID;

public class EventListener {

    public Handler handler;
    public PlayerHandler playerHandler;

    public EventListener(Handler handler, PlayerHandler playerHandler){
        this.handler = handler;
        this.playerHandler = playerHandler;
    }

    public void received(Object p){
        // another player joined
        if(p instanceof ClientAddPlayerPacket){
            ClientAddPlayerPacket packet = (ClientAddPlayerPacket) p;
            playerHandler.addPlayer(new Player(0, 0, packet.uuid, ID.Player, packet.name, handler));
            System.out.println(packet.name + " has joined the game");
        }
        // another player left
        else if(p instanceof ClientRemovePlayerPacket){
            ClientRemovePlayerPacket packet = (ClientRemovePlayerPacket)p;
            System.out.println(packet.name + " has left the game");
            playerHandler.removePlayer(packet.uuid);
        }
        //received player updates
        else if(p instanceof ClientPlayerUpdatePacket){
            ClientPlayerUpdatePacket packet = (ClientPlayerUpdatePacket) p;
            System.out.println(packet.players.keySet().size());
            for(UUID id: packet.players.keySet()){
                if(!id.equals(playerHandler.myPlayer.getUUID())) {
                    playerHandler.updatePlayer(id, packet.players.get(id));
                }
            }
        }
    }

}
