/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package videogame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/**
 *
 * @author antoniomejorado
 */
public class Game implements Runnable {

    private BufferStrategy bs;      // to have several buffers when displaying
    private Graphics g;             // to paint objects
    private Display display;        // to display in the game
    String title;                   // title of the window
    private int width;              // width of the window
    private int height;             // height of the window
    private Thread thread;          // thread to create the game
    private boolean running;        // to set the game
    private Player player;          // to use a player
    private KeyManager keyManager;  // to manage the keyboard
    private LinkedList<Enemy> lista;
    private int points = 0;
    private int lifes = 5;
    private int drops = 0;
    private int velEnemies = 1;
    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public int getVel(){
        return velEnemies;
    }
    public void setVel(int x){
        velEnemies =x;
    }
    public int getDrops(){
        return drops;
    }
    public void setDrops(int x){
        drops = x;
    }
    public int getLifes(){
        return lifes;
    }
    public void setLifes(int x){
        lifes = x;
    }
            
    public int getPoints(){
        return points;
    }
    public void setPoints(int x){
        points  = x;
    }
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
    }

    public Player getPlayer() {
        return player;
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        Assets.init();
        lista = new LinkedList<Enemy>();
        int monedas = 8;
        player = new Player(0, getHeight() - 100, 1, 100, 100, this);
        for (int i = 1; i <= monedas; i++) {
            Enemy enemy = new Enemy((int) (Math.random() * getWidth()), -80, 1, 100, 100, this);
            lista.add(enemy);
        }
        display.getJframe().addKeyListener(keyManager);
    }

    @Override
    public void run() {
        init();
        // frames per second
        int fps = 50;
        // time for each tick in nano segs
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanosecs
        long lastTime = System.nanoTime();
        while (running & lifes>0) {  //while we still have lifes
            // setting the time now to the actual time
              if(getDrops()>=10){  // if drops are ten or more increment the velocity by 1 and susbtract the lifes by 1. Also reset the drops
                setDrops(getDrops()%10);
                setLifes(getLifes()-1);
                setVel(getVel()+1) ;
            }
              
            now = System.nanoTime();
            // acumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            // updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    private void tick() {
        keyManager.tick();
        // avancing player with colision
        player.tick();
        //updating enemies / coins
        for (Enemy enemy : lista) {
            enemy.tick();
            // if collision (special (check item class)) sum 100 points and restart up in a random X position
            if (player.collision(enemy)) {             
                enemy.setX((int) (Math.random() * getWidth()));
                enemy.setY(-80);
                setPoints(getPoints()+100);
            }
        }

    }

    private void render() {
        // get the buffer strategy from the display
        bs = display.getCanvas().getBufferStrategy();
        /* if it is null, we define one with 3 buffers to display images of
        the game, if not null, then we display every image of the game but
        after clearing the Rectanlge, getting the graphic object from the 
        buffer strategy element. 
        show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            //printing the lifes and points updates on screen
            g.drawString("Lifes: ",30,30);  
            g.drawString(java.lang.Integer.toString(getLifes()), 70, 30);
            g.drawString("Points: ",30,60);
            g.drawString(java.lang.Integer.toString(getPoints()), 70, 60);
            player.render(g);
            // for to render all coind
            for (Enemy enemy : lista) {
                 enemy.render(g);
   
            }
            bs.show();
            g.dispose();
        }

    }

    /**
     * setting the thead for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
            try {
                thread.join();
            } catch (InterruptedException ie) {
                ie.printStackTrace();
            }
        }
    }

}
