
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
    
    //direction of the paddle
    private int dx;
    private Game game;

    public Paddle(int x1, int y1, int width, int height, Game game) {
        super(x1, y1, width, height);
        this.game = game;
        
        x = x1;
        y = y1;
    }


    @Override
    /*
    Updates its x position due to previous direction
    Checks movement of the Paddle, if collision with edges stay inside 
    Updates direction asking for the left and right keys
    */
    public void tick() {

        x += dx*4;

        if (x <= 0) {

            x = 0;
        }

        if (x >= 300 - width) {

            x = 300 - width;
        }
        
        if (game.getKeyManager().left) {

            dx = -1;
        } else if (game.getKeyManager().right) {

            dx = 1;
        } else {
            dx = 0;
        }
        
    }

    /*
    Updates the paddle position to its initial position (used when loosing a life)
    */
    public void resetState() {
        x = 200;
        y = 360;
    }
    
    @Override
    /*
    Paint the paddle with its x, y, width, and height
    */
    public void render(Graphics g) {
        g.drawImage(Assets.paddle, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
