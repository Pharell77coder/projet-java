package object;

import entity.Entity;
import main.GamePanel;


public class OBJ_Key extends Entity{

        final GamePanel gp;
        public static final  String objName = "Clé";

        public OBJ_Key(GamePanel gp) {
                super(gp);
                this.gp = gp;

                type = type_consumable;
                name = objName;
                down1 = setup("objects/key", gp.tileSize, gp.tileSize);
                description = "["+name+"]\nQue peut t'elle\nbien ouvrir?";
                price = 100;
                stackable = true;

                setDialogue();
        }
        public void setDialogue() {
            dialogues[0][0] = "Tu a utilisé la "+name+" pour ouvrir la porte.";
            dialogues[1][0] = "Qu'est ce que tu fais ?";
        }
        public boolean use(Entity entity) {

            int objIndex = getDectected(entity, gp.obj, "Porte");

            if(objIndex != 999) {
                startDialogue(this, 0);
                gp.SE("unlock.wav");
                gp.obj[gp.currentMap][objIndex] = null;
                return true;
            }
            else {
                startDialogue(this, 1);
                return false;
            }
        }
}
