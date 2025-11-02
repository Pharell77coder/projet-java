package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_BlueHeart extends Entity {

    GamePanel gp;
    public static final String objName = "Cœur Bleu";
    
    public OBJ_BlueHeart(GamePanel gp) {
        super(gp);
        this.gp = gp;


        type = type_pickupOnly;
        name = objName;
        down1 = setup("objects/blueheart", gp.tileSize, gp.tileSize);
 
        setDialogue();
    }
    public void setDialogue() {
        dialogues[0][0] = "Vous ramassez une magnifique gemme bleue.";
        dialogues[0][1] = "Vous trouvez le Cœur Bleu, le trésor légendaire !";
    }
    public boolean use(Entity entity) {
        /*gp.ui.currentDialogue = dialogues[0][0];
        gp.ui.drawDialogueScreen();
        gp.gameState = gp.dialogueState;

        gp.ui.currentDialogue = dialogues[0][1];
        gp.ui.drawDialogueScreen();
        gp.gameState = gp.dialogueState;*/

        gp.gameState = gp.cutsceneState;
        gp.csManager.sceneNum = gp.csManager.ending;
        return true;
    }
}
