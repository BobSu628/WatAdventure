package game;

import client.Client;
import game.framework.Game;
import game.framework.SaveSlot;
import game.window.BufferedImageLoader;
import game.window.MainMenu;
import game.window.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;

public class MainCanvas extends Canvas implements Runnable{

    public static int WIDTH = 1290;
    public static int HEIGHT = 960;

    private Window window;
    private boolean running;
    private Thread thread;
    private int frameCount, updateCount;

    private Client client;
    private Game game;

    public enum STATE{ // game state
        MainMenu(),
        Game(),
        Multiplayer(),
        GameOver()
    }

    public static STATE state;
    private int mainX = WIDTH / 10*6;
    private int mainY = HEIGHT / 15;
    private int mainWidth = WIDTH / 9;
    private int mainHeight = HEIGHT / 10 * 4;
    private MainMenu mainMenu;
    private BufferedImage background;
    private Font font = new Font("Gotham", Font.BOLD, 40);
    public String timeStamps[];

    public MainCanvas() {

        state = STATE.MainMenu;
        timeStamps = new String[4];
        initTimeStamps();
        mainMenu = new MainMenu(this,null,mainX,mainY,mainWidth,mainHeight);
        //TODO use one KeyListener throughout the program
        this.addKeyListener(mainMenu);

        BufferedImageLoader loader = new BufferedImageLoader();
        background = loader.loadImage("/background.png");

        window = new Window(WIDTH, HEIGHT, "WatAdventure", this);
    }

    public void initTimeStamps(){

        File directory = new File("saves");
        File[] saveFiles = directory.listFiles();
        for(int i = 1; i <= 3; i ++){
            loadSaveFile(i, saveFiles);
        }

    }

    private void loadSaveFile(int index, File[] saveFiles){
        /*
        if save file exists, store its timestamp into its index
        else, store "(Empty)" into its index
         */
        try{
            FileInputStream fileIn = new FileInputStream("saves/"+saveFiles[index-1].getName());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            SaveSlot s = (SaveSlot) in.readObject();
            if(s == null || s.timeStamp == null){
                timeStamps[index] = "(Empty)";
            }
            else{
                timeStamps[index] = s.timeStamp;
            }
            in.close();
            fileIn.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (ClassNotFoundException|NullPointerException|IOException e){
            timeStamps[index] = "(Empty)";
        }
    }

    public synchronized void start(){
        if(running) return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run(){
        this.requestFocus();
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates ++;
                delta --;
            }
            render();
            frames ++;
            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                this.frameCount = frames;
                this.updateCount = updates;
                //System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }

    private void tick(){
        if (state == STATE.MainMenu) {
            //mainMenuActive = true;
            mainMenu.tick();
        }else if(state == STATE.Game){
            this.game.tick();
        }else if(state == STATE.Multiplayer){
            this.client.communicate();
            this.game.tick();
        }else if(state == STATE.GameOver){

        }
    }

    private void render(){
        //create a 3 buffer strategy
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        //get graphics object
        Graphics g = bs.getDrawGraphics();

        /////////////////////
        //Draw Graphics

        if(state == STATE.MainMenu){
            //render main menu
            g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
            g.setColor(Color.YELLOW);
            g.setFont(font);
            g.drawString("WatAdventure", WIDTH/10, HEIGHT/10);
            this.mainMenu.render(g);
        }else if(state == STATE.Game){
            this.game.render(g);
        }else if(state == STATE.Multiplayer){
            this.game.render(g);
        }
        //Display FPS and ticks
        g.setColor(Color.white);
        g.drawString("FPS: " + frameCount, 20, 20);
        g.drawString("TICKS: " + updateCount, 20, 40);

        /////////////////////

        g.dispose();
        bs.show();

    }

    public void startNewGame(){
        this.game = new Game(this, 's');
        this.addKeyListener(game.getKeyInput()); //switch key input
        this.removeKeyListener(mainMenu);
        state = STATE.Game;
    }

    public void startMultiplayer(){
        this.game = new Game(this, 'm');
        client = new Client(this, this.game, Client.DEFAULT);
        this.addKeyListener(game.getKeyInput());
        this.removeKeyListener(mainMenu);
        client.connect();
        state = STATE.Multiplayer;
    }

    public void saveGame(String timeStamp, int id) {
        //TODO use a stabler method to write object (e.g. JSON)

        try{
            FileOutputStream fileOut = new FileOutputStream("saves/"+id+".save");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            SaveSlot ss = new SaveSlot(timeStamp, this.game); //serializable container for Game
            timeStamps[id] = timeStamp; //store timestamp in its corresponding index
            out.writeObject(ss);
            out.close();
            fileOut.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void loadGame(int id){
        try{
            this.removeKeyListener(mainMenu.getLoadMenu()); //switch key listener
            FileInputStream fileIn = new FileInputStream("saves/"+id+".save"); //
            ObjectInputStream in = new ObjectInputStream(fileIn);
            SaveSlot slot = (SaveSlot) in.readObject(); //read save file into object container
            Game loadedGame = slot.game;
            loadedGame.reinit(this); //reinitialize non-serializable instances
            this.addKeyListener(loadedGame.getKeyInput()); //add the keylistener of current game session
            this.game = loadedGame;
            in.close();
            fileIn.close();
            state = STATE.Game;
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            System.err.println("Save file does not exist");
            e.printStackTrace();
        }
    }

    public Game getGame() {
        return game;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    //Opens an InputDialog that asks user to enter IP address of server
    public String getServerAddress(){
        return JOptionPane.showInputDialog(
                window.getFrame(),
                "Enter IP Address of the Server:",
                "Welcome to the game",
                JOptionPane.QUESTION_MESSAGE);
    }

    //Opens an InputDialog that asks user to enter screen name
    public String getName() {
        return JOptionPane.showInputDialog(
                window.getFrame(),
                "Choose a screen name:",
                "Screen name selection",
                JOptionPane.PLAIN_MESSAGE);
    }

    public Client getClient() {
        return client;
    }

    public static void main(String args[]){
        new MainCanvas();
        //window = new Window(WIDTH, HEIGHT, "WatAdventure", new MainCanvas());
    }

}
