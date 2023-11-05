package data;

import main.GamePanel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class SaveLoad {
    
    final GamePanel gp;
    public SaveLoad(GamePanel gp) {
        this.gp = gp;
    }

    public void save() {
        try{
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("save.dat"));
        
            DataStorage ds = new DataStorage();

            //PLAYER STATS
            ds.level = gp.player.level;
            ds.maxLife = gp.player.maxLife;
            ds.life = gp.player.life;
            ds.maxMana = gp.player.maxMana;
            ds.mana = gp.player.mana;
            ds.strength = gp.player.strength;
            ds.dexterity = gp.player.dexterity;
            ds.exp = gp.player.exp;
            ds.nextLevelExp = gp.player.nextLevelExp;
            ds.coin = gp.player.coin;

            //PLAYER INVENTORY 
            for(int i = 0; i < gp.player.inventory.size(); i++) {
                ds.itemNames.add(gp.player.inventory.get(i).name);
                ds.itemAmounts.add(gp.player.inventory.get(i).amount);
            }    

            //PLAYER EQUIPEMENT
            ds.currentWeaponSlot = gp.player.getCurrentWeaponSlot();
            ds.currentShieldSlot = gp.player.getCurrentShieldSlot();  

            //OBJECTS ON MAP       
            int i = 1;         
            ds.mapObjectNames = new String[gp.maxMap][gp.obj[i].length];
            ds.mapObjectWorldX = new int[gp.maxMap][gp.obj[i].length];
            ds.mapObjectWorldY = new int[gp.maxMap][gp.obj[i].length];
            ds.mapObjectLootNames = new String[gp.maxMap][gp.obj[i].length];
            ds.mapObjectOpened = new boolean[gp.maxMap][gp.obj[i].length];
            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for(int j = 0; j < gp.obj[i].length; j++) {
                    if(gp.obj[mapNum][j] == null) {
                        ds.mapObjectNames[mapNum][j] = "NA";
                    }
                    else {
                        ds.mapObjectNames[mapNum][j] = gp.obj[mapNum][j].name;
                        ds.mapObjectWorldX[mapNum][j] = gp.obj[mapNum][j].worldX;
                        ds.mapObjectWorldY[mapNum][j] = gp.obj[mapNum][j].worldY;
                        if(gp.obj[mapNum][j].loot != null) {
                            ds.mapObjectLootNames[mapNum][j] = gp.obj[mapNum][j].loot.name;
                        }
                        ds.mapObjectOpened[mapNum][j] = gp.obj[mapNum][j].opened;
                    }
                }
            }

            //WRITE THE DATATSTORAGE
            oos.writeObject(ds);
        }
        catch(Exception e){
            System.out.println("Save Exception!");
        }
       
    }
    public void load() {
        try{
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("save.dat"));
        
            DataStorage ds = (DataStorage)ois.readObject();

            //PLAYER STATS
            gp.player.level = ds.level;
            gp.player.maxLife = ds.maxLife;
            gp.player.life = ds.life;
            gp.player.maxMana = ds.maxMana;
            gp.player.mana = ds.mana;
            gp.player.strength = ds.strength;
            gp.player.dexterity = ds.dexterity;
            gp.player.exp = ds.exp;
            gp.player.nextLevelExp = ds.nextLevelExp;
            gp.player.coin = ds.coin;

            //PLAYER INVENTORY 
            gp.player.inventory.clear();
            for(int i = 0; i < ds.itemNames.size(); i++) {
                gp.player.inventory.add(gp.eGenerator.getObject(ds.itemNames.get(i)));
                gp.player.inventory.get(i).amount = ds.itemAmounts.get(i);
            } 
            //PLAYER EQUIPEMENT
            gp.player.currentWeapon = gp.player.inventory.get(ds.currentWeaponSlot);
            gp.player.currentShield = gp.player.inventory.get(ds.currentShieldSlot);
            gp.player.getAttack();
            gp.player.getDefense();
            gp.player.getAttackImage();

            // OBJECTS ON MAP
            int i = 1;
            for(int mapNum = 0; mapNum < gp.maxMap; mapNum++) {
                for(int j = 0; j < gp.obj[i].length; j++) {
                    if(ds.mapObjectNames[mapNum][j].equals("NA")) {
                        gp.obj[mapNum][j] = null;
                    }
                    else {
                        gp.obj[mapNum][j] = gp.eGenerator.getObject(ds.mapObjectNames[mapNum][j]);
                        gp.obj[mapNum][j].worldX = ds.mapObjectWorldX[mapNum][j];
                        gp.obj[mapNum][j].worldY = ds.mapObjectWorldY[mapNum][j];
                        if(ds.mapObjectLootNames[mapNum][j] != null) {
                            gp.obj[mapNum][j].setLoot(gp.eGenerator.getObject(ds.mapObjectLootNames[mapNum][j]));
                        }
                        gp.obj[mapNum][j].opened = ds.mapObjectOpened[mapNum][j];
                        if(gp.obj[mapNum][j].opened) {
                            gp.obj[mapNum][j].down1 = gp.obj[mapNum][j].image2;
                        }
                    }
                }
            }
        }
        catch(Exception e){
            System.out.println("Load Exception!");
        }
    }
}
