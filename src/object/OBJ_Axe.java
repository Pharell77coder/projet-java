package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{

    public static final  String objName = "Hache de bucheron";
    
    public OBJ_Axe(GamePanel gp) {
        super(gp);

        type = type_axe;
        name = objName;
        down1 = setup("objects/axe", gp.tileSize, gp.tileSize);
        description = "["+name+"]\nRouillé mais peut encore\ncouper certains arbres.";
        attackValue = 2;
        attackArea.width = 30;
        attackArea.height = 30;
        price = 75;
        knockBackPower = 10;
        motion1_duration = 20;
        motion2_duration = 40;

    }
}
