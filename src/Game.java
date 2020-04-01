
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

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
    //private LinkedList <Enemy> enemies;// to use an enemy
    private Brick bricks[];
    private int numberOfBricks = 30;
    private KeyManager keyManager;
    private int score;
    private int lives;
    private Ball ball;
    private Paddle paddle;
    
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

                bricks[k] = new Brick(j * 42 + 30, i * 11 + 50, Assets.brick[0].getWidth(),Assets.brick[0].getHeight());
                k++;
            }
        }

        ball = new Ball(5, 5);
        paddle = new Paddle(30, 10, this);
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
         for(int i=0; i<numberOfBricks; i++){
                bricks[i].tick();
            }
        
        if (ball.collision(paddle)) {

            int paddleLPos = (int) paddle.getX();
            int ballLPos = (int) ball.getX();

            int first = paddleLPos + 8;
            int second = paddleLPos + 16;
            int third = paddleLPos + 24;
            int fourth = paddleLPos + 32;

            if (ballLPos < first) {

                ball.setXDir(-1);
                ball.setYDir(-1);
            }

            if (ballLPos >= first && ballLPos < second) {

                ball.setXDir(-1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos >= second && ballLPos < third) {

                ball.setXDir(0);
                ball.setYDir(-1);
            }

            if (ballLPos >= third && ballLPos < fourth) {

                ball.setXDir(1);
                ball.setYDir(-1 * ball.getYDir());
            }

            if (ballLPos > fourth) {

                ball.setXDir(1);
                ball.setYDir(-1);
            }
        }
        
        ball.tick();
        paddle.tick();
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
            
            ball.render(g);
            paddle.render(g);
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

}
