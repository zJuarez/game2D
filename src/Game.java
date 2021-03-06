
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
    private Animation explosion;

    /**
     * to create title, width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
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
     * To get the key manager
     *
     * @return keyManager
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * initializing the display window of the game
     */
 
    public void Save(String strFileName) {

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(strFileName));
            writer.print("" + lives + "/" + score);

            for (int i = 0; i < numberOfBricks; i++) {
                writer.print("/" + bricks[i].isDestroyed());
            }

            writer.print("/" + ball.getX() + "/" + ball.getY() + "/" + ball.getXDir() + "/" + ball.getYDir());
            writer.print("/" + paddle.getX() + "/" + paddle.getY());

            writer.close();
        } catch (IOException ioe) {
            System.out.println("File Not found CALL 911");
        }

    }

    public void Load(String strFileName) {
        try {
            FileReader file = new FileReader(strFileName);
            BufferedReader reader = new BufferedReader(file);
            String line;
            String datos[];
            line = reader.readLine();

            datos = line.split("/");
            lives = Integer.parseInt(datos[0]);
            score = Integer.parseInt(datos[1]);
            boolean destroyed[];
            destroyed = new boolean[numberOfBricks];

            for (int i = 2; i < 2 + numberOfBricks; i++) {
                boolean des = Boolean.parseBoolean(datos[i]);
                destroyed[i-2] = des;
            }

            int k = 0;

            for (int i = 0; i < 5; i++) {

                for (int j = 0; j < 6; j++) {

                    bricks[k] = new Brick(j * 42 + 30, i * 11 + 50, Assets.brick[0].getWidth(), Assets.brick[0].getHeight(), destroyed[k]);
                    k++;
                }
            }
            
            int pos = 2 + numberOfBricks;
            int xBall = Integer.parseInt(datos[pos++]);
            int yBall = Integer.parseInt(datos[pos++]);
            int xDir = Integer.parseInt(datos[pos++]);
            int yDir = Integer.parseInt(datos[pos++]);
            
        ball = new Ball(xBall, yBall, 5, 5, xDir, yDir);
        
        int xPaddle = Integer.parseInt(datos[pos++]);
        int yPaddle = Integer.parseInt(datos[pos++]);
        
        paddle = new Paddle(xPaddle, yPaddle, 100, 10, this);
        //explosion = new Animation (Assets.explosion,30);

            reader.close();
        } catch (IOException e) {
            System.out.println("File Not found CALL 911");
        }
    }

    private void init() {
        display = new Display(title, getWidth(), getHeight());
        bricks = new Brick[numberOfBricks];
        //explosion = new Animation (Assets.explosion,30);
        Assets.init();

        int k = 0;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 6; j++) {

                bricks[k] = new Brick(j * 42 + 30, i * 11 + 50, Assets.brick[0].getWidth(), Assets.brick[0].getHeight(), false);
                k++;
            }
        }

        score = 0;
        lives = 5;
        ball = new Ball(250,355,5,5,1,-1);
        paddle = new Paddle(200,360,100, 10, this);
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
        while (running) {
            now = System.nanoTime();
            delta += (now - lastTime) / timeTick;
            lastTime = now;
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    private void tick() {
        keyManager.tick();

        if (lives > 0) {

            for (int i = 0; i < numberOfBricks; i++) {
                bricks[i].tick();
            }

            if (ball.getY() >= 400) {
                lives--;
                ball.resetState();
                paddle.resetState();
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

            for (int i = 0; i < numberOfBricks; i++) {
                if (ball.collision(bricks[i])) {

                    int ballLeft = (int) ball.getX();
                    int ballHeight = (int) ball.getHeight();
                    int ballWidth = (int) ball.getWidth();
                    int ballTop = (int) ball.getY();

                    Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                    Point pointLeft = new Point(ballLeft - 1, ballTop);
                    Point pointTop = new Point(ballLeft, ballTop - 1);
                    Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                    if (!bricks[i].isDestroyed()) {

                        if (bricks[i].contains(pointRight)) {

                            ball.setXDir(-1);
                        } else if (bricks[i].contains(pointLeft)) {

                            ball.setXDir(1);
                        }

                        if (bricks[i].contains(pointTop)) {

                            ball.setYDir(1);
                        } else if (bricks[i].contains(pointBottom)) {

                            ball.setYDir(-1);
                        }

                        bricks[i].setDestroyed(true);
                        score += 20;
                    }
                   // explosion.setIndex(1);
                  //  explosion.tick();
                }
            }

            ball.tick();
            paddle.tick();
            
            if(keyManager.s){
                Save("archivo.txt");
            }
            
            if(keyManager.l){
                Load("archivo.txt");
            }

        } else {
            if (keyManager.space) {
                lives = 5;
                score = 0;
                ball.resetState();
                paddle.resetState();
                for (int i = 0; i < numberOfBricks; i++) {
                    bricks[i].setDestroyed(false);
                }
            }
        }
    }

    private void render() {
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            if (lives > 0) {
                for (int i = 0; i < numberOfBricks; i++) {
                        bricks[i].render(g);
                    
                }
                 for (int i = 0; i < numberOfBricks; i++) {
                        if(bricks[i].getTicking()==true)
                            bricks[i].render(g);
                    
                }
                ball.render(g);
                paddle.render(g);
            } else {
                g.drawString("You lose your score: " + Integer.toString(score)
                        + " press space to restart", 100, 100);
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
