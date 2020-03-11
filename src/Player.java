
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
public class Player extends Item {
    
    private String direction;
    private int width;
    private int height;
    private Game game;
    private int velocity;

    public Player(int x, int y, String direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.velocity = 8;
    }

    public String getDirection() {
        return direction;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void tick() {

        if(game.getKeyManager().up){
            setY(getY() - velocity);
        }
        if(game.getKeyManager().down){
            setY(getY() + velocity);
        }
        if(game.getKeyManager().right){
            setX(getX() + velocity);
        }
        if(game.getKeyManager().left){
            setX(getX() - velocity);
        }
                
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.player, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
