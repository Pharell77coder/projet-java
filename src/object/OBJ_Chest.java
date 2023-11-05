package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{
        
        final GamePanel gp;
        public static final  String objName = "Coffre";

        public OBJ_Chest(GamePanel gp) {

                super(gp);
                this.gp = gp;
                
                type = type_obstacle;
                name = objName;
                image = setup("objects/chest", gp.tileSize, gp.tileSize);
                image2 = setup("objects/chest_opened", gp.tileSize, gp.tileSize);
                down1 = image;
                collision = true;

                solidArea.x = 4;
                solidArea.y = 16;
                solidArea.width = 40;
                solidArea.height = 32;
                solidAreaDefaultX = solidArea.x;
                solidAreaDefaultY = solidArea.y;        
        }
        public void setDialogue() {

                dialogues[0][0] = "Tu as ouvert le coffre et trouver "+loot.name+" !\n... Mais tu ne peut rien porter de plus!";
                dialogues[1][0] = "Tu as ouvert le coffre et trouver "+loot.name+" !\n... Tu obtiens "+loot.name+" !";
                dialogues[2][0] = "Il est vide...";
        }

        public void setLoot(Entity loot) {
                this.loot = loot;
                setDialogue();
        }
        public void interact() {

                if(!opened) {
                        gp.SE("unlock.wav");
                        if(!gp.player.canObtainItem(loot)) {
                                startDialogue(this, 0);
                        }
                        else {
                                startDialogue(this, 1);
                                down1 = image2;
                                opened = true;
                        }
                }
                else {
                        startDialogue(this, 2);
                }
        }
}