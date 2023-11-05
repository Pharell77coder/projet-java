package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Tent  extends Entity{

    public static final  String objName = "Tente";


    final GamePanel gp;
    
    public OBJ_Tent(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_consumable;
        name = objName;
        down1 = setup("objects/tent", gp.tileSize, gp.tileSize);
        description = "[Tente]\nPour passer\nune bonne nuit.";
        price = 300;
        stackable = true;
    }
    public boolean use(Entity entity) {

        gp.gameState = gp.sleepState;
        gp.SE("sleep.wav");
        gp.player.life = gp.player.maxLife;
        gp.player.mana = gp.player.maxMana;
        gp.player.getSleepingImage(down1);
        return true;
    }
}