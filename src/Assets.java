
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
    public static BufferedImage explosion[];
    public static BufferedImage sprit;
    
    
    /**
     * initializing the images of the game
     */
    public static void init(){
        background = ImageLoader.loadImage("/images/t.png");
        player = ImageLoader.loadImage("/images/player.png");
        drop = ImageLoader.loadImage("/images/drop.png");
        sprit = ImageLoader.loadImage("/images/sprei.png");
        //brick = ImageLoader.loadImage("images/brick.png");
        
        brick = new BufferedImage[12];
       int k = 0;
       for(int i =1 ; i<=6; i++){
           
           brick[k++] = ImageLoader.loadImage("/images/Diapositiva"+String.valueOf(i)+ ".JPG");
           brick[12-k] = ImageLoader.loadImage("/images/Diapositiva"+String.valueOf(i)+ ".JPG");
       }
    
        ball = ImageLoader.loadImage("/images/ball.png");
        paddle = ImageLoader.loadImage("/images/paddle.png");
        
        
        explosion = new BufferedImage[40];
        
        for(int i = 0; i<40; i++){
            if(i+1<10)
            explosion[i]= ImageLoader.loadImage("/images/image_part_00"+ String.valueOf(i+1)+ ".png");
            else 
             explosion[i] = ImageLoader.loadImage("/images/image_part_0"+ String.valueOf(i+1)+ ".png");
        }
        
       
    }
}
