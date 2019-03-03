package server;

import packets.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

public class Connection implements Runnable {

    private UUID uuid = null;
    private String playerName;
    private UpdateParameters parameters;
    private PlayerData playerData;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    //private UUID id;

    public Connection(Socket socket){
        this.socket = socket;

        try{
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try{
            do{
                sendObject(out, new ClientSubmitNamePacket());
                try {
                    Object packet = in.readObject();
                    if(packet instanceof ServerAddPlayerPacket){
                        uuid = ((ServerAddPlayerPacket) packet).uuid;
                        parameters = ((ServerAddPlayerPacket) packet).parameters;
                        playerName = ((ServerAddPlayerPacket) packet).name;
                    }

                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }

                if(uuid != null && !Server.players.containsKey(uuid)) {
                    Server.playerNames.put(uuid, playerName);
                    Server.players.put(uuid, parameters);
                    Server.outputStreams.put(uuid, this.out);
                }

            }while(uuid == null || !Server.players.containsKey(uuid));

            this.playerData = new PlayerData(this.out, 0, 0, this.uuid, this.playerName);
            Sender.addPlayer(this.playerData);

            while(socket.isConnected()){

                //receive
                try{
                    Object data = in.readObject();
                    if(data instanceof ServerRemovePlayerPacket){
                        break;
                    }else if(data instanceof ServerPlayerUpdatePacket){

                        ServerPlayerUpdatePacket playerPacket = ((ServerPlayerUpdatePacket) data);
                        parameters = playerPacket.parameters;
                        Server.update(this.playerData, parameters);
                    }

                }catch (ClassNotFoundException e){
                    e.printStackTrace();
                }

            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            close();
        }
    }

    private void close(){

        if(uuid != null) {
            Server.players.remove(uuid);
            Server.playerNames.remove(uuid);
            Server.outputStreams.remove(uuid);
        }

        Sender.removePlayer(this.playerData);

        try{
            in.close();
            out.close();
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void sendObject(ObjectOutputStream out, Object packet){
        try{
            out.writeObject(packet);
            out.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
