
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    //private LinkedList <Enemy> enemies;// to use an enemy
    private Brick bricks[];
    private int numberOfBricks = 30;
    private int maxBalls = 3;
    private KeyManager keyManager;
    private int score;
    private int lives;
    private Paddle paddle;
    private int random;
    private LinkedList<PowerUp> powerups;
    private Ball balls[];
    private int numBalls;
    private Animation explosion;
    private boolean win = false;

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

    public void Save(String strFileName) {

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(strFileName));
            writer.print("" + lives + "/" + score);

            for (int i = 0; i < numberOfBricks; i++) {
                writer.print("/" + bricks[i].isDestroyed());
            }

            int numberOfPowerUps = powerups.size();

            writer.print("/" + numberOfPowerUps);

            for (PowerUp p : powerups) {
                writer.print("/" + p.getX() + "/" + p.getY() + "/" + p.isDestroyed());
            }

            for (int i = 0; i < maxBalls; i++) {
                writer.print("/" + balls[i].getX() + "/" + balls[i].getY() + "/" + balls[i].getXDir() + "/" + balls[i].getYDir() + "/" + balls[i].isDestroyed());
            }

            writer.print("/" + paddle.getX() + "/" + paddle.getY());

            writer.close();
        } catch (IOException ioe) {
            System.out.println("File Not found CALL 911");
        }

    }

    public void Load(String strFileName) {
        try {
            win = false;
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
                destroyed[i - 2] = des;
            }

            int k = 0;

            for (int i = 0; i < 5; i++) {

                for (int j = 0; j < 6; j++) {

                    bricks[k] = new Brick(j * 40 + 30, i * 10 + 50, destroyed[k]);
                    k++;
                }
            }

            int pos = 2 + numberOfBricks;

            int nPU = Integer.parseInt(datos[pos++]);
            powerups.clear();

            for (int i = 0; i < nPU; i++) {
                int x = Integer.parseInt(datos[pos++]);
                int y = Integer.parseInt(datos[pos++]);
                boolean des = Boolean.parseBoolean(datos[pos++]);
                powerups.add(new PowerUp(x, y, 25, 25, this, des));
            }

            for (int j = 0; j < maxBalls; j++) {
                int xBall = Integer.parseInt(datos[pos++]);
                int yBall = Integer.parseInt(datos[pos++]);
                int xDir = Integer.parseInt(datos[pos++]);
                int yDir = Integer.parseInt(datos[pos++]);
                boolean des = Boolean.parseBoolean(datos[pos++]);

                balls[j] = new Ball(xBall, yBall, xDir, yDir, des);

            }

            int xPaddle = Integer.parseInt(datos[pos++]);
            int yPaddle = Integer.parseInt(datos[pos++]);

            paddle = new Paddle(xPaddle, yPaddle, 50, 10, this);
            reader.close();

        } catch (IOException e) {
            System.out.println("File Not found CALL 911");
        }
    }

    /**
     * initializing the display window of the game
     */
    private void init() {
        display = new Display(title, getWidth(), getHeight());
        bricks = new Brick[numberOfBricks];
        //explosion = new Animation (Assets.explosion,30);
        Assets.init();

        int k = 0;

        for (int i = 0; i < 5; i++) {

            for (int j = 0; j < 6; j++) {

                bricks[k] = new Brick(j * 40 + 30, i * 10 + 50, false);
                k++;
            }
        }

        score = 0;
        lives = 5;
        balls = new Ball[maxBalls];

        for (int i = 0; i < maxBalls; i++) {
            balls[i] = new Ball(230, 355, 1, -1, true);
        }

        balls[0].setDestroyed(false);
        numBalls = 1;
        paddle = new Paddle(200, 360, 50, 10, this);
        display.getJframe().addKeyListener(keyManager);
        powerups = new LinkedList();
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

        if (lives > 0 && !win) {

            for (int i = 0; i < numberOfBricks; i++) {
                bricks[i].tick();
            }

            for (int j = 0; j < maxBalls; j++) {

                Ball ballFor = balls[j];
                if (!ballFor.isDestroyed()) {
                    if (ballFor.getY() >= 400) {
                        if (numBalls == 1) {
                            for (PowerUp powerup : powerups) {
                                powerup.setDestroyed(true);
                            }
                            lives--;
                            ballFor.resetState();
                            paddle.resetState();

                        } else {
                            ballFor.setDestroyed(true);
                            numBalls--;
                        }
                    }

                    if (ballFor.collision(paddle)) {

                        int paddleLPos = (int) paddle.getX();
                        int ballLPos = (int) ballFor.getX();

                        int first = paddleLPos + 8;
                        int second = paddleLPos + 16;
                        int third = paddleLPos + 24;
                        int fourth = paddleLPos + 32;

                        if (ballLPos < first) {

                            ballFor.setXDir(-1);
                            ballFor.setYDir(-1);
                        }

                        if (ballLPos >= first && ballLPos < second) {

                            ballFor.setXDir(-1);
                            ballFor.setYDir(-1 * ballFor.getYDir());
                        }

                        if (ballLPos >= second && ballLPos < third) {

                            ballFor.setXDir(0);
                            ballFor.setYDir(-1);
                        }

                        if (ballLPos >= third && ballLPos < fourth) {

                            ballFor.setXDir(1);
                            ballFor.setYDir(-1 * ballFor.getYDir());
                        }

                        if (ballLPos > fourth) {

                            ballFor.setXDir(1);
                            ballFor.setYDir(-1);
                        }
                    }
                    int destroyedBricks = 0;
                    for (int i = 0; i < numberOfBricks; i++) {
                        if (ballFor.collision(bricks[i])) {

                            int ballLeft = (int) ballFor.getX();
                            int ballHeight = (int) ballFor.getHeight();
                            int ballWidth = (int) ballFor.getWidth();
                            int ballTop = (int) ballFor.getY();

                            Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                            Point pointLeft = new Point(ballLeft - 1, ballTop);
                            Point pointTop = new Point(ballLeft, ballTop - 1);
                            Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                            if (!bricks[i].isDestroyed()) {

                                if (bricks[i].contains(pointRight)) {

                                    ballFor.setXDir(-1);
                                } else if (bricks[i].contains(pointLeft)) {

                                    ballFor.setXDir(1);
                                }

                                if (bricks[i].contains(pointTop)) {

                                    ballFor.setYDir(1);
                                } else if (bricks[i].contains(pointBottom)) {

                                    ballFor.setYDir(-1);
                                }

                                bricks[i].setDestroyed(true);
                                score += 20;
                                //Assets.breakBrick.play();
                                random = (int) (Math.random() * 19) + 1;
                                if (random != 13) {
                                    powerups.add(new PowerUp(bricks[i].getX(),
                                            bricks[i].getY(), 25, 25, this, false));
                                }
                            }
                        }
                        if (bricks[i].isDestroyed()) {
                            destroyedBricks++;
                        }
                    }
                    if (destroyedBricks == numberOfBricks) {
                        win = true;
                    }

                    ballFor.tick();
                }
            }

            for (PowerUp powerup : powerups) {
                if (!powerup.isDestroyed()) {
                    powerup.tick();
                    if (paddle.collision(powerup)) {
                        if (numBalls < maxBalls) {
                            numBalls++;
                            addBall(paddle.getX(), paddle.getY());
                        }
                        powerup.setDestroyed(true);
                    }
                    if (powerup.getY() >= 400) {
                        powerup.setDestroyed(true);
                    }
                    // explosion.setIndex(1);
                    //  explosion.tick();
                }
            }

            paddle.tick();

            if (keyManager.s) {
                Save("archivo.txt");
            }

            if (keyManager.l) {
                Load("archivo.txt");
            }

        } else {
            if (keyManager.space) {
                win = false;
                lives = 5;
                score = 0;
                numBalls = 1;
                for (int i = 0; i < maxBalls; i++) {
                    balls[i].setDestroyed(true);
                }
                balls[0].setDestroyed(false);
                paddle.resetState();
                for (int i = 0; i < numberOfBricks; i++) {
                    bricks[i].setDestroyed(false);
                }
                for (PowerUp powerup : powerups) {
                    powerup.setDestroyed(true);
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
            if (lives > 0 && !win) {
                g.setFont(new Font("Karmatic Arcade", Font.PLAIN, 9));
                g.setColor(Color.blue);
                g.drawString("Score: " + Integer.toString(score), 215, 18);
                for (int i = 1; i <= lives; i++) {
                    g.drawImage(Assets.life, 11 * i, 10, 10, 10, null);
                }
                for (int i = 0; i < numberOfBricks; i++) {
                    bricks[i].render(g);
                }
                for (int i = 0; i < numberOfBricks; i++) {
                    if (bricks[i].getTicking() == true) {
                        bricks[i].render(g);
                    }
                }

                for (int j = 0; j < maxBalls; j++) {
                    if (!balls[j].isDestroyed()) {
                        balls[j].render(g);
                    }
                }

                paddle.render(g);

                for (PowerUp powerup : powerups) {
                    if (!powerup.isDestroyed()) {
                        powerup.render(g);
                    }
                }
            } else {
                g.setFont(new Font("Karmatic Arcade", Font.PLAIN, 9));
                g.setColor(Color.blue);
                g.drawString("Score: " + Integer.toString(score), 215, 18);
                g.setFont(new Font("Karmatic Arcade", Font.PLAIN, 26));

                if (win) {
                    g.setColor(Color.green);
                    g.drawString("You win", 87, height / 2 - 20);
                } else {
                    g.setColor(Color.red);
                    g.drawString("You lose", 70, height / 2 - 20);
                }

                g.setFont(new Font("Karmatic Arcade", Font.PLAIN, 12));
                g.setColor(Color.yellow);
                g.drawString("Press space to play again", 37, height / 2 + 10);

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

    private void addBall(int x, int y) {
        System.out.println("si se llama");
        for (int i = 0; i < maxBalls; i++) {
            if (balls[i].isDestroyed()) {
                balls[i].setDestroyed(false);
                balls[i].setPosition(x, y);
                break;
            }
        }
    }

}
