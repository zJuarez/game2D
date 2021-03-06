
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
    
    public Ball(int x1, int y1, int width, int height, int xdir1, int ydir1) {
        super(x1, y1, width, height);
        xdir = xdir1;
        ydir = ydir1;
        x=x1;
        y=y1;
       // resetState();
    }
    
    @Override
    public void tick() {

        x += xdir*4;
        y += ydir*4;

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
    
    public void resetState() {
        x = 230;
        y = 355;
    }

    void setXDir(int x) {
        xdir = x;
    }

    void setYDir(int y) {
        ydir = y;
    }

    int getYDir() {
        return ydir;
    }
    int getXDir(){
        return xdir;
    }
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }

}
