package main.framework;

import java.io.Serializable;
import java.util.HashMap;

import static java.awt.Color.green;

class ColorCodeTranslator {

    private HashMap<RGB, IdTypePair> map;

    ColorCodeTranslator(){
        this.map = new HashMap<>();
        map.put(new RGB(255, 255, 255), new IdTypePair(ID.Block, 0)); //dirt
        map.put(new RGB(128, 128, 128), new IdTypePair(ID.Block, 1)); //grass
        map.put(new RGB(240, 240, 240), new IdTypePair(ID.Block, 2)); //brick
        map.put(new RGB(230, 230, 230), new IdTypePair(ID.Block, 3)); //cobblestone
        map.put(new RGB(220, 220, 220), new IdTypePair(ID.Block, 4)); //plank
        map.put(new RGB(0, 0, 255),     new IdTypePair(ID.Player, 0)); //player
        map.put(new RGB(255, 255, 0),   new IdTypePair(ID.Flag, 0)); //flag
        map.put(new RGB(255, 0, 0),     new IdTypePair(ID.Teleport, 0)); //debug teleport
        map.put(new RGB(0, 255, 0),     new IdTypePair(ID.Mathiant, 0)); //Basic Enemy TA
        map.put(new RGB(255, 180, 200), new IdTypePair(ID.Elixir, 0)); //Elixir
        map.put(new RGB(160, 80, 160),  new IdTypePair(ID.Grenade, 0)); //Grenade
        map.put(new RGB(0,250,150),     new IdTypePair(ID.MechanicalPencil,0));//Mechanical Pencil
        map.put(new RGB(255, 255, 240), new IdTypePair(ID.DestroyableBlock, 0));//Destroyable Block
        map.put(new RGB(255, 255, 128), new IdTypePair(ID.Ladder, 0)); //Ladder
    }

    IdTypePair translate(int rgb){
        int red = (rgb >> 16) & 0xff, green = (rgb >> 8) & 0xff, blue = rgb & 0xff;
        RGB key = new RGB(red, green, blue);

        if(this.map.containsKey(key)) return map.get(key);
        return null;
    }

    class RGB{
        int red, green, blue;
        RGB(int red, int green, int blue){
            this.red = red; this.green = green; this.blue = blue;
        }

        @Override
        public int hashCode() {
            return (red<<16)|(green<<8)|blue;
        }

        @Override
        public boolean equals(Object obj) {
            return this.hashCode() == obj.hashCode();
        }
    }

    class IdTypePair{
        ID id;
        int type;
        IdTypePair(ID id, int type){
            this.id = id; this.type = type;
        }
    }
}
