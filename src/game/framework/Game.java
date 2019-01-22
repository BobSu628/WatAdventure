package game.framework;
import client.PlayerHandler;
import game.MainCanvas;
import game.window.*;
import packets.UpdateParameters;

import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

/*
 */

public class Game implements Serializable {

    public static int WIDTH = 1290;
    public static int HEIGHT = 960;

    private transient MainCanvas mainCanvas;
    private Handler handler;
    private PlayerHandler playerHandler;
    private Camera camera;
    private transient KeyInput keyInput;
    private transient CommandLine commandLine;
    private transient PausedMenu pausedMenu;
    private transient static Texture texture;
    //private int level;
    private char type;
    public boolean paused;

    public Game(MainCanvas mainCanvas, char type){
        this.type = type;
        this.paused = false;
        this.mainCanvas = mainCanvas;
        texture = new Texture();
        camera = new Camera(0, 0);
        playerHandler = new PlayerHandler(this);
        handler = new Handler(this, camera, type);
        keyInput = new KeyInput(mainCanvas, this, this.playerHandler);
        pausedMenu = new PausedMenu(mainCanvas, this, type);
        if(type == 's'){
            //playerHandler.myPlayer = handler.player;
            commandLine = new CommandLine(mainCanvas, this, this.handler);
            //level = 1;
        }else if(type == 'm'){
            //level = 0;
        }
    }

    public void reinit(MainCanvas mainCanvas){
        this.mainCanvas = mainCanvas;
        texture = new Texture();
        keyInput = new KeyInput(mainCanvas, this, this.playerHandler);
        commandLine = new CommandLine(mainCanvas, this, this.handler);
        pausedMenu = new PausedMenu(mainCanvas, this, type);
        this.handler.reinit(this);
        this.playerHandler.reinit(this);

    }

    public void tick() {
        if(!paused) {
            if (type == 'm' || !pausedMenu.isActive()) {
                handler.tick();
                playerHandler.tick();
                camera.tick(playerHandler.myPlayer);
            }
            if (pausedMenu.isActive()) {
                pausedMenu.tick();
            }
        }
    }

    public void render(Graphics g){

        Graphics2D g2d = (Graphics2D) g;
        //////////////////////////////////

        g.setColor(new Color(45, 218, 252)); //light blue background
        g.fillRect(0, 0, MainCanvas.WIDTH, MainCanvas.HEIGHT);
        //g.drawImage(background, 0, 0, MainCanvas.WIDTH, MainCanvas.HEIGHT, null); // background image
        g2d.translate(camera.getX(), camera.getY()); //begin of camera
        handler.render(g);
        playerHandler.render(g);

        g2d.translate(-camera.getX(), -camera.getY()); //end of camera
        if (type == 's' && this.commandLine.isActive()) this.commandLine.render(g);
        if (this.pausedMenu.isActive()) this.pausedMenu.render(g);

        ////////////////////////////////

    }

    public void quit(){
        mainCanvas.getMainMenu().setDisplay(true);
        mainCanvas.getMainMenu().getLoadMenu().setActive(false);
        mainCanvas.getMainMenu().getLoadMenu().setDisplay(false);
        MainCanvas.state = MainCanvas.STATE.MainMenu;
        mainCanvas.addKeyListener(mainCanvas.getMainMenu());
        mainCanvas.removeKeyListener(this.keyInput);
    }

    // multiplayer mode, add other online players to local game
    public void initOtherPlayers(HashMap<UUID, UpdateParameters> players, HashMap<UUID, String> names){
        playerHandler.initOtherPlayers(players, names);
    }

    public static Texture getInstance(){
        return texture;
    }

    public CommandLine getCommandLine(){
        return this.commandLine;
    }

    public PausedMenu getPausedMenu(){return this.pausedMenu;}

    public KeyInput getKeyInput(){
        return this.keyInput;
    }

    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }

    public Handler getHandler() {
        return handler;
    }

    /*
    public int getLevel() {
        return level;
    }
    */

    public Camera getCamera() {
        return camera;
    }

    public void setKeyInput(KeyInput keyInput) {
        this.keyInput = keyInput;
    }

    public void setCommandLine(CommandLine commandLine) {
        this.commandLine = commandLine;
    }

    public void setPausedMenu(PausedMenu pausedMenu) {
        this.pausedMenu = pausedMenu;
    }

    /*
    public void incrementLevel(){
        this.level ++;
    }
    */
}

