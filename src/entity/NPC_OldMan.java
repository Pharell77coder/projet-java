package entity;

import java.awt.Rectangle;
import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{
    
    public NPC_OldMan(GamePanel gp) {
        super(gp);

        direction = "down";
        speed = 2;
        
        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 30;
        solidArea.height = 30;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }
    public void getImage() {

        up1 = setup("npc/oldman_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("npc/oldman_up_2", gp.tileSize, gp.tileSize);
        down1 = setup("npc/oldman_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("npc/oldman_down_2", gp.tileSize, gp.tileSize);
        left1 = setup("npc/oldman_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("npc/oldman_left_2", gp.tileSize, gp.tileSize);
        right1 = setup("npc/oldman_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("npc/oldman_right_2", gp.tileSize, gp.tileSize);
    
    }
    public void setDialogue() {

        dialogues[0][0] = "Bonjour, Hero.";
        dialogues[0][1] = "Alors tu es venu sur cette île, pour\ntrouver le trésor.";
        dialogues[0][2] = "J'étais un grand magicien. Mais maintenant...\nJe suis un peu trop vieux pour partir à l'aventure.";
        dialogues[0][3] = "Bien, bonne chance à toi.";
    
        dialogues[1][0] = "Si vous êtes fatigué, reposez-vous au bord de l'eau.";
        dialogues[1][1] = "Cependant, les monstres réapparaissent si vous vous reposez.\nJe ne sais pas pourquoi mais c'est comme ça que ça marche.";
        dialogues[1][2] = "Dans tous les cas, ne vous forcez pas trop.";
        
        dialogues[2][0] = "Je me demande comment ouvrir cette porte...";   
    
    }
    public void setAction() {
        if (onPath) {
            
            searchPath(getGoalCol(gp.player), getGoalRow(gp.player), false);
        }else {
            actionLockCounter++;

            if(actionLockCounter > 120){
                Random random =  new Random();
                int i = random.nextInt(100)+1; //pick up a number 1 a 100

                if(i <= 25){ direction = "up"; }
                if(i > 25 && i <= 50){ direction = "down"; }
                if(i > 50 && i <= 75){ direction = "left"; }
                if(i > 75){ direction = "right"; }
                actionLockCounter = 0;
            }
        }
    }
    public void speak(){

        facePlayer();
        startDialogue(this,dialogueSet);

        dialogueSet++;

        if(dialogues[dialogueSet][0] == null) {
            dialogueSet--;
            onPath = true;
        }

        if(gp.player.life < gp.player.maxLife/3) {
            dialogueSet = 1;
            
        }

    }
}
