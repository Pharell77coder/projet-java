package tile_interactive;

import entity.Entity;
import main.GamePanel;
import java.awt.*;

public class IT_Drytree extends InteractiveTile{

    final GamePanel gp;

    public IT_Drytree(GamePanel gp, int col, int row) {
        super(gp);
        this.gp = gp;

        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;
        down1 = setup("tiles_interactive/drytree", gp.tileSize, gp.tileSize);
        down2 = down1;
        destructible = true;
        life = 3;
    }
    public boolean isCorrectItem(Entity entity) {
        return entity.currentWeapon.type == type_axe;
    }
    public void playSE() {
        gp.SE("cuttree.wav");
    }
    public InteractiveTile getDestroyedForm() {
        return new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
    } 
    public Color getParticleColor() {
        return new Color(65,50,30);
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
}