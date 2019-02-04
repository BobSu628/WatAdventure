package game.window;
//TODO move player to PlayerHandler

import game.entities.Player;
import game.framework.Game;
import game.framework.GameObject;
import game.framework.ID;
import game.framework.LevelLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;

/*
 */

public class Handler implements Serializable {

    private transient Game game;
    //public Player player;
    public LinkedList<GameObject> object = new LinkedList<GameObject>(); //list of game objects
    private GameObject tempObject;
    private transient LevelLoader levelLoader;
    private transient Camera camera;
    private transient BufferedImage[] maps = new BufferedImage[3];
    //private transient BufferedImage cloud;
    //private int cloudY[] = new int[15];
    private int type;
    private int level; //0 for multiplayer map, >0 for singleplayer maps

    public Handler(Game game, Camera camera, char type){
        this.type = type;
        this.game = game;
        this.camera = camera;
        //Random random = new Random();

        initImages();

        /*
        for(int i = 0; i < 15; i ++){
            cloudY[i] = random.nextInt(Game.WIDTH-cloud.getHeight());
        }
        */
        //initialize a player for single player or a placeholder player for multi player
        game.getPlayerHandler().myPlayer = new Player(0, 0, UUID.randomUUID(), ID.Player, "Bob", game.getPlayerHandler(), this);
        if(type == 's') {
            level = 1;
        }else if(type == 'm'){
            level = 0;
        }
        levelLoader = new LevelLoader(this, game.getPlayerHandler());
        levelLoader.setLevel(maps[level]);
        levelLoader.load();
    }

    private void initImages(){

        BufferedImageLoader loader = new BufferedImageLoader();
        //initialize game maps
        maps[0] = loader.loadImage("/multi_map.png");
        maps[1] = loader.loadImage("/level1.png"); //loading level 1
        maps[2] = loader.loadImage("/level2.png"); //loading level 2
        //initialize images
        //cloud = loader.loadImage("/cloud.png"); //loading clouds
    }

    public void reinit(Game game){
        initImages();
        this.game = game;
        this.camera = game.getCamera();
        levelLoader = new LevelLoader(this, game.getPlayerHandler());
        levelLoader.setLevel(maps[level]);
        //player.reinit();
        for(int i = 0; i < object.size(); i ++){
            object.get(i).reinit();
        }
    }

    public void tick(){
        for(int i = 0; i < object.size(); i ++){
            tempObject = object.get(i);
            tempObject.tick();
            //if object is outside the bounds of the map, destroy it
            if(tempObject.getY() > maps[level].getHeight()*LevelLoader.FACTOR){
                tempObject.destroy();
            }
        }
    }

    public void render(Graphics g){
        /*
        for(int i = 0; i < 15; i ++){
            g.drawImage(cloud, i*cloud.getWidth()*3, cloudY[i], null);
        }
        */
        for(int i = 0; i < object.size(); i ++){
            tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void loadImageLevel(BufferedImage image){
        levelLoader.setLevel(image);
        levelLoader.load();

    }

    private void clearLevel(){
        object.clear();
    }

    public void switchLevel(){
        this.clearLevel();
        camera.setX(0); camera.setY(0);

        level ++;
        //if no more levels remaining, quit
        //else, go to next level
        if(level == maps.length){
            this.game.quit();
        }else{
            this.loadImageLevel(maps[level]);
        }

    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.object.remove(object);
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public void setLevelLoader(LevelLoader levelLoader) {
        this.levelLoader = levelLoader;
    }
}
