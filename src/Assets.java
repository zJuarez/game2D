
import java.awt.image.BufferedImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author danieltrevino
 */
public class Assets {
    public static BufferedImage background;
    public static BufferedImage player;
    public static BufferedImage drop;
    public static BufferedImage brick[];
    public static BufferedImage sprites;
    public static BufferedImage playerUp[];
    public static BufferedImage playerDown[];
    public static BufferedImage playerLeft[];
    public static BufferedImage playerRight[];
    public static BufferedImage playerX[];
    public static BufferedImage ball;
    public static BufferedImage paddle;
    
    
    /**
     * initializing the images of the game
     */
    public static void init(){
        background = ImageLoader.loadImage("/images/t.png");
        player = ImageLoader.loadImage("/images/player.png");
        drop = ImageLoader.loadImage("/images/drop.png");
        //brick = ImageLoader.loadImage("images/brick.png");
        
        brick = new BufferedImage[12];
        
       
        brick[0] = ImageLoader.loadImage("/images/Diapositiva1.JPG");
        brick[1] = ImageLoader.loadImage("/images/Diapositiva2.JPG");
        brick[2] = ImageLoader.loadImage("/images/Diapositiva3.JPG");
        brick[3] = ImageLoader.loadImage("/images/Diapositiva4.JPG");
        brick[4] = ImageLoader.loadImage("/images/Diapositiva5.JPG");
        brick[5] = ImageLoader.loadImage("/images/Diapositiva6.JPG");
        brick[6] = ImageLoader.loadImage("/images/Diapositiva6.JPG");
        brick[7] = ImageLoader.loadImage("/images/Diapositiva5.JPG");
        brick[8] = ImageLoader.loadImage("/images/Diapositiva4.JPG");
        brick[9] = ImageLoader.loadImage("/images/Diapositiva3.JPG");
        brick[10] = ImageLoader.loadImage("/images/Diapositiva2.JPG");
        brick[11] = ImageLoader.loadImage("/images/Diapositiva1.JPG");
        
        ball = ImageLoader.loadImage("/images/ball.png");
        paddle = ImageLoader.loadImage("/images/paddle.png");
        
        playerUp = new BufferedImage[3];
        playerDown = new BufferedImage[3];
        playerLeft = new BufferedImage[3];
        playerRight = new BufferedImage[3];
        playerX = new BufferedImage[3];


        playerUp[0] = ImageLoader.loadImage("/images/Character_rpg-20.png");
        playerUp[1] = ImageLoader.loadImage("/images/Character_rpg-24.png");
        playerUp[2] = ImageLoader.loadImage("/images/Character_rpg-28.png");
        
        playerDown[0] = ImageLoader.loadImage("/images/Character_rpg-21.png");
        playerDown[1] = ImageLoader.loadImage("/images/Character_rpg-25.png");
        playerDown[2] = ImageLoader.loadImage("/images/Character_rpg-29.png");
        
        playerRight[0] = ImageLoader.loadImage("/images/Character_rpg-19.png");
        playerRight[1] = ImageLoader.loadImage("/images/Character_rpg-23.png");
        playerRight[2] = ImageLoader.loadImage("/images/Character_rpg-27.png");
        
        playerLeft[0] = ImageLoader.loadImage("/images/Character_rpg-22.png");
        playerLeft[1] = ImageLoader.loadImage("/images/Character_rpg-26.png");
        playerLeft[2] = ImageLoader.loadImage("/images/Character_rpg-30.png");
        
        playerX[0] = ImageLoader.loadImage("/images/Character_rpg-21.png");
        playerX[1] = ImageLoader.loadImage("/images/Character_rpg-21_1.png");
        playerX[2] = ImageLoader.loadImage("/images/Character_rpg-21_2.png");

    }
}
