/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Graphics;

/**
 *
 * @author antoniomejorado
 */
public class Enemy extends Item{

    private int direction;
    private Game game;
   
    
    public Enemy(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.game = game;
    }

    public int getDirection() {
        return direction;
    }


    public void setDirection(int direction) {
        this.direction = direction;
    }


    @Override
    public void tick() {
        
        // drop with game velocity
        setY(getY()+game.getVel()); 
       // check if collision / gets out of the screen to keep it onscreen
       
        if (getX() + 60 >= game.getWidth()) {
            setX(game.getWidth() - 60);
        }
        else if (getX() <= -30) {
            setX(-30);
        }
        if (getY() >= game.getHeight()+10) { // means that the player didn't catch it thus it restarts up and we sum 1 to drops and sunstract points
            setY(-80);
            setX((int) (Math.random() * getWidth()));
            game.setPoints(game.getPoints()-20);
            game.setDrops(game.getDrops()+1);           
        }
        else if (getY() < -80) { // starting before the height for visual purposes
            setY(-80);
        }
        
       
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.enemy, getX(), getY(), getWidth(), getHeight(), null);
    }
}
