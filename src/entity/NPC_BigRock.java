package entity;

import java.awt.Rectangle;
import java.util.ArrayList;

import main.GamePanel;
import object.OBJ_Door_Iron;
import tile_interactive.IT_MetalPlate;
import tile_interactive.InteractiveTile;

public class NPC_BigRock  extends Entity{

    public static final  String npcName = "Gros Rocher";
    
    public NPC_BigRock(GamePanel gp) {
        super(gp);

        name = npcName;
        direction = "down";
        speed = 4;

        solidArea = new Rectangle();
        solidArea.x = 2;
        solidArea.y = 6;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 44;
        solidArea.height = 40;

        dialogueSet = -1;

        getImage();
        setDialogue();
    }
    public void getImage() {

        down1 = setup("npc/bigrock", gp.tileSize, gp.tileSize);
        down2 = down1; up1 = down1; up2 = down2; left1 = down1;   left2 = down2; right1 = down1;  right2 = down2;
    
    }
    public void setDialogue() {
        dialogues[0][0] = "C'est un Rocher Géant.";
    }

    public void update() {}
    public void speak(){

        facePlayer();
        startDialogue(this,dialogueSet);

        dialogueSet++;

        if(dialogues[dialogueSet][0] == null) {
            dialogueSet--;
        }

        if(gp.player.life < gp.player.maxLife/3) {
            dialogueSet = 1;
        }
    }
    public void move(String d) {
        this.direction = d;

        checkCollision();

        if(!collisionOn) {
            switch(direction) {
                case "up": worldY -= speed; break;
                case "down": worldY += speed; break;  
                case "left": worldX -= speed; break;
                case "right": worldX += speed; break;
            }
        }
        detectPlate();
    }
    public void detectPlate() {

        ArrayList<InteractiveTile> plateList = new ArrayList<>();
        ArrayList<Entity> rockList = new ArrayList<>();
        int j = 1;
        for(int i = 0; i < gp.iTile[j].length; i++) {
            if(gp.iTile[gp.currentMap][i] != null && 
            gp.iTile[gp.currentMap][i].name != null &&
            gp.iTile[gp.currentMap][i].name.equals(IT_MetalPlate.itName)) {
                plateList.add(gp.iTile[gp.currentMap][i]);
            }
        }
        for(int i = 0; i < gp.npc[j].length; i++) {
            if(gp.npc[gp.currentMap][i] != null && 
            gp.npc[gp.currentMap][i].name.equals(NPC_BigRock.npcName)) {
                rockList.add(gp.npc[gp.currentMap][i]);
            }
        }

        int count = 0;

        for (InteractiveTile interactiveTile : plateList) {
            int xDistance = Math.abs(worldX - interactiveTile.worldX);
            int yDistance = Math.abs(worldY - interactiveTile.worldY);
            int distance = Math.max(xDistance, yDistance);

            if (distance < 8) {

                if (linkedEntity == null) {
                    linkedEntity = interactiveTile;
                    gp.SE("unlock.wav");
                }
            } else {
                if (linkedEntity == interactiveTile) {
                    linkedEntity = null;
                }
            }
        }
        for (Entity entity : rockList) {
            if (entity.linkedEntity != null) {
                count++;
            }
        }
        if(count == rockList.size()) {
            for(int i = 0; i < gp.obj[j].length; i++) {
                if(gp.obj[gp.currentMap][i] != null && 
                gp.obj[gp.currentMap][i].name.equals(OBJ_Door_Iron.objName)) {
                    gp.obj[gp.currentMap][i] = null;
                    gp.SE("dooropen.wav");
                }
            }
        }
    }
}
