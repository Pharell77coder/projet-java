package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Door extends Entity{

    public static final  String objName =  "Porte";
    
    public OBJ_Door(GamePanel gp) {
        super(gp);

        type = type_obstacle;
        name = objName;
        down1 = setup("objects/door", gp.tileSize, gp.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        setDialogue();
    }
    public void setDialogue() {
        dialogues[0][0] = "Tu a besoin d'une clé pour ouvrir cette porte.";
    }
    public void interact() {

        startDialogue(this,0);
        
    }
}

