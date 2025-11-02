package main;

import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import entity.Entity;


@SuppressWarnings("ALL")
public class UI {

    final GamePanel gp;
    Graphics2D g2;
    public Font maruMonica;
    Font purisaB;
    final BufferedImage heart_full;
    final BufferedImage heart_half;
    final BufferedImage heart_blank;
    final BufferedImage crystal_full;
    final BufferedImage crystal_blank;
    final BufferedImage coin;
    public boolean messageOn = false;
    final ArrayList<String>message = new ArrayList<>();
    final ArrayList<Integer>messageCounter = new ArrayList<>();
    public boolean gameFinished = false;
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int playerSlotCol = 0;
    public int playerSlotRow = 0;
    public int npcSlotCol = 0;
    public int npcSlotRow = 0;
    int subState = 0;
    int counter = 0;
    public Entity npc;
    int charIndex = 0;
    String combinedText = "";

    public UI(GamePanel gp) {

        this.gp = gp;
        try {
            InputStream is = new FileInputStream("res/font/Purisa Bold.ttf");
            purisaB = Font.createFont(Font.TRUETYPE_FONT, is);
            is = new FileInputStream("res/font/x12y16pxMaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        // CREATE HUD OBJECT
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
        Entity crystal = new OBJ_ManaCrystal(gp);
        crystal_full = crystal.image;
        crystal_blank = crystal.image2;
        Entity bronzeCoin = new OBJ_Coin_Bronze(gp);
        coin = bronzeCoin.down1;
    }
    public void addMessage(String text){

        message.add(text);
        messageCounter.add(0);

    }
    public void draw(Graphics2D g2){
        this.g2 = g2;

        g2.setFont(maruMonica);
        //g2.setFont(purisaB);

        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        //TITLE STATE
        if(gp.gameState == gp.titleState){
            drawTitleScreen();
        }

        //PLAY STATE
        if(gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }
        //PAUSE STATE
        if(gp.gameState == gp.pauseState) {
            drawPlayerLife();
            drawPausedScreen();
        }
        //DIALOGUE STATE
        if(gp.gameState == gp.dialogueState) {

            drawDialogueScreen();
        }
        //CHARACTER STATE
        if(gp.gameState == gp.characterState) {
            drawCharacterSreen();
            drawInventory(gp.player,true);
        }
        //OPTIONS STATE
        if(gp.gameState == gp.optionsState){
            drawOptionsScreen();
        }
        //GAME OVER STATE
        if(gp.gameState == gp.gameOverState){
            drawGameOverScreen();
        }
        //TRANSITION STATE
        if(gp.gameState == gp.transitionState){
            drawTransition();
        }
        //TRADE STATE
        if(gp.gameState == gp.tradeState){
            drawTradeScreen();
        }
        //SLEEP STATE
        if(gp.gameState == gp.sleepState){
            drawSleepScreen();
        }
    }
    public void drawPlayerLife() {
        
        //RESET
        int x = gp.tileSize/2;
        int y = gp.tileSize/2;
        int i = 0;

        int iconSize = 32;
        int manaStartX = (gp.tileSize/2)-5;
        int manaStartY = 0;

        //DRAW CURRENT LIFE
        while(i < gp.player.maxLife/2) {
            g2.drawImage(heart_blank, x, y, iconSize, iconSize, null);
            i++;
            x += gp.tileSize*0.75;
            manaStartY = y + 32;

            if(i % 8 == 0) {
                x = gp.tileSize/2;
                y += iconSize;
            }
        }

        x = gp.tileSize/2;
        y = gp.tileSize/2;
        i = 0;

        while(i < gp.player.life) {
            g2.drawImage(heart_half, x, y, iconSize, iconSize, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, iconSize, iconSize, null);
            }
            i++;
            x += gp.tileSize*0.75;

            if(i % 16 == 0) {
                x = gp.tileSize/2;
                y += iconSize;
            }
        }
        

        //DRAW MAX MANA
        x = manaStartX;
        y = manaStartY;
        i = 0;
        //y += gp.tileSize;
        while(i < gp.player.maxMana) {
            g2.drawImage(crystal_blank, x, y, iconSize, iconSize, null);
            i++;
            x += 20;
        }
        x = manaStartX;
        y = manaStartY;
        i = 0;

        while(i < gp.player.mana) {
            g2.drawImage(crystal_full, x, y, iconSize, iconSize, null);
            i++;
            x += 20;
        }



    }
    @SuppressWarnings("SuspiciousListRemoveInLoop")
    public void drawMessage() {

        int messageX = gp.tileSize;
        int messageY = gp.tileSize*4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 28f));

        for(int i = 0; i < message.size(); i++) {
            if(message.get(i) != null) {
                
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX+2, messageY+2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1;
                messageCounter.set(i, counter);

                messageY += 50;

                if(messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);

                }
            }
        }

    }
    public void drawTitleScreen() {

        if (titleScreenState == 0) {
            g2.setColor(new Color(0,0,0));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

            //TITLE NAME
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,70F));
            String text = "Blue Boy Adventure";
            int x = getXforCentreredText(text);
            int y = gp.tileSize*3;

            //SHADOW
            g2.setColor(Color.gray);
            g2.drawString(text, x+5, y+5);

            //MAIN COLOR
            g2.setColor(Color.white);
            g2.drawString(text, x, y);

            //IMAGE
            x = gp.screenWidth/2 - (gp.tileSize*2)/2;
            y += gp.tileSize*2;
            g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2, null);

            //MENU
            g2.setFont(g2.getFont().deriveFont(Font.BOLD,40F));

            text = "NOUVELLE PARTIE";
            x = getXforCentreredText(text);
            y += gp.tileSize*3.5;
            g2.drawString(text, x, y);
            if (commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "CHARGER UNE PARTIE";
            x = getXforCentreredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "QUITTER";
            x = getXforCentreredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if (commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }
        } else if(titleScreenState == 1){
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(40F));

            String text = "Sélectionnez votre classe !";
            int x = getXforCentreredText(text);
            int y = gp.tileSize*3;
            g2.drawString(text, x, y);

            text = "Combattant";
            x = getXforCentreredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 0){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Voleur";
            x = getXforCentreredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Sorcier";
            x = getXforCentreredText(text);
            y += gp.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2){
                g2.drawString(">", x-gp.tileSize, y);
            }

            text = "Retour";
            x = getXforCentreredText(text);
            y += gp.tileSize*2;
            g2.drawString(text, x, y);
            if(commandNum == 3){
                g2.drawString(">", x-gp.tileSize, y);
            }
        }
    }
    public void drawPausedScreen() {

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSE";
        int x = getXforCentreredText(text);
        int y = gp.screenHeight/2;
        g2.drawString(text, x, y);

    }
    public void drawDialogueScreen(){

        //WINDOW
        int x = gp.tileSize*2;
        int y = gp.tileSize/2;
        int width = gp.screenWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;

        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,28F));
        x += gp.tileSize;
        y += gp.tileSize;

        if(npc.dialogues[npc.dialogueSet][npc.dialogueIndex] != null) {
            //currentDialogue = npc.dialogues[npc.dialogueSet][npc.dialogueIndex];
            
            char[] characters = npc.dialogues[npc.dialogueSet][npc.dialogueIndex].toCharArray();
            
            if(charIndex < characters.length) {
                
                gp.SE("speak.wav");
                String s = String.valueOf(characters[charIndex]);
                combinedText = combinedText + s;
                currentDialogue = combinedText;
                charIndex++;
            }

            if(gp.keyH.enterPressed) {

                charIndex = 0;
                combinedText = "";

                if(gp.gameState == gp.dialogueState) {
                    npc.dialogueIndex++;
                    gp.keyH.enterPressed = false;
                }
            }
        }
        else {
            npc.dialogueIndex = 0;

            if(gp.gameState == gp.dialogueState) {
                gp.gameState = gp.playState;
            }
        }

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);  
            y += 40;
        }
        
    }
    public void drawCharacterSreen() {

        // CREATE A FRAME
        final int frameX = gp.tileSize*2;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize*5;
        final int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //TEXT 
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(28F));

        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 32;

        //NAMES
        g2.drawString("Niveau", textX, textY);
        textY += lineHeight;
        g2.drawString("Vie", textX, textY);
        textY += lineHeight;
        g2.drawString("Mana", textX, textY);
        textY += lineHeight;
        g2.drawString("Force", textX, textY);
        textY += lineHeight;
        g2.drawString("Dextérité", textX, textY);
        textY += lineHeight;
        g2.drawString("Attaque", textX, textY);
        textY += lineHeight;
        g2.drawString("Défense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Niveau suivant", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Arme", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Bouclier", textX, textY);

        //Values
        int tailX = (frameX + frameWidth) - 30;

        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.life + "/" + gp.player.maxLife;
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = gp.player.mana + "/" + gp.player.maxMana;
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexterity);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXforAlightToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;


        g2.drawImage(gp.player.currentWeapon.down1, tailX - 30, textY-10, null);
        textY += lineHeight;

        g2.drawImage(gp.player.currentShield.down1, tailX - 30, textY, null);

    }
    public void drawInventory(Entity entity, boolean cursor) {

        int frameX;
        int frameY;
        int frameWidth;
        int frameHeight;
        int slotCol;
        int slotRow;

        if(entity == gp.player) {
            frameX = gp.tileSize*12;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = playerSlotCol;
            slotRow = playerSlotRow;
        }else {
            frameX = gp.tileSize*2;
            frameY = gp.tileSize;
            frameWidth = gp.tileSize*6;
            frameHeight = gp.tileSize*5;
            slotCol = npcSlotCol;
            slotRow = npcSlotRow;
        }

        //FRAME
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //SLOT
        final int slotXstart = frameX + 20;
        final int slotYstart = frameY + 20;
        int slotX = slotXstart;
        int slotY = slotYstart;
        int slotSize = gp.tileSize+3;

        //DRAW PLAYER'S ITEM
        for(int i = 0; i < entity.inventory.size(); i++) {
            
            if(entity.inventory.get(i) == entity.currentWeapon 
                || entity.inventory.get(i) == entity.currentShield 
                || entity.inventory.get(i) == entity.currentLight) {
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10 ,10);
                
            }
            g2.drawImage(entity.inventory.get(i).down1, slotX, slotY, null);

            if(entity == gp.player && entity.inventory.get(i).amount > 1) {

                g2.setFont(g2.getFont().deriveFont(32f));
                int amountX;
                int amountY;

                String s = "" + entity.inventory.get(i).amount;
                amountX = getXforAlightToRightText(s, slotX + 44);
                amountY = slotY + gp.tileSize;

                g2.setColor(new Color(60,60,60));
                g2.drawString(s, amountX, amountY);

                g2.setColor(Color.white);
                g2.drawString(s, amountX-3, amountY-3);


            }

            slotX += slotSize;

            if(i == 4 || i == 9 || i == 14) {
                slotX = slotXstart;
                slotY += slotSize;
            }
        }

        //CURSOR
        if(cursor) {
            int cursorX = slotXstart + (slotSize * slotCol);
            int cursorY = slotYstart + (slotSize * slotRow);     
            int cursorWidth = gp.tileSize;
            int cursorHeight = gp.tileSize;

            //DRAW CURSOR
            g2.setColor(Color.white);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);
        
            //DESCRIPTION FRAME
            int dFrameY = frameY + frameHeight;
            int dFrameHeight = gp.tileSize*3;
            

            // DRAW DESCRIPTION TEXT
            int textX = frameX + 20;
            int textY = dFrameY + gp.tileSize;
            g2.setFont(g2.getFont().deriveFont(28F));
            int itemIndex = getItemIndexOnSlot(slotCol, slotRow);
            if(itemIndex < entity.inventory.size()) {
                drawSubWindow(frameX, dFrameY, frameWidth, dFrameHeight);
                for(String line: entity.inventory.get(itemIndex).description.split("\n")){
                    g2.drawString(line, textX, textY);
                    textY += 32;
                }
                textY = gp.tileSize*11;
                g2.drawString("Durabilité : "+ entity.inventory.get(itemIndex).durability, textX, textY);
            }
        }
    }
    public void drawGameOverScreen() {
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 110f));

        text = "GAME OVER";
        g2.setColor(Color.black);        
        x = getXforCentreredText(text);
        y = gp.tileSize*4;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Recommencer";
        x = getXforCentreredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-40, y);
        }

        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Quitter";
        x = getXforCentreredText(text);
        y += 55;
        g2.drawString(text, x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-40, y);
        }
    }
    public void drawOptionsScreen() {
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int frameX = gp.tileSize*6;
        int frameY =  gp.tileSize;
        int frameWidth =  gp.tileSize*8;
        int frameHeight =  gp.tileSize*10;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch(subState) {
            case 0: options_top(frameX, frameY); break;
            case 1: options_fullScreenNotification(frameX, frameY); break;
            case 2: options_control(frameX, frameY); break;
            case 3: options_endGameConfirmation(frameX, frameY); break;
        }
        gp.keyH.enterPressed = false;
    }
    public void options_top(int frameX, int frameY) {

        int textX;
        int textY;

        // TITILE
        String text = "Options";
        textX = getXforCentreredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        // FULL SCREEN ON/OFF
        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Pleine Ecran", textX, textY); 
        if (commandNum == 0){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed) {
                gp.fullScreenOn = !gp.fullScreenOn;
                subState = 1;
            }
            
        }
        // MUSIC
        textY += gp.tileSize;
        g2.drawString("Musique", textX, textY); 
        if (commandNum == 1){
            g2.drawString(">", textX-gp.tileSize/2, textY);
        }
        
        // SOUND EFFECT
        textY += gp.tileSize;
        g2.drawString("Effet", textX, textY); 
        if (commandNum == 2){
            g2.drawString(">", textX-gp.tileSize/2, textY);
        }
        // CONTROL
        textY += gp.tileSize;
        g2.drawString("Contrôle", textX, textY); 
        if (commandNum == 3){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed) {
                subState = 2;
                commandNum = 0;
            }
        }
        // END GAME
        textY += gp.tileSize;
        g2.drawString("Fin de partie", textX, textY); 
        if (commandNum == 4){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed) {
                subState = 3;
                commandNum = 0;
            }
        }
        // BACK
        textY += gp.tileSize*2;
        g2.drawString("Retour", textX, textY);
        if (commandNum == 5){
            g2.drawString(">", textX-gp.tileSize/2, textY);
            if(gp.keyH.enterPressed) {
                gp.gameState = gp.playState;
            }
        } 

        // FULL SCREEN CHECK BOX
        textX = frameX + (int) (gp.tileSize*4.5);
        textY = frameY + gp.tileSize*2-24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn){
            g2.fillRect(textX, textY, 24, 24);
        }

        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);

        int volumeWidth = gp.music.volumeScale * 24;
        g2.fillRect(textX, textY, volumeWidth, 24);

        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);

        volumeWidth = gp.se.volumeScale * 24;
        g2.fillRect(textX, textY, volumeWidth, 24);

        gp.config.saveConfig();
    }
    public void options_fullScreenNotification(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        currentDialogue = "Le changement prendra\neffet après le\nredémarrage du jeu .";

        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }

        textY = frameY + gp.tileSize*9;
        g2.drawString("Retour", textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed) {
                subState = 0;
            }
        }

    }
    public void options_control(int frameX, int frameY) {
        int textX;
        int textY;

        String text = "Contrôle";
        textX = getXforCentreredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Mouvement", textX, textY); textY += gp.tileSize;
        g2.drawString("Confirmé/Attaque", textX, textY); textY += gp.tileSize;
        g2.drawString("Tir/Jet", textX, textY); textY += gp.tileSize;
        g2.drawString("Statistiques", textX, textY); textY += gp.tileSize;
        g2.drawString("Pause", textX, textY); textY += gp.tileSize;
        g2.drawString("Options", textX, textY);

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("ZQSD", textX, textY); textY += gp.tileSize;
        g2.drawString("ENTER", textX, textY); textY += gp.tileSize;
        g2.drawString("F", textX, textY); textY += gp.tileSize;
        g2.drawString("C", textX, textY); textY += gp.tileSize;
        g2.drawString("P", textX, textY); textY += gp.tileSize;
        g2.drawString("ESC", textX, textY);

        textX = frameX + gp.tileSize;
    textY = frameY + gp.tileSize*9;
    g2.drawString("Retour", textX, textY);
    if(commandNum == 0) {
        g2.drawString(">", textX-25, textY);
        if(gp.keyH.enterPressed) {
            subState = 0;
            commandNum = 3;
        }
    }
    }
    public void options_endGameConfirmation(int frameX, int frameY) {
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize;

        currentDialogue = "Quitter le jeu et \nretourné à l'écran titre?";
        for(String line: currentDialogue.split("\n")) {
            g2.drawString(line, textX, textY);
            textY += 40;
        }
        String text = "OUI";
        textX = getXforCentreredText(text);
        textY += gp.tileSize*3;
        g2.drawString(text, textX, textY);
        if(commandNum == 0) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed) {
                subState = 0;
                gp.gameState = gp.titleState;
                gp.resetGame(true);
                gp.stopMusic();
            }
        }
        text = "NON";
        textX = getXforCentreredText(text);
        textY += gp.tileSize;
        g2.drawString(text, textX, textY);
        if(commandNum == 1) {
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed) {
                subState = 0;
                commandNum = 4;
            }
        }
    }
    public void drawTransition() {
        
        counter++;
        g2.setColor(new Color(0,0,0,counter*5));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
        if(counter == 50) {

            counter = 0;
            gp.gameState = gp.playState;
            gp.currentMap = gp.eHandler.tempMap;
            gp.player.worldX = gp.tileSize * gp.eHandler.tempCol;
            gp.player.worldY = gp.tileSize * gp.eHandler.tempRow;
            gp.eHandler.previousEventX = gp.tileSize * gp.eHandler.tempCol;
            gp.eHandler.previousEventY = gp.tileSize * gp.eHandler.tempRow;
            
            gp.changeArea();
        }
    }
    public void drawTradeScreen() {
        switch(subState) {
            case 0: trade_select(); break;
            case 1: trade_buy(); break;
            case 2: trade_sell(); break;
        }
        gp.keyH.enterPressed = false;
    }
    public void trade_select() {
        npc.dialogueSet = 0;
        drawDialogueScreen();
        

        int x = gp.tileSize*15;
        int y = gp.tileSize*4;
        int width = gp.tileSize*3;
        int height = (int) (gp.tileSize*3.5);
        drawSubWindow(x, y, width, height);

        x += gp.tileSize/2;
        y += gp.tileSize;

        g2.drawString("Acheter", x, y);
        if(commandNum == 0) {
            g2.drawString(">", x-12, y);
            if(gp.keyH.enterPressed) {subState = 1;}
        }
        y += gp.tileSize;
        g2.drawString("Vendre", x, y);
        if(commandNum == 1) {
            g2.drawString(">", x-12, y);
            if(gp.keyH.enterPressed) {subState = 2;}
        }
        y += gp.tileSize;
        g2.drawString("Partir", x, y);
        if(commandNum == 2) {
            g2.drawString(">", x-12, y);
            if(gp.keyH.enterPressed) {
                commandNum = 0;
                npc.startDialogue(npc, 1);
            }
                            
        }
    }
    public void trade_buy() {
        drawInventory(gp.player, false);
        drawInventory(npc, true);

        int x = gp.tileSize * 2;
        int y = gp.tileSize * 9;
        int width = gp.tileSize * 6;
        int height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] back", x+24, y+60);

        x = gp.tileSize * 12;
        drawSubWindow(x, y, width, height);
        g2.drawString("Ton argent: "+gp.player.coin, x+24, y+60);

        int itemIndex = getItemIndexOnSlot(npcSlotCol, npcSlotRow);
        if(itemIndex < npc.inventory.size()) {
            x = (int) (gp.tileSize*5.5);
            y = (int) (gp.tileSize*5.5);
            width = (int) (gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = npc.inventory.get(itemIndex).price;
            String text =""+price;
            x = getXforAlightToRightText(text, gp.tileSize*8-20);
            g2.drawString(text, x, y+34);

            if(gp.keyH.enterPressed) {
                if(npc.inventory.get(itemIndex).price > gp.player.coin) {
                    subState = 0;
                    npc.startDialogue(npc, 2);
                }
                else {
                    if(gp.player.canObtainItem(npc.inventory.get(itemIndex))) {
                        gp.player.coin -= npc.inventory.get(itemIndex).price;
                    }
                    else {
                        subState = 0;
                        npc.startDialogue(npc, 3);

                    }
                }
            }
        }
    }
    public void trade_sell() {
        drawInventory(gp.player, true);

        int x;
        int y;
        int width;
        int height;

        x = gp.tileSize * 2;
        y = gp.tileSize * 9;
        width = gp.tileSize * 6;
        height = gp.tileSize * 2;
        drawSubWindow(x, y, width, height);
        g2.drawString("[ESC] back", x+24, y+60);

        x = gp.tileSize * 12;
        drawSubWindow(x, y, width, height);
        g2.drawString("Ton argent: "+gp.player.coin, x+24, y+60);

        int itemIndex = getItemIndexOnSlot(playerSlotCol, playerSlotRow);
        if(itemIndex < gp.player.inventory.size()) {
            x = (int) (gp.tileSize*15.5);
            y = (int) (gp.tileSize*5.5);
            width = (int) (gp.tileSize*2.5);
            height = gp.tileSize;
            drawSubWindow(x, y, width, height);
            g2.drawImage(coin, x+10, y+8, 32, 32, null);

            int price = gp.player.inventory.get(itemIndex).price/2;
            String text =""+price;
            x = getXforAlightToRightText(text, gp.tileSize*18-20);
            g2.drawString(text, x, y+34);

            if(gp.keyH.enterPressed) {
                if(gp.player.inventory.get(itemIndex) == gp.player.currentWeapon ||
                    gp.player.inventory.get(itemIndex) == gp.player.currentShield) {
                        commandNum = 0;
                        subState = 0;
                        npc.startDialogue(npc, 4);

                }
                else {
                    if(gp.player.inventory.get(itemIndex).amount > 1) {
                        gp.player.inventory.get(itemIndex).amount--;
                    }
                    else {
                        gp.player.inventory.remove(itemIndex);
                    }
                    gp.player.coin += price/2;
                }
            }
        }
    }    
    public void drawSleepScreen() {
        counter++;

        if(counter < 120) {
            gp.eManager.lighting.filterAlpha += 0.01f;
            if(gp.eManager.lighting.filterAlpha > 1f) {
                gp.eManager.lighting.filterAlpha = 1f;
            }
        }
        if(counter >= 120) {
            gp.eManager.lighting.filterAlpha -= 0.01f;
            if(gp.eManager.lighting.filterAlpha < 0f) {
                gp.eManager.lighting.filterAlpha = 0f;
                counter = 0;
                gp.eManager.lighting.dayState = gp.eManager.lighting.day;
                gp.eManager.lighting.dayCounter = 0;
                gp.gameState = gp.playState;
                gp.player.getImage();
            }
        }
    }
    public int getItemIndexOnSlot(int slotCol, int slotRow) {
        return slotCol + (slotRow*5);
    }
    public void drawSubWindow(int x, int y, int width, int height){

        Color c = new Color(0,0,0,210);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }
    public int getXforCentreredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth/2 - length/2;
    }
    public int getXforAlightToRightText(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return tailX - length;
    }
}
