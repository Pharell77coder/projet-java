package main;

import java.awt.Color;
import java.awt.Graphics2D;

import entity.PlayerDummy;
import monster.MON_SkeletonLord;
import object.OBJ_BlueHeart;
import object.OBJ_Door_Iron;

public class CutsceneManager {
    
    GamePanel gp;
    Graphics2D g2;
    public int sceneNum;
    public int scenePhase;
    int counter = 0;
    float alpha = 0f;
    int y;
    String endCredit;

    // Scene Number
    public final int NA = 0;
    public final int skeletonLord = 1;
    public final int ending = 2;

    public CutsceneManager(GamePanel gp) {
        this.gp = gp;

        endCredit  = "CREDITS\n"
        + "\n"
        + "Program\n"
        + "RyiSnow\n"
        + "Pharell\n"
        + "\n"
        + "Music & Sound Design\n"
        + "RyiSnow\n"
        + "FreeSound.org\n"
        + "OpenGameArt.org\n"
        + "\n"
        + "Art & Visuals\n"
        + "RyiSnow\n"
        + "Itch.io\n"
        + "\n"
        + "Testers\n"
        + "Gabriel\n"
        + "Emile\n"
        + "Hugues-Arnaud\n"
        + "\n"
        + "Special Thanks\n"
        + "Mom and Dad\n"
        + "YouTube.com\n"
        + "The Indie Dev Community\n"
        + "Everyone who supported this project\n"
        + "and YOU !";
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        switch(sceneNum) {
            case skeletonLord:  scene_skeletonLord(); break;
            case ending:  scene_ending(); break;
        }
    }
    public void scene_skeletonLord() {
        if(scenePhase == 0) {
            gp.bossBattleOn = true;

            for(int i = 0; i < gp.obj[1].length; i++) {
                if(gp.obj[gp.currentMap][i] == null) {
                    gp.obj[gp.currentMap][i] = new OBJ_Door_Iron(gp);
                    gp.obj[gp.currentMap][i].worldX = gp.tileSize*25;
                    gp.obj[gp.currentMap][i].worldY = gp.tileSize*28;
                    gp.obj[gp.currentMap][i].temp = true;
                    gp.SE("dooropen.wav");
                    break;
                }
            }
            // Scarch a vacant slot for the dummy
            for(int i = 0; i < gp.npc[1].length; i++){
                if(gp.npc[gp.currentMap][i] == null){
                    gp.npc[gp.currentMap][i] = new PlayerDummy(gp);
                    gp.npc[gp.currentMap][i].worldX = gp.player.worldX;
                    gp.npc[gp.currentMap][i].worldY = gp.player.worldY;
                    gp.npc[gp.currentMap][i].direction = gp.player.direction;
                    break;
                }
            }
            gp.player.drawing = false;

            scenePhase++;
        }
        if(scenePhase == 1) {
            gp.player.worldY -=2;

            if(gp.player.worldY < gp.tileSize*16) {
                scenePhase++;
            }
        }
        if(scenePhase == 2) {

            //Search the boss
            for(int i = 0; i < gp.monster[1].length; i++) {
                if(gp.monster[gp.currentMap][i] != null && gp.monster[gp.currentMap][i].name == MON_SkeletonLord.monName) {
                    gp.monster[gp.currentMap][i].sleep = false;
                    gp.ui.npc = gp.monster[gp.currentMap][i];
                    scenePhase++;
                    break;
                }
            }
        }
        if(scenePhase == 3) {

            // The boss speaks
            gp.ui.drawDialogueScreen();

        }
        if(scenePhase == 4) {
            // Retour au joueur
            for(int i = 0; i < gp.npc[1].length; i++) {
                if(gp.npc[gp.currentMap][i] != null && gp.npc[gp.currentMap][i].name.equals(PlayerDummy.npcName)) {
                    gp.player.worldX = gp.npc[gp.currentMap][i].worldX;
                    gp.player.worldY = gp.npc[gp.currentMap][i].worldY;
                    // Delete the dummy
                    gp.npc[gp.currentMap][i] = null;
                    break;
                }
            }
            gp.player.drawing = true;
            scenePhase++;
            //Reset
            sceneNum = NA;
            scenePhase = 0;
            gp.gameState = gp.playState;

            //Change the music
            gp.stopMusic();
            gp.playMusic("FinalBattle.wav");
        }
    }
    public void scene_ending() {
        if(scenePhase == 0) {
            gp.stopMusic();
            gp.ui.npc = new OBJ_BlueHeart(gp);
            scenePhase++;
        }
        if(scenePhase == 1) {
            gp.ui.drawDialogueScreen();
        }
        if (scenePhase == 2) {
            gp.SE("fanfare.wav");
            scenePhase++;
    }
        if(scenePhase == 3) {
            if(counterReached(300)) {
                scenePhase++;
            }
        }
        if(scenePhase == 4) {
            alpha += 0.005f;
            if(alpha >= 1f) {
                alpha = 1f;
            }
            drawBlackBackground(alpha);

            if(alpha == 1f) {
                alpha = 0;
                scenePhase++;
            }
        }
        if(scenePhase == 5) {
            drawBlackBackground(1f);
            alpha += 0.005f;
            if(alpha >= 1f) {
                alpha = 1f;
            }

            String text = "Après un combat acharné contre le seigneur squelette,\n"
                        + "le garçon bleu trouva enfin le trésor légendaire.\n"
                        + "Mais ce n'est pas la fin de son voyage.\n"
                        + "L'aventure du garçon bleu ne fait que commencer.";
            drawString(alpha, 30f, 200, text, 70);
            if(counterReached(600)) {
                gp.playMusic("BlueBoyAdventure.wav");
                scenePhase++;
            }
            
        }
        if(scenePhase == 6) {
            drawBlackBackground(1f);
            drawString(alpha, 30f, gp.screenHeight/2, "Blue Boy Adventure", 40);
            if(counterReached(480)) {
                scenePhase++;
            }
        }
        if(scenePhase == 7) {
            drawBlackBackground(1f);
            y = gp.screenHeight/2;
            drawString(1f, 38f, y, endCredit, 40);
            if(counterReached(480)) {
                scenePhase++;
            }
        }
        if(scenePhase == 8){
            drawBlackBackground(1f);

            y--;
            drawString(1f, 38f, y, endCredit, 40);
        }
    }
    public boolean counterReached(int target) {

        boolean counterReached = false;

        counter++;
        if(counter > target) {
            counterReached = true;
            counter = 0;
            
        }
        return counterReached;
    }
    public void drawBlackBackground(float alpha) {
        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.black);
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));
    }
    public void drawString(float alpha, float fontSize, int y, String text, int lineHeight) {
        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(fontSize));

        for(String line : text.split("\n")) {
            int x = gp.ui.getXforCenteredText(line);
            g2.drawString(line, x, y);
            y += lineHeight;
        }
        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1f));
    }
}
