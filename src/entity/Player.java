package entity;

import java.awt.*;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.KeyHandler;
import object.*;


@SuppressWarnings("ALL")
public class Player extends Entity {


    final KeyHandler keyH;

    public final int screenX;    
    public final int screenY;
    int standCounter = 0;
    public boolean attackCanceled = false;
    public boolean lightUpdated = false;


    public Player(GamePanel gp, KeyHandler keyH){

        super(gp);

        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle();
        solidArea.x = 8;        
        solidArea.y = 16; 
        solidAreaDefaultX = solidArea.x;  
        solidAreaDefaultY = solidArea.y;         
        solidArea.width = 32;        
        solidArea.height = 32;   


        setDefaultValues();

    }
    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        defaultSpeed = 4;
        speed = defaultSpeed;
        direction = "down";
        
        //PLAYER STATUS
        level = 1;
        maxLife = 6;
        life = maxLife;
        maxMana = 4;
        mana = maxMana;
        ammo = 10;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 500;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        currentLight = null;
        projectile = new OBJ_Fireball(gp);
        attack = getAttack();
        defense = getDefense();

        getImage();
        getAttackImage();
        getGuardImage();
        setItems();
        setDialogue();
    }
    public void setDefaultPositions() {

        gp.currentMap = 0;
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        direction = "down";
    }
    public void setDialogue() {
        dialogues[0][0] = "Tu est est maintenant de \nniveau "+level+" ! Tu es devenu plus fort!";
    }
    public void restoreStatus() {
        life = maxLife;
        mana = maxMana;
        invincible = false;
        transparent = false;
        attacking = false;
        guarding = false;
        knockBack = false;
        lightUpdated = true;
        speed = defaultSpeed;
    }
    public void setItems() {
        inventory.clear();
        inventory.add(currentWeapon);
        inventory.add(currentShield);
    }
    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        motion1_duration = currentWeapon.motion1_duration; 
        motion2_duration = currentWeapon.motion2_duration;
        return attack = strength * currentWeapon.attackValue;
    }
    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }
    public int getCurrentWeaponSlot() {
        int currentWeaponSlot = 0;
        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i) == currentWeapon) {
                currentWeaponSlot = i;
            }
        }
        return currentWeaponSlot;
    }
    public int getCurrentShieldSlot() {
        int currentShieldSlot = 0;
        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i) == currentShield) {
                currentShieldSlot = i;
            }
        }
        return currentShieldSlot;
    }
    public void getImage() {

            up1 = setup("player/boy_up_1", gp.tileSize, gp.tileSize);
            up2 = setup("player/boy_up_2", gp.tileSize, gp.tileSize);
            down1 = setup("player/boy_down_1", gp.tileSize, gp.tileSize);
            down2 = setup("player/boy_down_2", gp.tileSize, gp.tileSize);
            left1 = setup("player/boy_left_1", gp.tileSize, gp.tileSize);
            left2 = setup("player/boy_left_2", gp.tileSize, gp.tileSize);
            right1 = setup("player/boy_right_1", gp.tileSize, gp.tileSize);
            right2 = setup("player/boy_right_2", gp.tileSize, gp.tileSize);
       
    }
    public void getSleepingImage(BufferedImage image) {
        up1 = image; up2 = image; down1 = image; down2 = image; left1 = image; left2 = image; right1 = image; right2 = image;
   
    }
    public void getAttackImage() {

        if(currentWeapon.type == type_sword) {
            attackUp1 = setup("player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_axe) {
            attackUp1 = setup("player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
        }
        if(currentWeapon.type == type_pickaxe) {
            attackUp1 = setup("player/boy_pick_up_1", gp.tileSize, gp.tileSize*2);
            attackUp2 = setup("player/boy_pick_up_2", gp.tileSize, gp.tileSize*2);
            attackDown1 = setup("player/boy_pick_down_1", gp.tileSize, gp.tileSize*2);
            attackDown2 = setup("player/boy_pick_down_2", gp.tileSize, gp.tileSize*2);
            attackLeft1 = setup("player/boy_pick_left_1", gp.tileSize*2, gp.tileSize);
            attackLeft2 = setup("player/boy_pick_left_2", gp.tileSize*2, gp.tileSize);
            attackRight1 = setup("player/boy_pick_right_1", gp.tileSize*2, gp.tileSize);
            attackRight2 = setup("player/boy_pick_right_2", gp.tileSize*2, gp.tileSize);
        }
    }
    public void getGuardImage() {
        guardUp = setup("player/boy_guard_up", gp.tileSize, gp.tileSize);
        guardDown = setup("player/boy_guard_down", gp.tileSize, gp.tileSize);
        guardLeft = setup("player/boy_guard_left", gp.tileSize, gp.tileSize);
        guardRight = setup("player/boy_guard_right", gp.tileSize, gp.tileSize);

    }
    public void update(){
        
        if(knockBack) {
            
            collisionOn = false;
            gp.cChecker.checkTile(this);
            gp.cChecker.checkObject(this);
            gp.cChecker.checkEntity(this, gp.npc);
            gp.cChecker.checkEntity(this, gp.monster);
            gp.cChecker.checkEntity(this, gp.iTile);

            //noinspection DuplicateCondition
            if(collisionOn) {


                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }
            else //noinspection DuplicateCondition
            {
            switch(knockBackDirection) {
                case "up": worldY -= speed; break;
                case "down": worldY  += speed; break;
                case "left": worldX  -= speed; break;
                case "right": worldX  += speed; break;
            }
        }
            knockBackCounter++;
            if(knockBackCounter == 10) {
                knockBackCounter = 0;
                knockBack = false;
                speed = defaultSpeed;
            }

        }
        else if(attacking){
            attacking();
        }
        else if (keyH.spacePressed){
            guarding = true;
            guardCounter++;
        }
        else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed 
        || keyH.rightPressed || keyH.enterPressed){

            if (keyH.upPressed){
                direction = "up";
            }
            else if (keyH.downPressed){
                direction = "down";
            }
            else if (keyH.leftPressed){
                direction = "left";
            }
            else if(keyH.rightPressed){
                direction = "right";
            }

            //CHECK TILE COLLISION
            collisionOn = false;
            gp.cChecker.checkTile(this);

            //CHECK OBJECT COLLISION
            int objIndex = gp.cChecker.checkObject(this);
            pickUpObject(objIndex);

            //CHECK NPC COLLISION
            int npcindex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcindex);

            //CHECK MONSTER COLLISION
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monster);
            contactMonster(monsterIndex);

            //CHECK INTERACTIVE TILE COLLISION
            gp.cChecker.checkEntity(this, gp.iTile);

            
            //CHECK EVENT
            gp.eHandler.checkEvent();
            
            //IF COLLISION IS FALSE? PLAYER CAN MOVE
            if(!collisionOn && !keyH.enterPressed) {
                switch(direction) {
                case "up": worldY -= speed; break; 
                case "down": worldY  += speed; break; 
                case "left": worldX  -= speed; break; 
                case "right": worldX  += speed; break;                     
                }
            }
            if(keyH.enterPressed && !attackCanceled){
                gp.SE("swingweapon.wav");
                attacking = true;
                spriteCounter = 0;
                currentWeapon.durability--;
            }

            attackCanceled = false;
            gp.keyH.enterPressed = false;
            guarding = false;
            guardCounter = 0;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                }else if(spriteNum == 2) {
                    spriteNum = 1; 
                }
                spriteCounter = 0;
            }
        }
        else {
            standCounter++;
            if(standCounter == 20) {
                spriteNum = 1;
                standCounter = 0;
            }
            guarding = false;
            guardCounter = 0;
        }
        if(gp.keyH.shotPressed && !projectile.alive && shotAvailableCounter == 30 && projectile.haveRessource(this)) {
            projectile.set(worldX, worldY, direction, true, this);
            
            // SUBTRACT THE COST (MANA)
            projectile.subtractRessource(this);

            for(int i = 0; i < gp.projectile[gp.currentMap].length; i++) {
                if(gp.projectile[gp.currentMap][i] == null) { 
                    gp.projectile[gp.currentMap][i] = projectile;
                    break;
                }
            }
            gp.SE("burning.wav");
            shotAvailableCounter = 0;

        }
        if(invincible){
            invincibleCounter++;
            if(invincibleCounter > 60) {
                invincible = false;
                transparent = false;
                invincibleCounter = 0;
            }
        }if(shotAvailableCounter < 30) {
            shotAvailableCounter++;
        }
        if(life > maxLife) {
            life = maxLife;
        }
        if(mana > maxMana) {
            mana = maxMana;
        }
        if(!keyH.godModeOn) {
            if(life <= 0) {
                gp.gameState = gp.gameOverState;
                gp.ui.commandNum =  -1;
                gp.stopMusic();
                gp.SE("gameover.wav");
            }
        }
    }      
    public void pickUpObject(int i){

        if(i != 999){
            // PICKUP ONLY ITEMS
            if(gp.obj[gp.currentMap][i].type == type_pickupOnly) {
                gp.obj[gp.currentMap][i].use(this);
                gp.obj[gp.currentMap][i] = null;
            }
            //OBSTACLE
            else if(gp.obj[gp.currentMap][i].type == type_obstacle){
                if(keyH.enterPressed) {
                    attackCanceled = true;
                    gp.obj[gp.currentMap][i].interact();
                }
            }
            //INVENTORY ITEMS
            else {
                String text;
                if(canObtainItem(gp.obj[gp.currentMap][i])) {
                    gp.SE("coin.wav");
                    text = gp.obj[gp.currentMap][i].name+" obtenu !";
                    gp.obj[gp.currentMap][i] = null;
                }
                else {
                    text = "Tu ne peux plus porter autre choses";
                }
                gp.ui.addMessage(text);
            }
        }
    }
    public void interactNPC(int i) {
        if (i != 999){
            if(gp.keyH.enterPressed){
                attackCanceled = true;
                gp.npc[gp.currentMap][i].speak();
            }
            gp.npc[gp.currentMap][i].move(direction);

        }
    }
    public void contactMonster(int i) {
        if(i != 999){
            if(!invincible && !gp.monster[gp.currentMap][i].dying){
                gp.SE("receivedamage.wav");
                int damage = gp.monster[gp.currentMap][i].attack - defense;
                if(damage < 1){
                    damage = 1;
                }
                life -= damage;
                invincible = true;
                transparent = true;
            }
            

        }
    }
    public void damageMonster(int i, Entity attacker, int attack, int knockBackPower){

        if(i != 999){
            if(!gp.monster[gp.currentMap][i].invincible) {
                gp.SE("hitmonster.wav");

                if(knockBackPower > 0) {
                    setKnockBack(gp.monster[gp.currentMap][i], attacker, knockBackPower);
                }
                if(gp.monster[gp.currentMap][i].offBalance) {
                    attack *= 5;
                }

                int damage = attack - gp.monster[gp.currentMap][i].defense;
                if(damage<0){
                    damage = 0;
                }

                gp.monster[gp.currentMap][i].life -= damage;
                gp.ui.addMessage(damage + " de dégats!");

                gp.monster[gp.currentMap][i].invincible = true;
                gp.monster[gp.currentMap][i].damageReaction();
                if(gp.monster[gp.currentMap][i].life <= 0) {
                    gp.monster[gp.currentMap][i].dying = true;
                    gp.ui.addMessage("Le "+gp.monster[gp.currentMap][i].name + " a été tué!");
                    gp.ui.addMessage("Tu gagne "+gp.monster[gp.currentMap][i].exp + " points exp !");
                    exp += gp.monster[gp.currentMap][i].exp;
                    checkLevelUp();
                }
           }
        }
    }            
    public void damageInteractiveTile(int i) {
        if(i != 999 && gp.iTile[gp.currentMap][i].destructible &&
                gp.iTile[gp.currentMap][i].isCorrectItem(this) && !gp.iTile[gp.currentMap][i].invincible){
            gp.iTile[gp.currentMap][i].playSE();
            gp.iTile[gp.currentMap][i].life--;
            gp.iTile[gp.currentMap][i].invincible = true;
            generateParticle(gp.iTile[gp.currentMap][i], gp.iTile[gp.currentMap][i]);
            if(gp.iTile[gp.currentMap][i].life == 0) {
                gp.iTile[gp.currentMap][i].checkDrop();
                gp.iTile[gp.currentMap][i] = gp.iTile[gp.currentMap][i].getDestroyedForm();
            }

            
        }
    }
    public void damageProjectile(int i) {
        if(i != 999 ){
            Entity projectile = gp.projectile[gp.currentMap][i];
            projectile.alive = false;
            generateParticle(projectile, projectile);
        }
    }
    public void checkLevelUp() {
        //while 
        if(exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp*2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();
            gp.SE("levelup.wav");
            gp.gameState = gp.dialogueState;
            setDialogue();
            startDialogue(this, 0);
        }
    }
    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot(gp.ui.playerSlotCol, gp.ui.playerSlotRow);

        if(itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if(selectedItem.type == type_sword || selectedItem.type == type_axe || selectedItem.type == type_pickaxe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getAttackImage();
            }
            if(selectedItem.type == type_shield) {
                currentShield = selectedItem;
                attack = getDefense();
            }
            if(selectedItem.type == type_light) {
                if(currentLight == selectedItem){
                    currentLight = null;
                }
                else {
                    currentLight = selectedItem;
                }
                lightUpdated = true;
            }
            if(selectedItem.type == type_consumable) {
                if(selectedItem.use(this)) {
                    if(selectedItem.amount > 1) {
                        selectedItem.amount--;
                    }
                    else {
                        inventory.remove(itemIndex);
                    }
                }
            }
        }
    }
    public int searchItemInventory(String itemName) {
        int itemIndex = 999;

        for(int i = 0; i < inventory.size(); i++) {
            if(inventory.get(i).name.equals(itemName)) {
                itemIndex = i;
                break;
            }
        }
        return itemIndex;
    }
    public boolean canObtainItem(Entity item) {
        boolean canObtain = false;

        Entity newItem = gp.eGenerator.getObject(item.name);

        if(newItem.stackable) {
            int index = searchItemInventory(newItem.name);

            if(index != 999) {
                inventory.get(index).amount++;
                canObtain = true;
            }
            else {
                if(inventory.size() != maxInventorySize) {
                    inventory.add(newItem);
                    canObtain = true;
                }
            }
        }
        else {
            if(inventory.size() != maxInventorySize) {
                inventory.add(newItem);
                canObtain = true;
            }           
        }
        return canObtain;
    }
    public void draw(Graphics2D g2) {

        BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;
       
       switch(direction) {
        case "up":
            if(!attacking){
                if (spriteNum == 1){image = up1;}
                if (spriteNum == 2){image = up2;}
            }
            if(attacking){
                tempScreenY = screenY - gp.tileSize;
                if(spriteNum == 1) {image = attackUp1;}
                if(spriteNum == 2) {image = attackUp2;}
            }
            if(guarding) {
                image = guardUp;
            }
            break;
        case "down":
            if(!attacking){
                if (spriteNum == 1){image = down1;}
                if (spriteNum == 2){image = down2;}
            }
            if(attacking){
                if(spriteNum == 1) {image = attackDown1;}
                if(spriteNum == 2) {image = attackDown2;}
            }
            if(guarding) {
                image = guardDown;
            }
            break;
        case "left":
            if(!attacking){
                if (spriteNum == 1){image = left1;}
                if (spriteNum == 2){image = left2;}
            }
            if(attacking){
                tempScreenX = screenX - gp.tileSize;
                if(spriteNum == 1) {image = attackLeft1;}
                if(spriteNum == 2) {image = attackLeft2;}
            }
            if(guarding) {
                image = guardLeft;
            }
            break;
        case "right":
            if(!attacking){
                if (spriteNum == 1){image = right1;}
                if (spriteNum == 2){image = right2;}
            }
            if(attacking){
                if(spriteNum == 1) {image = attackRight1;}
                if(spriteNum == 2) {image = attackRight2;}
            }
            if(guarding) {
                image = guardRight;
            }
            break;
       }

       if(transparent) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
       } 
        g2.drawImage(image, tempScreenX, tempScreenY, null);

        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }
}
