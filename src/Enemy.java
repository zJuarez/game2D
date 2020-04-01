
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
public class Enemy extends Item {
    
    private int direction;
    private Game game;
    private int velocity;
    
    public Enemy(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.game = game;
        this.velocity = 1;
    }

    public int getDirection() {
        return direction;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }


    @Override
    public void tick() {
        setY(getY() + velocity);
        
        if(getY() >= game.getHeight()){
            //game.objBottom();
            destroy();
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.drop, getX(), getY(), getWidth(), getHeight(), null);
    }

    void destroy() {
        setY((int)(Math.random() * 400) - 400);
        setX((int)(Math.random() * (game.getWidth() - 100)));
    }
    
}
