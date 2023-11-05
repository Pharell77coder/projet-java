package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{

    final GamePanel gp;
   
    public static final  String objName = "Potion Rouge";

    public OBJ_Potion_Red(GamePanel gp) {
        super(gp);
   
        this.gp = gp;
        
        type = type_consumable;
        name = objName;
        value = 5;
        down1 = setup("objects/potion_red", gp.tileSize, gp.tileSize);
        description = "["+name+"]\nUne petite soif?\nDonne 5 points.";
        price = 25;
        stackable = true;

        setDialogue();
    }
    public void setDialogue() {
        dialogues[0][0] = "Tu bois la "+name+"!\n"+"Tu récupéres "+value+" points de vies.";
    }
    public boolean use(Entity entity) {
        
        startDialogue(this, 0);
        entity.life += value;
        if(entity.life > entity.maxLife) {
            entity.life = entity.maxLife;
        }
        gp.SE("powerup.wav");
        return true;
    }
    
}
