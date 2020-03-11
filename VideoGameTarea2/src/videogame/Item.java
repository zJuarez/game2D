/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package videogame;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author antoniomejorado
 */
public abstract class Item {
    protected int x;        // to store x position
    protected int y;        // to store y position
    protected int width;
    protected int height;
    protected int difY = 15;
     protected int difX = 35;

    
    /**
     * Set the initial values to create the item
     * @param x <b>x</b> position of the object
     * @param y <b>y</b> position of the object
     */
    public Item(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Get x value
     * @return x 
     */
    public int getX() {
        return x;
    }

    /**
     * Get y value
     * @return y 
     */
    public int getY() {
        return y;
    }

     public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
   /**
     * Set x value
     * @param x to modify
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Set y value
     * @param y to modify
     */
    public void setY(int y) {
        this.y = y;
    }
    
    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    /**
     * To update positions of the item for every tick
     */
    public abstract void tick();
    
    /**
     * To paint the item
     * @param g <b>Graphics</b> object to paint the item
     */
    public abstract void render(Graphics g);
    
    public boolean collision(Object o) {
        boolean status = false;  // it does not collission
        if (o instanceof Item) {
            Rectangle rItem = new Rectangle(this.getX(), this.getY(), 
                    this.getWidth(), this.getHeight());
            Item i = (Item) o;
            Rectangle rOther = new Rectangle(i.getX(), i.getY(), i.getWidth(), 
                    i.getHeight());
            status = rItem.intersects(rOther);  
            
            if(rItem.getY()-rOther.getHeight()+difY < rOther.getY())  //if the collision happens to be without the player being below the coin, it's false
                status = false;
            if(Math.abs(rItem.getX()-rOther.getX())>difX) // if the colision happens to be from the side (the differences in x of both objects being big enough) its false too
                status = false;
            
        }
        return status;    
    }
}
