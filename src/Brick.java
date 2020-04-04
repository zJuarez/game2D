
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
    
    
    private boolean destroyed;
    
    private Game game;
    
    
    private Animation animation;
    private Animation explosion;
    private long time;
    boolean firstTime;
    boolean ticking;
    
    /**
     * initialize the values for brick
     * @param x the x position
     * @param y the y position
     * @param des boolean to see if its destroyed
     */
    public Brick(int x, int y, boolean des) {
        super(x, y, Assets.brick[0].getWidth(), Assets.brick[0].getHeight());
        animation  = new Animation(Assets.brick, (int) (Math.random()*200 + 120));
        destroyed = des;
        explosion = new Animation (Assets.explosion, 12);
        firstTime = !des;
    }
    
    /**
     * get if destroyed
     * @return destroyed
     */
    public boolean isDestroyed() {
        return destroyed;
    }
    
    /**
     * set destroyed
     * @param state set to true or false 
     */
    public void setDestroyed(boolean state) {
        if (state==false)
            firstTime = true;
        
        destroyed = state;
    }

    /**
     * tick the brick
     */
    @Override
    public void tick() {
        if(destroyed==false){
            //tick brick animation
            animation.tick();
        }
        else{
            //tick explosion animation
            explosion.tick();
            if(firstTime==true){
                time = System.currentTimeMillis();
                firstTime=false;
            }    
        }
    }
    
    /**
     * get if ticking
     * @return ticking
     */
    public boolean getTicking(){
        return ticking;
    }
    
    /**
     * render the brick
     * @param g the graphics
     */
    @Override
    public void render(Graphics g) {
        ticking = false;
        
        if(destroyed==false){
        g.drawImage(animation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }
        else if(System.currentTimeMillis() < time + explosion.getSpeed()*explosion.getLength()){
            ticking = true;
            g.drawImage(explosion.getCurrentFrame(), getX()+8, getY(), 22,22, null);
        }
   }
}
