package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Heart extends Entity{
    final GamePanel gp;
    public static final  String objName = "Coeur";

    public OBJ_Heart(GamePanel gp) {
            super(gp);
            this.gp = gp;
            

            type = type_pickupOnly;
            name = objName;
            value = 2;
            down1 = setup("objects/heart_full", gp.tileSize, gp.tileSize);
            image = setup("objects/heart_full", gp.tileSize, gp.tileSize);
            image2 = setup("objects/heart_half", gp.tileSize, gp.tileSize);
            image3 = setup("objects/heart_blank", gp.tileSize, gp.tileSize);
            description = "["+name+"]\n Bon pour la santé.";
    }
    public boolean use(Entity entity) {
        gp.SE("coin.wav");
        gp.ui.addMessage("Vie + "+value);
        entity.life += value;
        return true;

    }

}
