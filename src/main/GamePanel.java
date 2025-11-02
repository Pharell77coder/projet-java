package main;

import entity.Entity;
import entity.Player;
import enviroment.EnvironmentManager;
import tile.Map;
import tile.TileManager;
import tile_interactive.InteractiveTile;

import javax.swing.*;

import ai.PathFinder;
import data.SaveLoad;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


@SuppressWarnings("ALL")
public class GamePanel extends JPanel implements Runnable{

    // SCREEN SETTINGS
    final int originalTileSize = 16; // 16x16 tile
    final int scale = 3; // 

    public final int tileSize = originalTileSize * scale; // 48x48 tile
    public final int maxScreenCol = 20;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol; //768px
    public final int screenHeight = tileSize * maxScreenRow; // 576px

    // WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int maxMap = 10;
    public int currentMap = 0;

    //FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    // FPS 
    public int drawFps = 0;
    final int FPS = 60;//1000000000//240//144

    //SYSTEM
    public final TileManager tileM = new TileManager(this);
    public final KeyHandler keyH = new KeyHandler(this);
    public final UI ui = new UI(this);
    public final EventHandler eHandler = new EventHandler(this);
    public final CollisionChecker cChecker = new CollisionChecker(this);
    public final AssetSetter aSetter = new AssetSetter(this);
    final Sound music = new Sound();
    final Sound se = new Sound();
    final Config config = new Config(this);
    public final PathFinder pFinder = new PathFinder(this);
    final EnvironmentManager eManager = new EnvironmentManager(this);
    final Map map = new Map(this);
    final SaveLoad saveLoad = new SaveLoad(this);
    public final EntityGenerator eGenerator = new EntityGenerator(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    public final Player player = new Player(this,keyH);
    public final Entity[][] obj = new Entity[maxMap][20];
    public final Entity[][] npc = new Entity[maxMap][10];
    public final Entity[][] monster = new Entity[maxMap][20];
    public final InteractiveTile[][] iTile = new InteractiveTile[maxMap][50];
    public final Entity[][] projectile = new Entity[maxMap][20];
    //public ArrayList<Entity> projectileList = new ArrayList<>();    
    public final ArrayList<Entity> particleList = new ArrayList<>();
    final ArrayList<Entity> entityList = new ArrayList<>();

    //GAME STATE
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;
    public final int optionsState = 5;
    public final int gameOverState = 6;
    public final int transitionState = 7;
    public final int tradeState = 8;
    public final int sleepState = 9;
    public final int mapState = 10;

    // AREA
    public int currentArea;
    public int nextArea;
    public final int outside = 50;
    public final int indoor = 51;
    public final int dungeon = 52;


    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    public void setupGame() {
        aSetter.setObject();
        aSetter.setNPC();
        aSetter.setMonster();
        aSetter.setInteractiveTile();
        eManager.setup();
        gameState = titleState;
        //currentArea = outside;
        currentArea = dungeon;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D)tempScreen.getGraphics();
        if(fullScreenOn) {
            setFullScreen();
        }
    }
    public void resetGame(boolean restart) {

        currentArea = outside;
        player.setDefaultPositions();
        player.restoreStatus();
        player.resetCounter();
        aSetter.setNPC();
        aSetter.setMonster();

        if(restart){
            player.setDefaultValues();
            aSetter.setObject();
            aSetter.setInteractiveTile();
            eManager.lighting.resetDay();
        }
    }
    public void setFullScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();

    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    
    @Override
    public void run() {
        double drawInterval = 1000000000F / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta > 1){
            update();
            drawToTempScreen();
            drawToScreen();
            delta--;
            drawCount++;
        }
        if (timer >= 1000000000) {
            drawFps = drawCount;
            drawCount = 0;
            timer = 0;
            }
        }
    }
    public void update() {
        if(gameState == playState){
            //PLAYER
            player.update();

            //NPC
            for (Entity entity : npc[currentMap]) {
                if (entity != null) {
                    entity.update();
                }
            }

            //MONSTER
            for(int i = 0; i < monster[currentMap].length; i++) {
                if(monster[currentMap][i] != null) {
                    if(monster[currentMap][i].alive && !monster[currentMap][i].dying) { 
                        monster[currentMap][i].update();
                    }
                    if(!monster[currentMap][i].alive) { 
                        monster[currentMap][i].checkDrop();
                        monster[currentMap][i] = null;
                    }

                }
            }

            //PROJECTILE
            for(int i = 0; i < projectile[currentMap].length; i++) {
                if(projectile[currentMap][i] != null) {
                    if(projectile[currentMap][i].alive) { 
                        projectile[currentMap][i].update();
                    }
                    if(!projectile[currentMap][i].alive) { 
                        projectile[currentMap][i] = null;
                    }

                }
            }

            //PARTICULES
            for(int i = 0; i < particleList.size(); i++) {
                if(particleList.get(i) != null) {
                    if(particleList.get(i).alive) { 
                        particleList.get(i).update();
                    }
                    if(!particleList.get(i).alive) { 
                        particleList.remove(i);
                    }

                }
            }

            //INTERACTIVE TILE
            for(int i = 0; i < iTile[currentMap].length; i++) {
                if(iTile[currentMap][i] != null) {
                    iTile[currentMap][i].update();
                }
            }
            eManager.update();
        }
        // nothing
    }
    public void drawToTempScreen() {

                //DEBUG
                long drawStart = 0;
                if (keyH.checkDrawTime){
                 drawStart = System.nanoTime();   
                }

                //TITLE SCREEN
                if (gameState == titleState) {
                    ui.draw(g2);
                }

                //MAP SCREEN
                else if (gameState == mapState) {
                    map.drawFullMapScreen(g2);
                }

                //OTHERS
                else {
        
                    //TILE
                    tileM.draw(g2);
        
                    //INTERACTIVE TILE
                    for(int i = 0; i < iTile[currentMap].length; i++) {
                        if(iTile[currentMap][i] != null) {
                            iTile[currentMap][i].draw(g2);
                        }
                    }
        
                    //ADD ENTITY TO THE LIST
                    entityList.add(player);
        
                    for (Entity entity : npc[currentMap]) {
                        if (entity != null) {
                            entityList.add(entity);
                        }
                    }
                    for (Entity entity : obj[currentMap]) {
                        if (entity != null) {
                            entityList.add(entity);
                        }
                    }
                    for (Entity entity : monster[currentMap]) {
                        if (entity != null) {
                            entityList.add(entity);
                        }
                    }
                    for (Entity entity : projectile[currentMap]) {
                        if (entity != null) {
                            entityList.add(entity);
                        }
                    }
                    for (Entity entity : particleList) {
                        if (entity != null) {
                            entityList.add(entity);
                        }
                    }
        
                    //SORT
                    entityList.sort((e1, e2) -> Integer.compare(e1.worldY, e2.worldY));
        
                    //DRAW ENTITY
                    for (Entity entity : entityList) {
                        entity.draw(g2);
                    }
        
                    //EMPTY ENTITY LIST
                    entityList.clear();
                    
                    //ENVIRONMENT
                    eManager.draw(g2);

                    //MINI MAP
                    map.drawMiniMap(g2);
                    
                    //UI
                    ui.draw(g2);
                }
                //DEBUG
                if (keyH.checkDrawTime){
                    long drawEnd = System.nanoTime();
                    long passed = drawEnd - drawStart;
                    g2.setFont(new Font("Arial", Font.PLAIN,20));
                    g2.setColor(Color.white);
                    int x = 10;
                    int y = 400;
                    int lineHeight = 20;

                    g2.drawString("WorldX : "+player.worldX, x, y); y += lineHeight;
                    g2.drawString("WorldY : "+player.worldY, x, y); y += lineHeight;
                    g2.drawString("Col : "+player.worldX/tileSize+ player.solidArea.x, x, y); y += lineHeight;
                    g2.drawString("Row : "+player.worldX/tileSize+ player.solidArea.x, x, y); y += lineHeight;
                    g2.drawString("Draw Time : "+passed, x, y); y += lineHeight;
                    g2.drawString("FPS : "+ drawFps, x, y); y += lineHeight;
                    g2.drawString("God Mode : "+ keyH.godModeOn, x, y);

                    // AttackArea
                    int tempScreenX;
                    int tempScreenY;
                   
                    tempScreenX = player.screenX + player.solidArea.x;
                    tempScreenY = player.screenY + player.solidArea.y;		
                    switch(player.direction) {
                    case "up": tempScreenY = player.screenY - player.attackArea.height; break;
                    case "down": tempScreenY = player.screenY + tileSize; break; 
                    case "left": tempScreenX = player.screenX - player.attackArea.width; break;
                    case "right": tempScreenX = player.screenX + tileSize; break;
                    }		
                    g2.setColor(Color.red);
                    g2.setStroke(new BasicStroke(1));
                    g2.drawRect(tempScreenX, tempScreenY, player.attackArea.width, player.attackArea.height);
                }
    }
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }
    public void playMusic(String path) {
        music.setFile(path);
        music.loop();
        music.play();
        
    }    
    public void stopMusic() {
        music.stop();
    }
    public void SE(String path) {
        se.setFile(path);
        se.play();
    }
    public void changeArea() {
        if(nextArea != currentArea){
            stopMusic();
            SE("stairs.wav");
            if(nextArea == outside){
                playMusic("BlueBoyAdventure.wav");
            }
            else if(nextArea == indoor){
                playMusic("Merchant.wav");

            }
            else if(nextArea == dungeon){
                playMusic("Dungeon.wav");
            }
            aSetter.setNPC();
        }
        currentArea = nextArea;
        aSetter.setMonster();
    }
}
