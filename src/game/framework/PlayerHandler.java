package game.framework;

import game.entities.NetPlayer;
import game.entities.Player;
import game.framework.Game;
import game.framework.ID;
import packets.UpdateParameters;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHandler implements Serializable {

    private transient Game game;
    public Player myPlayer;
    public transient HashMap<UUID, NetPlayer> players = new HashMap<>();

    public PlayerHandler(Game game){
        this.game = game;
        //myPlayer = new Player(0, 0, UUID.randomUUID(), ID.Player, "Bob", this, handler);

    }

    public void reinit(Game game){
        this.game = game;
        this.players = new HashMap<>();
    }

    public void tick() {
        myPlayer.tick();

    }

    //Update parameters of a Player according to received ClientPlayerUpdatePacket
    public void updatePlayer(UUID uuid, UpdateParameters parameters){
        NetPlayer player = players.get(uuid);
        player.setX(parameters.x);
        player.setY(parameters.y);
    }

    public void render(Graphics g) {
        myPlayer.render(g);
        for (NetPlayer player : players.values()) {
            player.render(g);
        }

    }

    //initialze other players when current player joins the game
    public void initOtherPlayers(HashMap<UUID, UpdateParameters> players, HashMap<UUID, String> names){
        for(Map.Entry<UUID, UpdateParameters> entry: players.entrySet()){

            UUID uuid = entry.getKey();
            UpdateParameters parameters = entry.getValue();
            NetPlayer player = new NetPlayer(parameters.x, parameters.y, uuid, ID.Player, names.get(uuid), game.getHandler());
            //updatePlayer(player, parameters);
            this.addPlayer(player);
        }
    }

    public void addPlayer(NetPlayer player) {
        players.put(player.getUUID(), player);
    }

    public void removePlayer(UUID uuid) {
        players.remove(uuid);
    }

    ///////////////////
    //set player movement flags

    public void setLeftWalk(boolean leftWalking) {
        myPlayer.setLeftWalking(leftWalking);
    }

    public void setRightWalk(boolean rightWalking) {
        myPlayer.setRightWalking(rightWalking);
    }

    public void setLeftRun(boolean leftRunning) {
        myPlayer.setLeftRunning(leftRunning);
    }

    public void setRightRun(boolean rightRunning) {
        myPlayer.setRightRunning(rightRunning);
    }
    ///////////////////
}
