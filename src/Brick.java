
import java.awt.Graphics;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Mauricio
 */
public class Brick extends Item{
    
    private int x;
    private int y;
    private int width;
    private int height;
    
    private Game game;
    
    
    private Animation animation;
    
    public Brick(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        animation  = new Animation(Assets.brick, (int) (Math.random()*200 + 120));
    }

    @Override
    public void tick() {
        animation.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
