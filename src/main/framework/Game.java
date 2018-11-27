package main.framework;
import main.MainCanvas;
import main.framework.KeyInput;
import main.framework.Texture;
import main.window.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.UUID;

/*
 */

public class Game implements Serializable {

    public static int WIDTH = 1290;
    public static int HEIGHT = 960;

    private transient MainCanvas mainCanvas;
    private Handler handler;
    private Camera camera;
    private transient KeyInput keyInput;
    private transient CommandLine commandLine;
    private transient PausedMenu pausedMenu;
    private transient static Texture texture;
    private int level = 1;

    public Game(MainCanvas mainCanvas){
        this.mainCanvas = mainCanvas;
        texture = new Texture();
        camera = new Camera(0, 0);
        handler = new Handler(this, camera);
        keyInput = new KeyInput(mainCanvas, this, this.handler);
        commandLine = new CommandLine(mainCanvas, this, this.handler);
        pausedMenu = new PausedMenu(mainCanvas, this);
    }

    public void reinit(MainCanvas mainCanvas){
        this.mainCanvas = mainCanvas;
        texture = new Texture();
        keyInput = new KeyInput(mainCanvas, this, this.handler);
        commandLine = new CommandLine(mainCanvas, this, this.handler);
        pausedMenu = new PausedMenu(mainCanvas, this);
        this.handler.reinit(this);

    }

    public void tick() {
        if (!pausedMenu.isActive()) {
            handler.tick();
            camera.tick(handler.player);
        }else{
            pausedMenu.tick();
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
        g2d.translate(-camera.getX(), -camera.getY()); //end of camera
        if (this.commandLine.isActive()) this.commandLine.render(g);
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

    /*
    public void setKeyInputEnabled(boolean enabled){
        if(enabled) this.addKeyListener(keyInput);
        else this.removeKeyListener(keyInput);
    }
    */

    public Handler getHandler() {
        return handler;
    }

    public int getLevel() {
        return level;
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

    public void incrementLevel(){
        this.level ++;
    }
}

