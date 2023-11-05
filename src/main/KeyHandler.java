package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    final GamePanel gp;

    public boolean upPressed, downPressed,leftPressed, rightPressed, enterPressed, shotPressed, spacePressed;

    //DEBUG
    public boolean checkDrawTime = false;
    public boolean godModeOn = false;

    public KeyHandler(GamePanel gp){
        this.gp = gp;

    }
    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        //TITLE STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        }

        //PLAY STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        }
        
        //PAUSE STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        }

        //DIALOGUE STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        }

        //CHARACTER STATE
        else if(gp.gameState == gp.characterState){
            characterState(code);
        }

        //OPTION STATE
        else if(gp.gameState == gp.optionsState){
            optionsState(code);
        }

        //GAME OVER STATE
        else if(gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
        //TRADE STATE
        else if(gp.gameState == gp.tradeState){
            tradeState(code);
        }
        //MAP STATE
        else if(gp.gameState == gp.mapState){
            mapState(code);
        }

    }
    public void titleState(int code) {
        if(gp.ui.titleScreenState == 0) {
            if (code == KeyEvent.VK_Z) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    //gp.ui.titleScreenState = 1;
                    gp.gameState = gp.playState;
                    gp.playMusic("BlueBoyAdventure.wav");


                }if (gp.ui.commandNum == 1) {
                    gp.saveLoad.load();
                    gp.gameState = gp.playState;
                    gp.playMusic("BlueBoyAdventure.wav");

                }if (gp.ui.commandNum == 2) {
                    System.exit(0);

                }
            }  
        }else if(gp.ui.titleScreenState == 1) {
            if (code == KeyEvent.VK_Z) {
                gp.ui.commandNum--;
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 3;
                }
            }
            if (code == KeyEvent.VK_S) {
                gp.ui.commandNum++;
                if(gp.ui.commandNum > 3) {
                    gp.ui.commandNum = 0;
                }
            }
            if (code == KeyEvent.VK_ENTER) {
                if (gp.ui.commandNum == 0) {
                    gp.gameState = gp.playState;

                }if (gp.ui.commandNum == 1) {
                    gp.gameState = gp.playState;

                }if (gp.ui.commandNum == 2) {
                    gp.gameState = gp.playState;

                }if (gp.ui.commandNum == 3) {
                    gp.ui.titleScreenState = 0;
                }
            } 
        }
    }   
    public void playState(int code) {
        if (code == KeyEvent.VK_Z) {
            upPressed = true;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = true;
        }
        if (code == KeyEvent.VK_Q) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.pauseState;
        } 
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        } 
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if (code == KeyEvent.VK_F) {
            shotPressed = true;
        }  
        if (code == KeyEvent.VK_ESCAPE) {
            gp.gameState = gp.optionsState;
        } 
        if (code == KeyEvent.VK_M) {
            gp.gameState = gp.mapState;
        }  
        if (code == KeyEvent.VK_X) {
            gp.map.miniMapOn = !gp.map.miniMapOn;
        }
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = true;
        }      

        //DEBUG
        if (code == KeyEvent.VK_T) {
            checkDrawTime = !checkDrawTime;
        }
        if(code == KeyEvent.VK_R){
            switch(gp.currentMap){
                case 0:  gp.tileM.loadMap("worldmap.txt", 0); break;
                case 1:  gp.tileM.loadMap("indoor01.txt", 1); break;
                case 2:  gp.tileM.loadMap("dungeon01.txt", 1); break;
                case 3:  gp.tileM.loadMap("dungeon02.txt", 1); break;
            }  
        }
        if (code == KeyEvent.VK_G) {
            godModeOn = !godModeOn;
        }
    }   
    public void pauseState(int code) {
        if (code == KeyEvent.VK_P) {
            gp.gameState = gp.playState; 
        }
    }    
    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = true;  
        }
    }
    public void characterState(int code) {
        if(code == KeyEvent.VK_C){
            gp.gameState = gp.playState; 
        }
        if(code == KeyEvent.VK_ENTER) {
            gp.player.selectItem();
        }        
        playerInventory(code);
    }
    public void optionsState(int code) {
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState; 
        }
        if(code == KeyEvent.VK_ENTER){
            enterPressed = true; 
        }
        int maxCommandNum = 0;
        switch(gp.ui.subState) {
            case 0: maxCommandNum = 5; break;
            case 3: maxCommandNum = 1; break;

        }
        if (code == KeyEvent.VK_Z) {
            gp.ui.commandNum--;
            gp.SE("cursor.wav"); 
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.SE("cursor.wav"); 
            if(gp.ui.commandNum > maxCommandNum) {
                gp.ui.commandNum = 0;
            }
        }
        if(code == KeyEvent.VK_Q){
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale > 0) {
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.SE("cursor.wav"); 

                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale > 0) {
                    gp.se.volumeScale--;
                    gp.se.checkVolume();
                    gp.SE("cursor.wav"); 

                }
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.subState == 0) {
                if(gp.ui.commandNum == 1 && gp.music.volumeScale < 5) {
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.SE("cursor.wav"); 

                }
                if(gp.ui.commandNum == 2 && gp.se.volumeScale < 5) {
                    gp.se.volumeScale++;
                    gp.se.checkVolume();
                    gp.SE("cursor.wav"); 

                }
            }
        }
    }
    public void gameOverState(int code) {
        if (code == KeyEvent.VK_Z) {
            gp.ui.commandNum--;
            gp.SE("cursor.wav"); 
            if(gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1;
            }
        }
        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            gp.SE("cursor.wav"); 
            if(gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.playState;
                gp.resetGame(false);
                gp.playMusic("BlueBoyAdventure.wav");// Lance la Sicmu
            }else if(gp.ui.commandNum == 1) {
                gp.gameState = gp.titleState;
                gp.resetGame(true);
            }
        }  
    }
    public void tradeState(int code) {
        if(code == KeyEvent.VK_ENTER) {
            enterPressed = true;
        }
        if(gp.ui.subState == 0) {
            if(code == KeyEvent.VK_Z) {
                gp.ui.commandNum--; 
                if(gp.ui.commandNum < 0) {
                    gp.ui.commandNum = 2;
            }
            gp.SE("cursor.wav");
            }
            if(code == KeyEvent.VK_S) {
                gp.ui.commandNum++; 
                if(gp.ui.commandNum > 2) {
                    gp.ui.commandNum = 0;
                }
                gp.SE("cursor.wav");
            }
        }
        if(gp.ui.subState == 1) {
            npcInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
        if(gp.ui.subState == 2) {
            playerInventory(code);
            if(code == KeyEvent.VK_ESCAPE) {
                gp.ui.subState = 0;
            }
        }
    }
    public void mapState(int code) {
        if(code == KeyEvent.VK_M) {
            gp.gameState = gp.playState;
        }
    }
    public void playerInventory(int code) {
        if(code == KeyEvent.VK_Z){
            if(gp.ui.playerSlotRow != 0){
                gp.ui.playerSlotRow--;
                gp.SE("cursor.wav"); 
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.playerSlotRow != 3){
                gp.ui.playerSlotRow++;
                gp.SE("cursor.wav"); 
            }
        }
        if(code == KeyEvent.VK_Q){
            if(gp.ui.playerSlotCol != 0){
                gp.ui.playerSlotCol--;
                gp.SE("cursor.wav"); 
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.playerSlotCol != 4){
                gp.ui.playerSlotCol++;
                gp.SE("cursor.wav"); 
            } 
        }
    }
    public void npcInventory(int code) {
        if(code == KeyEvent.VK_Z){
            if(gp.ui.npcSlotRow != 0){
                gp.ui.npcSlotRow--;
                gp.SE("cursor.wav"); 
            }
        }
        if(code == KeyEvent.VK_S){
            if(gp.ui.npcSlotRow != 3){
                gp.ui.npcSlotRow++;
                gp.SE("cursor.wav"); 
            }
        }
        if(code == KeyEvent.VK_Q){
            if(gp.ui.npcSlotCol != 0){
                gp.ui.npcSlotCol--;
                gp.SE("cursor.wav"); 
            }
        }
        if(code == KeyEvent.VK_D){
            if(gp.ui.npcSlotCol != 4){
                gp.ui.npcSlotCol++;
                gp.SE("cursor.wav"); 
            } 
        }
    }
    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_Z) {
            upPressed = false;
        }
        if (code == KeyEvent.VK_S) {
            downPressed = false;
        }
        if (code == KeyEvent.VK_Q) {
            leftPressed = false;
        }
        if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }
        if (code == KeyEvent.VK_F) {
            shotPressed = false;
        }
        if (code == KeyEvent.VK_ENTER) {
            enterPressed = false;
        }      
        if (code == KeyEvent.VK_SPACE) {
            spacePressed = false;
        }      
 


    }
}
