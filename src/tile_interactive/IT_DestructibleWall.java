package tile_interactive;

import entity.Entity;
import main.GamePanel;
import object.*;

import java.awt.*;
import java.util.Random;


public class IT_DestructibleWall extends InteractiveTile{
    public IT_DestructibleWall(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;
        down1 = setup("tiles_interactive/destructiblewall", gp.tileSize, gp.tileSize);
        down2 = down1;
        destructible = true;
        life = 3;
    }
    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon.type == type_pickaxe;
    }
    public void playSE() {
        gp.SE("chipwall.wav");
    }
    public InteractiveTile getDestroyedForm() {
        return null;
    } 
    public Color getParticleColor() {
        return new Color(65,65,65);
    }
    public int getParticleSize() {
        return 6;
    }
    public int getParticleSpeed() {
        return 1;
    }
    public int getParticleMaxLife() {
        return 20;
    }
    public void checkDrop() {
        int i = new Random().nextInt(100)+1;
        if(i < 50){
            dropItem(new OBJ_Coin_Bronze(gp));
        }
        if(i >= 50 && i < 75){
            dropItem(new OBJ_Heart(gp));
        }
        if(i >= 75 && i < 100){
            dropItem(new OBJ_ManaCrystal(gp));
        }
    }

}
