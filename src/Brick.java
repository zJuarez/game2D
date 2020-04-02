
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
    private boolean destroyed;
    
    private Game game;
    
    
    private Animation animation;
    private Animation explosion;
    private long time;
    boolean firstTime;
    boolean ticking;
    
    public Brick(int x, int y, int width, int height, boolean des) {
        super(x, y, width, height);
        this.width = width;
        this.height = height;
        this.x = x;
        this.y = y;
        animation  = new Animation(Assets.brick, (int) (Math.random()*200 + 120));
        destroyed = des;
        explosion = new Animation (Assets.explosion, 12);
        firstTime = !des;
    }
    
    public boolean isDestroyed() {
        return destroyed;
    }
    
    public void setDestroyed(boolean state) {
        if (state==false)
            firstTime = true;
        
        destroyed = state;
    }

    @Override
    public void tick() {
        if(destroyed==false){
        animation.tick();
    }
        else{
         explosion.tick();
         if(firstTime==true){
         time = System.currentTimeMillis();
         firstTime=false;
        }    
       }
    }
    
    public boolean getTicking(){
        return ticking;
    }
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
