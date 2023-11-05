package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity{
    final GamePanel gp;
    public static final  String objName = "Pièces de Bronze";

    public OBJ_Coin_Bronze(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_pickupOnly;
        name = objName;
        value = 1;
        down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
        description = "["+name+"]\nEn marche vers la richesse.";
    }

    public boolean use(Entity entity) {
        gp.SE("coin.wav");
        gp.ui.addMessage("Coin + "+value);
        gp.player.coin += value;
        return true;

    }
    
}
