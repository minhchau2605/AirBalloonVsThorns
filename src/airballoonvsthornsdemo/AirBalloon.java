/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airballoonvsthornsdemo;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import module.GameScreen;
import module.Objects;

/**
 *
 * @author Hadm
 */
public class AirBalloon extends Objects {

    private static BufferedImage AirBalloon_Image;
//    private static int x;
//    private static int y;
    private float vt = 0;
    private Rectangle rect;
    private boolean isFlying = false;
    private boolean isLive = true;

    public AirBalloon(int x, int y, int w, int h) {
        super(x, y, w, h);
//        try {
//            AirBalloon_Image = ImageIO.read(new File("Assets/airballoon_.png"));
//        } catch (IOException ex) {
//        }
        rect = new Rectangle(x, y, w, h);
    }

//    public void paint(Graphics2D g2) {
//        g2.drawImage(AirBalloon_Image, (int) this.getPosX(), (int) this.getPosY(), null);
//    }

    public void setVt(float vt) {

        this.vt = vt;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setLive(boolean b) {
        isLive = b;
    }
    public boolean getLive(){
        return isLive;
    }
    public boolean getIsFlying() {
        return isFlying;
    }
    
    public void update(long deltaTime) {
        vt += AirBalloonVsThornsDemo.getG();

        this.setPosY(this.getPosY() + vt);
        this.rect.setLocation((int) this.getPosX()-10, (int) this.getPosY()-10);

        if(vt < 0) isFlying = true;
        else isFlying = false;
    }

    public void fly() {
        vt = -3;
        //flapSound.play();
    }
}
