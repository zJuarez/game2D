
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
    
    /**
     * initializes the values for the powerup
     * @param x the x position
     * @param y the y position
     * @param width the width
     * @param height the height
     * @param game the game
     */
    public PowerUp(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
    }

    /**
     * tick the powerup
     */
    @Override
    public void tick() {
        //move down
        setY(getY() + 4);
    }

    /**
     * renders the powerup
     * @param g the graphics
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.add, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
