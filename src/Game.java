
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danieltrevino
 */
public class Game implements Runnable {
    private BufferStrategy bs;
    private Graphics g;
    private Display display;
    String title;
    private int width;
    private int height;
    private Thread thread;
    private boolean running;
    private int x;
    private int direction;
    private Player player;
    private LinkedList <Enemy> enemies;// to use an enemy
    private Brick bricks[];
    private int numberOfBricks = 30;
    private KeyManager keyManager;
    private int score;
    private int lives;
    
    /**
     * to create title, width and height and set the game is still not running
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height  to set the height of the window
     */
    public Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
    }

    /**
     * To get the width of the game window
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * To get the key manager
     * @return keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }
    
    /**
     * initializing the display window of the game
     */
    private void init(){
        display = new Display(title, getWidth(), getHeight());
        bricks = new Brick[numberOfBricks];
        Assets.init();
        
            int k = 0;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 6; j++) {

                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50, Assets.brick.getWidth(),Assets.brick.getHeight());
                k++;
            }
        }
        
        /*player = new Player(50, getHeight() - 150, "right", 100, 100, this);
        enemies = new LinkedList();
        for(int i = 0; i <= 5; i++){
            Enemy enemy = new Enemy((int)(Math.random() * (getWidth() - 100)), 
                    (int)(Math.random() * 400) - 400, 1, 100, 100, this);
            enemies.add(enemy);
        }
        score = 0;
        lives = 5;*/
        display.getJframe().addKeyListener(keyManager);
    }

    @Override
    public void run() {
        init();
        int fps = 50;
        double timeTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            if(delta >= 1){
                tick();
                render();
                delta --;
            }
        }
        stop();
    }
    
    private void tick() {
        keyManager.tick();
        /*
        if(lives > 0){
            player.tick();
            for(Enemy enemy : enemies){
                enemy.tick();
                if(player.collision(enemy)){
                    if(player.getY() < (enemy.getY() + enemy.getHeight()) && 
                        player.getY() > (enemy.getY() + enemy.getHeight() - 5)){
                            score += 100;
                            enemy.destroy();
                    }
                }
            }
        }else{
            if(keyManager.space){
                lives = 5;
                score = 0;
                for(Enemy enemy : enemies){
                    enemy.setVelocity(1);
                    enemy.destroy();
                }
            }
        }
        */
    }
    
    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
        }else{
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            for(int i=0; i<numberOfBricks; i++){
                bricks[i].render(g);
            }
            /*
            if(lives > 0){
                player.render(g);
                for(Enemy enemy : enemies){
                    enemy.render(g);
                }
                g.drawString("Score: " + Integer.toString(score), getWidth()-80, 
                        20);
                g.drawString("Lives: " + Integer.toString(lives), getWidth()-80, 
                        45);
            }else{
                g.drawString("You lose your score: " + Integer.toString(score) 
                        + " press space to play again", 200, 100);
            }
            */
            bs.show();
            g.dispose();
        }
    }
    
    /**
     * setting the thead for the game
     */
    public synchronized void start() {
        if(!running){
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if(running){
            running = false;
            try{
                thread.join();
            }catch (InterruptedException ie){
                ie.printStackTrace();
            }
        }
    }

    void objBottom() {
        score -= 20;
        lives --;
        for(Enemy enemy : enemies){
            enemy.setVelocity(enemy.getVelocity()+1);
        }
    }
}
