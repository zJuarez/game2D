
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
public class Paddle extends Item {
    
    private int dx;
    private Game game;

    public Paddle(int width, int height, Game game) {
        super(200, 360, width, height);
        this.game = game;
        
        resetState();        
    }


    @Override
    public void tick() {

        x += dx*4;

        if (x <= 0) {

            x = 0;
        }

        if (x >= 800 - width) {

            x = 800 - width;
        }
        
        if (game.getKeyManager().left) {

            dx = -1;
        } else if (game.getKeyManager().right) {

            dx = 1;
        } else {
            dx = 0;
        }
        
    }

    private void resetState() {
        x = 200;
        y = 360;
    }
    
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.paddle, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}