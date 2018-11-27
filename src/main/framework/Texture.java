package main.framework;

import main.window.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class Texture {

    private SpriteSheet bs, es;
    private BufferedImage block_sheet = null;
    private BufferedImage entity_sheet = null;

    public BufferedImage[] block = new BufferedImage[5];
    public BufferedImage[] player = new BufferedImage[14];
    public BufferedImage[] player_jump = new BufferedImage[2];
    public BufferedImage player_dead;
    public BufferedImage[] enemy = new BufferedImage[14];
    public BufferedImage[] enemy_jump = new BufferedImage[2];
    public BufferedImage[] coin = new BufferedImage[5];
    public BufferedImage[] player_attack = new BufferedImage[8];
    public BufferedImage[] enemy_attack = new BufferedImage[8];
    public BufferedImage[] iClicker = new BufferedImage[5];

    public BufferedImage ladder;

    private final int BLOCK_WIDTH = 32, BLOCK_HEIGHT = 32;
    private final int ENTITY_WIDTH = 32, ENTITY_HEIGHT = 64;
    private final int COIN_WIDTH = 16, COIN_HEIGHT = 16;

    public Texture(){

        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            block_sheet = loader.loadImage("/block_sheet.png");
            entity_sheet = loader.loadImage("/entity_sheet.png");
            //coin_sheet = loader.loadImage("/block_sheet.png");
        }catch (Exception e){
            e.printStackTrace();
        }

        bs = new SpriteSheet(block_sheet);
        es = new SpriteSheet(entity_sheet);
        //cs = new SpriteSheet(coin_sheet);

        getTextures();
    }

    private void getTextures(){
        //solid blocks
        block[0] = bs.grabImage(10, 0, BLOCK_WIDTH, BLOCK_HEIGHT); //dirt
        block[1] = bs.grabImage(9, 0, BLOCK_WIDTH, BLOCK_HEIGHT); //grass
        block[2] = bs.grabImage(0, 0, BLOCK_WIDTH, BLOCK_HEIGHT); //brick
        block[3] = bs.grabImage(3, 0, BLOCK_WIDTH, BLOCK_HEIGHT); //cobblestone
        block[4] = bs.grabImage(8, 0, BLOCK_WIDTH, BLOCK_HEIGHT); //plank

        //ladder
        ladder = bs.grabImage(0, 1, BLOCK_WIDTH, BLOCK_HEIGHT); //ladder

        //coin
        coin[0] = bs.grabImage2(0,3,COIN_WIDTH,COIN_HEIGHT);
        coin[1] = bs.grabImage2(1,3,COIN_WIDTH,COIN_HEIGHT);
        coin[2] = bs.grabImage2(2,3,COIN_WIDTH,COIN_HEIGHT);
        coin[3] = bs.grabImage2(3,3,COIN_WIDTH,COIN_HEIGHT);
        coin[4] = bs.grabImage2(4,3,COIN_WIDTH,COIN_HEIGHT);

        //player looking right
        player[0] = es.grabImage(0,0,ENTITY_WIDTH, ENTITY_HEIGHT); //idle frame
        player[1] = es.grabImage(1,0,ENTITY_WIDTH, ENTITY_HEIGHT);
        player[2] = es.grabImage(2,0,ENTITY_WIDTH, ENTITY_HEIGHT);
        player[3] = es.grabImage(3,0,ENTITY_WIDTH, ENTITY_HEIGHT);
        player[4] = es.grabImage(4,0,ENTITY_WIDTH, ENTITY_HEIGHT);
        player[5] = es.grabImage(5,0,ENTITY_WIDTH, ENTITY_HEIGHT);
        player[6] = es.grabImage(6,0,ENTITY_WIDTH, ENTITY_HEIGHT);

        //player looking left
        player[7] = es.grabImage(14,0,ENTITY_WIDTH, ENTITY_HEIGHT); //idle frame
        player[8] = es.grabImage(13,0,ENTITY_WIDTH, ENTITY_HEIGHT);
        player[9] = es.grabImage(12,0,ENTITY_WIDTH, ENTITY_HEIGHT);
        player[10] = es.grabImage(11,0,ENTITY_WIDTH, ENTITY_HEIGHT);
        player[11] = es.grabImage(10,0,ENTITY_WIDTH, ENTITY_HEIGHT);
        player[12] = es.grabImage(9,0,ENTITY_WIDTH, ENTITY_HEIGHT);
        player[13] = es.grabImage(8,0,ENTITY_WIDTH, ENTITY_HEIGHT);

        //player melee attacking right
        player_attack[0] = es.grabImage(1,1,ENTITY_WIDTH,ENTITY_HEIGHT);
        player_attack[1] = es.grabImage(2,1,ENTITY_WIDTH,ENTITY_HEIGHT);

        //player melee attacking left
        player_attack[2] = es.grabImage(13,1,ENTITY_WIDTH,ENTITY_HEIGHT);
        player_attack[3] = es.grabImage(12,1,ENTITY_WIDTH,ENTITY_HEIGHT);

        //player range attacking right
        player_attack[4] = es.grabImage(5,1,ENTITY_WIDTH,ENTITY_HEIGHT);
        player_attack[5] = es.grabImage(6,1,ENTITY_WIDTH,ENTITY_HEIGHT);

        //player range attacking left
        player_attack[6] = es.grabImage(9,1,ENTITY_WIDTH,ENTITY_HEIGHT);
        player_attack[7] = es.grabImage(8,1,ENTITY_WIDTH,ENTITY_HEIGHT);


        //player jump
        player_jump[0] = es.grabImage(0, 1, ENTITY_WIDTH, ENTITY_HEIGHT); //jump left
        player_jump[1] = es.grabImage(14, 1, ENTITY_WIDTH, ENTITY_HEIGHT); //jump right

        //player dead
        player_dead = es.grabImage(3, 1, 64, 64); // dead

        //enemy looking right
        enemy[0] = es.grabImage(0,2,ENTITY_WIDTH, ENTITY_HEIGHT); //idle frame
        enemy[1] = es.grabImage(1,2,ENTITY_WIDTH, ENTITY_HEIGHT);
        enemy[2] = es.grabImage(2,2,ENTITY_WIDTH, ENTITY_HEIGHT);
        enemy[3] = es.grabImage(3,2,ENTITY_WIDTH, ENTITY_HEIGHT);
        enemy[4] = es.grabImage(4,2,ENTITY_WIDTH, ENTITY_HEIGHT);
        enemy[5] = es.grabImage(5,2,ENTITY_WIDTH, ENTITY_HEIGHT);
        enemy[6] = es.grabImage(6,2,ENTITY_WIDTH, ENTITY_HEIGHT);

        //enemy looking left
        enemy[7] = es.grabImage(14,2,ENTITY_WIDTH, ENTITY_HEIGHT); //idle frame
        enemy[8] = es.grabImage(13,2,ENTITY_WIDTH, ENTITY_HEIGHT);
        enemy[9] = es.grabImage(12,2,ENTITY_WIDTH, ENTITY_HEIGHT);
        enemy[10] = es.grabImage(11,2,ENTITY_WIDTH, ENTITY_HEIGHT);
        enemy[11] = es.grabImage(10, 2,ENTITY_WIDTH, ENTITY_HEIGHT);
        enemy[12] = es.grabImage(9, 2,ENTITY_WIDTH, ENTITY_HEIGHT);
        enemy[13] = es.grabImage(8,2,ENTITY_WIDTH, ENTITY_HEIGHT);

        //enemy melee attacking right
        enemy_attack[0] = es.grabImage(1,3,ENTITY_WIDTH,ENTITY_HEIGHT);
        enemy_attack[1] = es.grabImage(2,3,ENTITY_WIDTH,ENTITY_HEIGHT);

        //enemy melee attacking left
        enemy_attack[2] = es.grabImage(13,3,ENTITY_WIDTH,ENTITY_HEIGHT);
        enemy_attack[3] = es.grabImage(12,3,ENTITY_WIDTH,ENTITY_HEIGHT);

        //enemy jump
        enemy_jump[0] = es.grabImage(0, 3, ENTITY_WIDTH, ENTITY_HEIGHT); //jump left
        enemy_jump[1] = es.grabImage(14, 3, ENTITY_WIDTH, ENTITY_HEIGHT); //jump right

        //bullet
        iClicker[0] = bs.grabImage2(0,4,8,8);
        iClicker[1] = bs.grabImage2(1,4,8,8);
        iClicker[2] = bs.grabImage2(2,4,8,8);
        iClicker[3] = bs.grabImage2(3,4,8,8);
        iClicker[4] = bs.grabImage2(4,4,8,8);


    }

}
