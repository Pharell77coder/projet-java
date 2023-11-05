package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.*;

public class MON_RedSlime extends Entity {

    final GamePanel gp;

    public MON_RedSlime(GamePanel gp) {
        super(gp);
        this.gp = gp;

        type = type_monster;
        name = "Slime Rouge";
        defaultSpeed = 2;
        speed = defaultSpeed;
        maxLife = 8;
        life = maxLife;
        attack = 7;
        defense = 0;
        exp = 5;
        projectile = new OBJ_Rock(gp);

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;

        getImage();
    }
    public void getImage() {
        down1 = setup("monster/redslime_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("monster/redslime_down_2", gp.tileSize, gp.tileSize);
        up1 = down1; up2 = down2; left1 = down1;   left2 = down2; right1 = down1;  right2 = down2;
    }
    public void setAction() {

        if (onPath) {

            checkStopChasingOrNot(gp.player, 15, 100);

            searchPath(getGoalCol(gp.player), getGoalRow(gp.player), false);

            checkShootOrNot(200, 30);
        }
        else {
            checkStartChasingOrNot(gp.player, 5, 100);

            getRandomDirection(120);
        }

  
    }
    public void damageReaction() {
        actionLockCounter = 0;
        onPath = true;
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
