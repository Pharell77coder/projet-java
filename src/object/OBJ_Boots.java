package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boots extends Entity{
        final GamePanel gp;
        public static final  String objName = "Bottes";
           
        public OBJ_Boots(GamePanel gp) {

                super(gp);
                this.gp = gp;

                name = objName;
                down1 = setup("objects/boots", gp.tileSize, gp.tileSize);
                description = "["+name+"]\n Utile pour courir.";
                value = 1;
                type = type_consumable;
                
                setDialogue();
        }
        public void setDialogue() {
                dialogues[0][0] = "Tu prends les "+name+"!\n"+"T'as vitesse augmente de "+value+" points.";
        }
        public boolean use(Entity entity) {
                
                startDialogue(this, 0);
                entity.defaultSpeed += value;
                entity.speed += value;

                gp.SE("powerup.wav");
                return true;
        }
}
