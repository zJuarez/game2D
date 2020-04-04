
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
public class Ball extends Item {

    private int xdir;
    private int ydir;

    /**
     * initializes the values for ball
     * @param x1 the x of the ball
     * @param y1 the y of the ball
     * @param xdir1 the direction in x
     * @param ydir1 the direction in y
     */
    public Ball(int x1, int y1, int xdir1, int ydir1) {
        super(x1, y1, 7, 7);
        xdir = xdir1;
        ydir = ydir1;
        x = x1;
        y = y1;
    }

    /**
     * tick the player
     */
    @Override
    public void tick() {

        //move
        x += xdir * 5;
        y += ydir * 5;

        //bounce with the walls
        if (x <= 0) {
            setXDir(1);
        }
        if (x >= 300 - width) {
            setXDir(-1);
        }
        if (y <= 0) {
            setYDir(1);
        }

    }

    /**
     * reset to start position
     */
    public void resetState() {
        x = 230;
        y = 355;
    }

    /**
     * set position to x and y
     * @param x the x
     * @param y the y
     */
    void setPosition(int x, int y) {
        this.x = x + 30;
        this.y = y - 5;
    }

    /**
     * set the direction in x
     * @param x direction in x
     */
    void setXDir(int x) {
        xdir = x;
    }

    /**
     * set the direction in y
     * @param y the direction in y
     */
    void setYDir(int y) {
        ydir = y;
    }

    /**
     * get the direction in y
     * @return the direction in y
     */
    int getYDir() {
        return ydir;
    }

    /**
     * get the direction in x
     * @return the direction in x
     */
    int getXDir() {
        return xdir;
    }

    /**
     * render the ball
     * @param g the graphics
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }

}
