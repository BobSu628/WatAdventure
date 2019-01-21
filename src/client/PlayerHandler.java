package client;

import game.entities.Player;
import game.framework.ID;
import packets.UpdateParameters;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHandler {

    public Player myPlayer;
    public HashMap<UUID, Player> players = new HashMap<>();

    public PlayerHandler(){

    }

    public void tick() {
        myPlayer.tick();
        //System.out.println(myPlayer.getX()+", "+myPlayer.getY());
        /*for (Map.Entry<UUID, NetPlayer> entry : players.entrySet()) {
            entry.getValue().tick();
        }*/

    }

    public void updatePlayer(UUID uuid, UpdateParameters parameters){
        Player player = players.get(uuid);
        player.setX(parameters.x);
        player.setY(parameters.y);
    }

    public void render(Graphics g) {
        myPlayer.render(g);
        for (Map.Entry<UUID, Player> entry : players.entrySet()) {
            entry.getValue().render(g);
        }

    }

    public void initOtherPlayers(HashMap<UUID, UpdateParameters> players, HashMap<UUID, String> names){
        for(Map.Entry<UUID, UpdateParameters> entry: players.entrySet()){

            UUID uuid = entry.getKey();
            UpdateParameters parameters = entry.getValue();
            Player player = new Player(parameters.x, parameters.y, uuid, ID.Player, names.get(uuid));
            //updatePlayer(player, parameters);
            this.addPlayer(player);
        }
    }

    public void addPlayer(Player player) {
        players.put(player.getUUID(), player);
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

}
