
import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danieltrevino
 */
public class PowerUp extends Item {
    
    private Game game;
    private boolean destroyed;
    
    public PowerUp(int x, int y, int width, int height, Game game, boolean destoryed) {
        super(x, y, width, height);
        this.game = game;
        this.destroyed = destroyed;
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
    
    public void setDestroyed(boolean state) {
        destroyed = state;
    }

    @Override
    public void tick() {
        setY(getY() + 4);
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.add, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
