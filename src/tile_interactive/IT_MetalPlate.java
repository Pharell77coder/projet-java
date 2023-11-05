package tile_interactive;

import main.GamePanel;

public class IT_MetalPlate extends InteractiveTile{

    public static final  String itName = "Plaque de métal";

    public IT_MetalPlate(GamePanel gp, int col, int row) {
        super(gp);

        this.worldX = gp.tileSize*col;
        this.worldY = gp.tileSize*row;

        name = itName;
        down1 = setup("tiles_interactive/metalplate", gp.tileSize, gp.tileSize);
        down2 = down1;

        solidArea.x = 0;
        solidArea.y = 0;
        solidArea.width = 0;
        solidArea.height = 0;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    
    }
}
