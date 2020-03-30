
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

    private Animation animationUp;
    private Animation animationDown;
    private Animation animationLeft;
    private Animation animationRight;
    private Animation animationX;
    private Animation current;
    
    
    private Animation getA(){
        if (game.getKeyManager().up) {
            return animationUp;
        }
        else if (game.getKeyManager().down) {
            return animationDown;
        }
        else if (game.getKeyManager().right) {
            return animationRight;
        }
        else if (game.getKeyManager().left) {
            return animationLeft;
        }
        else{
            return animationX;
        }
    }
    
    public Player(int x, int y, String direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.direction = direction;
        this.width = width;
        this.height = height;
        this.game = game;
        this.velocity = 8;
        
        int s = 170;
        
        animationRight = new Animation(Assets.playerRight, s);
        animationLeft = new Animation(Assets.playerLeft, s);
        animationUp = new Animation(Assets.playerUp, s);
        animationDown = new Animation(Assets.playerDown, s);
        animationX = new Animation(Assets.playerX, s);

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

        if (game.getKeyManager().up) {
            this.animationUp.tick();
            setY(getY() - velocity);
            current = animationUp;
        }
        else if (game.getKeyManager().down) {
            this.animationDown.tick();
            setY(getY() + velocity);
           current = animationDown;

        }
        else if (game.getKeyManager().right) {
            this.animationRight.tick();
            setX(getX() + velocity);
             current = animationRight;

        }
        else if (game.getKeyManager().left) {
            this.animationLeft.tick();
            setX(getX() - velocity);
            current = animationLeft;
        }
        else{
            this.animationX.tick();
            current = animationX;
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(current.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }

}
