
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
    public static BufferedImage brick[];
    public static BufferedImage sprites;
    public static BufferedImage ball;
    public static BufferedImage paddle;
    public static BufferedImage add;
    public static BufferedImage explosion[];
    
    public static BufferedImage life;
    public static SoundClip breakBrick;
    public static SoundClip backmusic;
    public static SoundClip power;
    
    /**
     * initializing the images of the game
     */
    public static void init(){
        //load images
        background = ImageLoader.loadImage("/images/black.png");
        add = ImageLoader.loadImage("/images/add.png");
        life = ImageLoader.loadImage("/images/heart.png");
        ball = ImageLoader.loadImage("/images/ballo.png");
        paddle = ImageLoader.loadImage("/images/paddle.png");
        
        //load sounds
        breakBrick = new SoundClip("/sounds/bang.wav");
        backmusic = new SoundClip("/sounds/music.wav");
        power = new SoundClip("/sounds/cling.wav");
        
       // load images for brick animation
       brick = new BufferedImage[12];
       int k = 0;
       for(int i =1 ; i<=6; i++){
           
           brick[k++] = ImageLoader.loadImage("/images/Diapositiva"+String.valueOf(i)+ ".JPG");
           brick[12-k] = ImageLoader.loadImage("/images/Diapositiva"+String.valueOf(i)+ ".JPG");
       }
        
        // load images for exposion animation
        explosion = new BufferedImage[40];
        for(int i = 0; i<40; i++){
            if(i+1<10)
            explosion[i]= ImageLoader.loadImage("/images/image_part_00"+ String.valueOf(i+1)+ ".png");
            else 
             explosion[i] = ImageLoader.loadImage("/images/image_part_0"+ String.valueOf(i+1)+ ".png");
        }
        
       
    }
}
