package main.framework;

import main.entities.TA;
import main.entities.Player;
import main.items.Elixir;
import main.items.Grenade;
import main.items.Weapon.MechanicalPencil;
import main.objects.*;
import main.window.Handler;

import java.awt.image.BufferedImage;
import java.io.Serializable;

public class LevelLoader {

    private int w, h;
    private BufferedImage level;
    private ColorCodeTranslator cct;
    private Handler handler;
    private Player player;

    public static final int FACTOR = 32;

    public LevelLoader(Handler handler){

        this.cct = new ColorCodeTranslator();
        this.handler = handler;
    }

    public void setLevel(BufferedImage level){
        this.level = level;

        this.w = level.getWidth();
        this.h = level.getHeight();
        //player = new Player(0, 0, ID.Player, handler);

    }

    public void load(){
        for(int xx = 0; xx < w; xx ++){
            for(int yy = 0; yy < h; yy ++){
                int pixel = level.getRGB(xx, yy);

                ColorCodeTranslator.IdTypePair identifier = cct.translate(pixel);
                if(identifier != null) {
                    float displayX = xx * FACTOR, displayY = yy * FACTOR;
                    if (identifier.id == ID.Block) {
                        handler.addObject(new Block(displayX, displayY, identifier.type, ID.Block, handler));
                    } else if (identifier.id == ID.Player) {
                        handler.player.setX(displayX); handler.player.setY(displayY);
                    } else if (identifier.id == ID.Flag) {
                        handler.addObject(new Flag(displayX, displayY, ID.Flag, handler));
                    } else if(identifier.id == ID.Teleport){
                        handler.addObject(new Debug_Teleport(displayX, displayY, ID.Teleport, handler));
                    } else if(identifier.id == ID.Mathiant){
                        handler.addObject(new TA(displayX, displayY, ID.Mathiant, handler));
                    } else if(identifier.id == ID.Elixir){
                        handler.addObject(new Elixir(displayX, displayY, ID.Elixir, handler));
                    } else if(identifier.id == ID.Grenade){
                        handler.addObject(new Grenade(displayX, displayY, ID.Grenade, handler));
                    } else if (identifier.id == ID.MechanicalPencil){
                        handler.addObject((new MechanicalPencil(displayX,displayY,ID.MechanicalPencil,handler,1000,1000, 1,2)));
                    } else if(identifier.id == ID.DestroyableBlock){
                        handler.addObject(new DestroyableBlock(displayX, displayY, identifier.type, ID.DestroyableBlock, handler));
                    }else if(identifier.id == ID.Ladder){
                        handler.addObject(new Ladder(displayX, displayY, ID.Ladder, handler));
                    }
                }

            }
        }
    }

}
