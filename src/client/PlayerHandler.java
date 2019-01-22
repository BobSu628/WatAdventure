package client;

import game.entities.Player;
import game.framework.Game;
import game.framework.ID;
import game.window.Camera;
import game.window.Handler;
import packets.UpdateParameters;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerHandler implements Serializable {

    private transient Game game;
    public Player myPlayer;
    public transient HashMap<UUID, Player> players = new HashMap<>();

    private transient boolean leftWalk, rightWalk, leftRun, rightRun;

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
            Player player = new Player(parameters.x, parameters.y, uuid, ID.Player, names.get(uuid), this, game.getHandler());
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

    public boolean isLeftWalk() {
        return leftWalk;
    }

    public void setLeftWalk(boolean leftWalk) {
        this.leftWalk = leftWalk;
    }

    public boolean isRightWalk() {
        return rightWalk;
    }

    public void setRightWalk(boolean rightWalk) {
        this.rightWalk = rightWalk;
    }

    public boolean isLeftRun() {
        return leftRun;
    }

    public void setLeftRun(boolean leftRun) {
        this.leftRun = leftRun;
    }

    public boolean isRightRun() {
        return rightRun;
    }

    public void setRightRun(boolean rightRun) {
        this.rightRun = rightRun;
    }

}
