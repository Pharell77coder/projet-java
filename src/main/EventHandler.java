package main;

import data.Progress;
import entity.Entity;

public class EventHandler{
    
    final GamePanel gp;
    final EventRect[][][] eventRect;
    final Entity eventMaster;

    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    int tempMap, tempCol, tempRow;

    public EventHandler(GamePanel gp) {
        this.gp = gp;

        eventMaster = new Entity(gp);

        eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
        int map = 0;
        int col = 0;
        int row = 0;
        while(map < gp.maxMap && col < gp.maxWorldCol &&  row < gp.maxWorldRow) {

        eventRect[map][col][row] = new EventRect();
        eventRect[map][col][row].x = 23;
        eventRect[map][col][row].y = 23;
        eventRect[map][col][row].width = 2;
        eventRect[map][col][row].height = 2;
        eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
        eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
        
        col++;
            if (col == gp.maxWorldCol) {
                col = 0;
                row++;
                if(row == gp.maxWorldRow) {
                    map++;
                    row = 0;
                }
            }
        }
        setDialogue();
    }
    public void setDialogue() {
        eventMaster.dialogues[0][0] = "Tu es tombé dans une fosse!";
        eventMaster.dialogues[1][0] = "Tu as bu de l'eau. \nTa vie et ton mana a été récupéré.\n(La progression a été sauvegarder)";
        eventMaster.dialogues[1][1] = "Wow, c'est de la bonne eau.";

    }
    public void checkEvent() {

        int xDistance = Math.abs(gp.player.worldX - previousEventX); 
        int yDistance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDistance); 
        if(distance > gp.tileSize) {
            canTouchEvent = true;
        }
        if(canTouchEvent){
            if(hit(0, 23, 12, "up")) { healingPool();}
            else if(hit(0, 27, 16, "any")) {damagePit();}
            else if(hit(0, 10, 39, "any")) {teleport(1,12,12, gp.indoor);} // to the marchant's house
            else if(hit(1,12,13, "any")) {teleport(0, 10, 40, gp.outside);} // to outside
            else if(hit(1,12,9, "any")) {speak(gp.npc[1][0]);}
            else if(hit(0, 12,9, "any")) {teleport(2,10,41, gp.dungeon);} // to the dungoen
            else if(hit(2,9,41, "any")) {teleport(0, 11,9, gp.outside);} // to outside
            else if(hit(2, 8,7, "any")) {teleport(3,27,41, gp.dungeon);} // to B2
            else if(hit(3,26,41, "any")) {teleport(2, 9,7, gp.dungeon);} // to B1
            else if(hit(3,25,27, "any")) {skeletonLord();} // BOSS
        }


    }
    public boolean hit(int map, int col, int row, String reDirection) {
        boolean hit = false;

        if (map == gp.currentMap) {
            gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
            gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
            eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
            eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;

            if(gp.player.solidArea.intersects(eventRect[map][col][row]) && !eventRect[map][col][row].eventDone) {
                if(gp.player.direction.contentEquals(reDirection) || reDirection.contentEquals("any")) {
                    hit = true;

                    previousEventX = gp.player.worldX;
                    previousEventY = gp.player.worldY;

                }
            }

            gp.player.solidArea.x = gp.player.solidAreaDefaultX;
            gp.player.solidArea.y = gp.player.solidAreaDefaultY;
            eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
            eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
        }
        return hit;

    }
    public void teleport(int map, int col, int row, int area) {
        gp.gameState = gp.transitionState;
        gp.nextArea = area;
        tempMap = map;
        tempCol = col;
        tempRow = row;


    }
    public void damagePit() {
        
        gp.SE("receivedamage.wav");
        eventMaster.startDialogue(eventMaster, 0);
        gp.player.life -= 1;
        canTouchEvent = false;
    }
    public void healingPool() {
        if (gp.keyH.enterPressed) {
            gp.player.attackCanceled = true;
            gp.SE("powerup.wav");
            eventMaster.startDialogue(eventMaster, 1);
            gp.player.life = gp.player.maxLife;
            gp.player.mana = gp.player.maxMana;
            gp.aSetter.setMonster();
            gp.saveLoad.save();
        }
    }
    public void speak(Entity entity) {
        if(gp.keyH.enterPressed) {
            gp.gameState = gp.dialogueState;
            gp.player.attackCanceled = true;
            entity.speak();
        }
    }
    public void skeletonLord() {
        if(gp.bossBattleOn == false && Progress.skeletonLordDefeated == false) {
            gp.gameState = gp.cutsceneState;
            gp.csManager.sceneNum = gp.csManager.skeletonLord;
        }
    }
}
