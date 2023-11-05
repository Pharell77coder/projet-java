package entity;

import java.awt.Rectangle;

import main.GamePanel;
import object.*;

public class NPC_Mechant extends Entity{
    
    public NPC_Mechant(GamePanel gp) {
        super(gp);

        direction = "down";

        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 8;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

        getImage();
        setDialogue();
        setItems();
    }
    public void getImage() {

        down1 = setup("npc/merchant_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("npc/merchant_down_2", gp.tileSize, gp.tileSize);
        up1 = down1; up2 = down2; left1 = down1;   left2 = down2; right1 = down1;  right2 = down2;
    
    }
    public void setDialogue() {

        dialogues[0][0] = "Hé hé, alors tu m'as trouvé. \nJ'ai de bonnes choses pour vous.\nVoulez-vous échanger?";
        dialogues[1][0] = "Reviens me voir, héhé !";
        dialogues[2][0] = "Tu a besoin de plus pour payer cela!";
        dialogues[3][0] = "Tu ne peux plus rien porter.";
        dialogues[4][0] = "Tu a équipé cet objet!";


    
    }
    public void setItems() {
        inventory.add(new OBJ_Potion_Red(gp));
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Sword_Normal(gp));
        inventory.add(new OBJ_Axe(gp));
        inventory.add(new OBJ_Shield_Wood(gp));
        inventory.add(new OBJ_Shield_Blue(gp));
        inventory.add(new OBJ_Tent(gp));
        inventory.add(new OBJ_Lantern(gp));
        inventory.add(new OBJ_Pickaxe(gp));

    }
    public void speak() {
        facePlayer();
        gp.gameState = gp.tradeState;
        gp.ui.npc = this;
    }
    
}
