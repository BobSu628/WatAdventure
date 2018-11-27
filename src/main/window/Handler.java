package main.window;

import main.entities.Player;
import main.framework.Game;
import main.framework.GameObject;
import main.framework.ID;
import main.framework.LevelLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;

/*
 */

public class Handler implements Serializable {

    private transient Game game;
    public Player player;
    public LinkedList<GameObject> object = new LinkedList<GameObject>();
    private GameObject tempObject;
    private transient LevelLoader levelLoader;
    private Camera camera;
    private transient BufferedImage level1, level2, cloud;
    private int cloudY[] = new int[15];
    private boolean leftWalk, rightWalk, leftRun, rightRun;

    public Handler(Game game, Camera camera){
        this.game = game;
        this.camera = camera;
        Random random = new Random();

        initImages();

        for(int i = 0; i < 15; i ++){
            cloudY[i] = random.nextInt(Game.WIDTH-cloud.getHeight());
        }
        player = new Player(0, 0, ID.Player, this);

        levelLoader = new LevelLoader(this);
        levelLoader.setLevel(level1);
        levelLoader.load();
    }

    public void initImages(){
        BufferedImageLoader loader = new BufferedImageLoader();
        level1 = loader.loadImage("/map.png"); //loading level 1
        level2 = loader.loadImage("/level2.png"); //loading level 2
        cloud = loader.loadImage("/cloud.png"); //loading clouds
    }

    public void reinit(Game game){
        initImages();
        this.game = game;
        levelLoader = new LevelLoader(this);
        if(game.getLevel() == 1) levelLoader.setLevel(level1);
        player.reinit();
        for(int i = 0; i < object.size(); i ++){
            object.get(i).reinit();
        }
    }

    public void tick(){
        player.tick();
        if(player.getY() > level1.getHeight()*LevelLoader.FACTOR) player.destroy();
        for(int i = 0; i < object.size(); i ++){
            tempObject = object.get(i);
            tempObject.tick();
            if(tempObject.getY() > level1.getHeight()*LevelLoader.FACTOR) tempObject.destroy();
        }
    }

    public void render(Graphics g){
        for(int i = 0; i < 15; i ++){
            g.drawImage(cloud, i*cloud.getWidth()*3, cloudY[i], null);
        }

        player.render(g);
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

        if(game.getLevel() == 1){
            this.loadImageLevel(level2);

        }else if(game.getLevel() == 2){
            this.game.quit();
        }

        game.incrementLevel();
    }

    public void addObject(GameObject object){
        this.object.add(object);
    }

    public void removeObject(GameObject object){
        this.object.remove(object);
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

    public void setGame(Game game) {
        this.game = game;
    }

    public void setLevelLoader(LevelLoader levelLoader) {
        this.levelLoader = levelLoader;
    }
}
