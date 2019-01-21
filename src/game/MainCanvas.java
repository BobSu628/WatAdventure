package game;

import client.Client;
import game.framework.Game;
import game.framework.SaveSlot;
import game.window.BufferedImageLoader;
import game.window.MainMenu;
import game.window.Window;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.*;

public class MainCanvas extends Canvas implements Runnable{

    public static int WIDTH = 1290;
    public static int HEIGHT = 960;

    private boolean running;
    private Thread thread;
    private int frameCount, updateCount;

    private Game game;

    public enum STATE{
        MainMenu(),
        Game(),
        GameOver()
    }

    public static STATE state;
    private int mainX = WIDTH / 10*6;
    private int mainY = HEIGHT / 15;
    private int mainWidth = WIDTH / 9;
    private int mainHeight = HEIGHT / 10 * 3;
    private MainMenu mainMenu;
    private BufferedImage background;
    private Font font = new Font("Gotham", Font.BOLD, 40);
    public String timeStamps[];

    public MainCanvas() {
        state = STATE.MainMenu;
        timeStamps = new String[4];
        initTimeStamps();
        mainMenu = new MainMenu(this,null,mainX,mainY,mainWidth,mainHeight);
        this.addKeyListener(mainMenu);

        BufferedImageLoader loader = new BufferedImageLoader();
        background = loader.loadImage("/background.png");

    }

    public void initTimeStamps(){

        File directory = new File("saves");
        File[] saveFiles = directory.listFiles();
        for(int i = 1; i <= 3; i ++){
            loadSaveFile(i, saveFiles);
        }

    }

    private void loadSaveFile(int index, File[] saveFiles){
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
        }catch (IOException e){
            timeStamps[index] = "(Empty)";
        }catch (ClassNotFoundException e){
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
        }else if(state == STATE.GameOver){

        }
    }

    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = bs.getDrawGraphics();

        /////////////////////

        if(state == STATE.MainMenu){
            g.drawImage(background, 0, 0, WIDTH, HEIGHT, null);
            g.setColor(Color.YELLOW);
            g.setFont(font);
            g.drawString("WatAdventure", WIDTH/10, HEIGHT/10);
            this.mainMenu.render(g);
        }else if(state == STATE.Game){
            this.game.render(g);
        }
        g.setColor(Color.white);
        g.drawString("FPS: " + frameCount, 20, 20);
        g.drawString("TICKS: " + updateCount, 20, 40);
        /////////////////////

        g.dispose();
        bs.show();

    }

    public void startNewGame(){
        Game game = new Game(this);
        this.game = game;
        this.addKeyListener(game.getKeyInput());
        this.removeKeyListener(mainMenu);
        state = STATE.Game;
    }

    public void startMultiplayer(){
        new Client(this, Client.DEFAULT).connect();
    }

    public void saveGame(String timeStamp, int id) {
        //UUID uuid = UUID.randomUUID();
        try{
            FileOutputStream fileOut = new FileOutputStream("saves/"+id+".save");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            SaveSlot ss = new SaveSlot(timeStamp, this.game);
            timeStamps[id] = timeStamp;
            //System.out.println(timeStamps[id]);
            out.writeObject(ss);
            out.close();
            fileOut.close();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void loadGame(int id){
        try{
            this.removeKeyListener(mainMenu.getLoadMenu());
            FileInputStream fileIn = new FileInputStream("saves/"+id+".save");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            SaveSlot slot = (SaveSlot) in.readObject();
            Game loadedGame = slot.game;
            loadedGame.reinit(this);
            this.addKeyListener(loadedGame.getKeyInput());
            this.game = loadedGame;
            in.close();
            fileIn.close();
            state = STATE.Game;
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            System.err.println("No such save found");
            e.printStackTrace();

        }
    }

    public Game getGame() {
        return game;
    }

    public MainMenu getMainMenu() {
        return mainMenu;
    }

    public static void main(String args[]){
        new Window(WIDTH, HEIGHT, "WatAdventure", new MainCanvas());
    }

}
