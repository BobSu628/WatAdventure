package client;

import game.MainCanvas;
import game.entities.NetPlayer;
import game.entities.Player;
import game.framework.Game;
import game.framework.ID;
import game.framework.PlayerHandler;
import packets.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.util.UUID;

public class Client{

    public static final int DEFAULT = 9001;

    private MainCanvas mainCanvas;
    private String host;
    private int port;
    private UUID uuid;
    private String playerName;
    private Player player;

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private SyncListener listener;
    private Game game;

    public Client(MainCanvas mainCanvas, Game game, int port){
        this.mainCanvas = mainCanvas;
        this.port = port;
        this.game = game;

    }

    //connect to the server
    public void connect(){
        try{
            //get host
            host = getServerAddress();
            socket = new Socket(host, port);

            //io setup
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            listener = new SyncListener(game.getHandler(), game.getPlayerHandler());

            //set player name
            while(true) {
                try {
                    Object receivedPacket = in.readObject();
                    if(receivedPacket instanceof ClientSubmitNamePacket) {
                        submitPlayer();
                    }else if(receivedPacket instanceof ClientPlayerAcceptedPacket){
                        ClientPlayerAcceptedPacket packet = (ClientPlayerAcceptedPacket) receivedPacket;
                        game.initOtherPlayers(packet.players, packet.playerNames);
                        //this.time = packet.time;
                        break;
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }catch (ClassNotFoundException e){ }

            }
            //start running
            //new Thread(this).start();
        }catch (ConnectException e){
            System.out.println("Unable to connect to the server");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void submitPlayer(){
        Player tPlayer = game.getPlayerHandler().myPlayer;
        playerName = getName();
        uuid = UUID.randomUUID();
        player = new Player(tPlayer.getX(), tPlayer.getY(), uuid, ID.Player, playerName, game.getHandler());
        game.getPlayerHandler().myPlayer = player;

        sendObject(new ServerAddPlayerPacket(uuid, playerName, extractParameters(player)));
    }

    //close the connection
    public void close(){
        try{
            game.quit();

            //tell the server that we disconnected
            ServerRemovePlayerPacket packet = new ServerRemovePlayerPacket();
            sendObject(packet);

            in.close();
            out.close();
            socket.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //send data to the server
    public void sendObject(Object packet){
        try{
            out.writeObject(packet);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void communicate(){
        //receive
        try{
            Object data = in.readObject();
            listener.received(data);
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }catch (SocketException e){
            close();
        }catch (IOException e){
            e.printStackTrace();
        }

        //send
        sendObject(new ServerPlayerUpdatePacket(extractParameters(player)));
    }

    private String getServerAddress() {
        return mainCanvas.getServerAddress();
    }

    private String getName() {
        return mainCanvas.getName();
    }

    //Extract the to-be-uploaded parameters from a Player
    public static UpdateParameters extractParameters(Player player){
        return new UpdateParameters(player.getX(),
                                    player.getY(),
                                    player.getFacing(),
                                    player.isLeftMoving(),
                                    player.isRightMoving(),
                                    player.isJumping());
    }

    //Update parameters of a NetPlayer according to received ClientPlayerUpdatePacket
    public static void updatePlayer(PlayerHandler playerHandler, UUID uuid, UpdateParameters parameters){
        NetPlayer player = playerHandler.players.get(uuid);
        player.setX(parameters.x);
        player.setY(parameters.y);
        player.setFacing(parameters.facing);
        player.setLeftMoving(parameters.leftMoving);
        player.setRightMoving(parameters.rightMoving);
        player.setJumping(parameters.jumping);
    }


}
