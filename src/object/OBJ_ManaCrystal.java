package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_ManaCrystal extends Entity{

    final GamePanel gp;
    public static final  String objName = "Cristal de Mana";

    public OBJ_ManaCrystal(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        value = 1;
        name = objName;
        down1 = setup("objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image = setup("objects/manacrystal_full", gp.tileSize, gp.tileSize);
        image2 = setup("objects/manacrystal_blank", gp.tileSize, gp.tileSize);
        description = "["+name+"]\n Abracadabra.";
    }
    public boolean use(Entity entity) {
        gp.SE("coin.wav");
        gp.ui.addMessage("Mana + "+value);
        entity.mana += value;
        return true;

    }
    
}
