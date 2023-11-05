package enviroment;

import main.GamePanel;

import java.awt.*;

public class EnvironmentManager {
    final GamePanel gp;
    public Lighting lighting;
    
    public EnvironmentManager(GamePanel gp) {
        this.gp = gp;
    }
    public void setup() {
        lighting = new Lighting(gp);//gp.screenHeight
    }
    public void update() {
        lighting.update();
    }
    public void draw(Graphics2D g2) {
        if (!gp.keyH.checkDrawTime){
            lighting.draw(g2);
        }
    }
}