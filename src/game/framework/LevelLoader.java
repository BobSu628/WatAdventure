package game.framework;

import game.window.PlayerHandler;
import game.entities.TA;
import game.items.Elixir;
import game.items.Grenade;
import game.items.Weapon.MechanicalPencil;
import game.objects.*;

import java.awt.image.BufferedImage;

public class LevelLoader {

    private int w, h;
    private BufferedImage map;
    private ColorCodeTranslator cct;
    private Handler handler;
    private PlayerHandler playerHandler;

    public static final int FACTOR = 32;

    public LevelLoader(Handler handler, PlayerHandler playerHandler){
        this.cct = new ColorCodeTranslator();
        this.handler = handler;
        this.playerHandler = playerHandler;
    }

    public void setMap(BufferedImage map){
        this.map = map;

        //get the width and height of current map
        this.w = map.getWidth();
        this.h = map.getHeight();

    }

    public void load(){
        for(int xx = 0; xx < w; xx ++){
            for(int yy = 0; yy < h; yy ++){
                int pixel = map.getRGB(xx, yy);

                ColorCodeTranslator.IdTypePair identifier = cct.translate(pixel);
                if(identifier != null) {
                    float displayX = xx * FACTOR, displayY = yy * FACTOR;
                    if (identifier.id == ID.Block) {
                        handler.addObject(new Block(displayX, displayY, identifier.type, ID.Block, handler));
                    } else if (identifier.id == ID.Player) {
                        playerHandler.myPlayer.setX(displayX); playerHandler.myPlayer.setY(displayY);
                    } else if (identifier.id == ID.Flag) {
                        handler.addObject(new Flag(displayX, displayY, ID.Flag, handler));
                    } else if(identifier.id == ID.Teleport){
                        handler.addObject(new Debug_Teleport(displayX, displayY, ID.Teleport, handler));
                    } else if(identifier.id == ID.Mathiant){
                        handler.addObject(new TA(displayX, displayY, ID.Mathiant, playerHandler, handler));
                    } else if(identifier.id == ID.Elixir){
                        handler.addObject(new Elixir(displayX, displayY, ID.Elixir, handler));
                    } else if(identifier.id == ID.Grenade){
                        handler.addObject(new Grenade(displayX, displayY, ID.Grenade, handler));
                    } else if (identifier.id == ID.MechanicalPencil){
                        handler.addObject((new MechanicalPencil(displayX,displayY,ID.MechanicalPencil,handler,1000,1000, 1,2)));
                    } else if(identifier.id == ID.DestroyableBlock){
                        handler.addObject(new TrapBlock(displayX, displayY, identifier.type, ID.DestroyableBlock, handler));
                    }else if(identifier.id == ID.Ladder){
                        handler.addObject(new Ladder(displayX, displayY, ID.Ladder, handler));
                    }
                }

            }
        }
    }

}
