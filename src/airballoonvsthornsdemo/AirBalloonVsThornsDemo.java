/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airballoonvsthornsdemo;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import module.AFrameOnImage;
import module.Animation;
import module.GameScreen;

/**
 *
 * @author Hadm
 */
public class AirBalloonVsThornsDemo extends GameScreen {

    private static BufferedImage bg, AirBalloon_image, Thorns_image, begin_screen, gameover_screen;
    private AirBalloon AirBalloon;
    private Thorns Thorns;
    private ThornGroup ThornGroup;
    private Background BackGround;
    private Ground ground;
    private Animation AirBalloon_anim;
    //Gia toc roi g cua AirBalloon
    private static float g = 0.1f;
    private int scores;

    private int BEGIN_SCREEN = 0;
    private int GAMEPLAY_SCREEN = 1;
    private int GAMEOVER_SCREEN = 2;

    private int CurrentScreen = BEGIN_SCREEN;

    public static float getG() {
        return g;
    }

    public AirBalloonVsThornsDemo() {
        super(800, 600);
        try {
            AirBalloon_image = ImageIO.read(new File("Assets/airballoon_1.png"));
        } catch (IOException ex) {}
        
        AirBalloon_anim = new Animation(70);
        AFrameOnImage f;
        f = new AFrameOnImage(0, 0, 60, 60);
        AirBalloon_anim.AddFrame(f);
        f = new AFrameOnImage(60, 0, 60, 60);
        AirBalloon_anim.AddFrame(f);
        f = new AFrameOnImage(120, 0, 60, 60);
        AirBalloon_anim.AddFrame(f);
        f = new AFrameOnImage(60, 0, 60, 60);
        AirBalloon_anim.AddFrame(f);

        AirBalloon = new AirBalloon(350, 250, 50, 50);
        BackGround = new Background();
        ground = new Ground();
        //Thorns = new Thorns(700, 300, 70, 400);
        ThornGroup = new ThornGroup();
        BeginGame();
    }

    public static void main(String[] args) {
        new AirBalloonVsThornsDemo();
    }

    private void resetGame() {
        AirBalloon.setPos(380, 308);
        AirBalloon.setVt(0);
        AirBalloon.setLive(true);
        scores = 0;
        ThornGroup.resetThorns();
    }

    @Override
    public void GAME_UPDATE(long deltaTime) {
//        AirBalloon_anim.Update_Me(deltaTime);
//        AirBalloon.update(deltaTime);
        if (CurrentScreen == BEGIN_SCREEN) {
            resetGame();
        } else if (CurrentScreen == GAMEPLAY_SCREEN) {
            if(AirBalloon.getLive())
            AirBalloon_anim.Update_Me(deltaTime);
            AirBalloon.update(deltaTime);
            ground.update();
            ThornGroup.update();

            //Kiểm tra airballoon có cham vào thorn không, nếu có kết thúc trò chơi
            for (int i = 0; i < ThornGroup.getSize(); i++) {
                if (AirBalloon.getRect().intersects(ThornGroup.getThorns(i).getRect())) {
                    AirBalloon.setLive(false);
                    CurrentScreen = GAMEOVER_SCREEN;
                }
            }
            //Kiểm tra xem airballoon bay qua thorn chưa, nếu đã bay qua tăng Point lên 1
            for (int i = 0; i < ThornGroup.SIZE; i++) {
                if (AirBalloon.getPosX() > ThornGroup.getThorns(i).getPosX() && !ThornGroup.getThorns(i).getIsBehindAirBalloon()
                        && i % 2 == 0) {
                    scores++;
                    ThornGroup.getThorns(i).setIsBehindAirBalloon(true);
                }

            }
            //Kiem tra xem AirBalloon cham mat dat hay khong, neu cham ket thuc tro choi
            if (AirBalloon.getPosY() + AirBalloon.getH() > ground.getYGround()) {
                CurrentScreen = GAMEOVER_SCREEN;
            }
            //Khong cho AirBalloon bay vuot man hinh game
            if (AirBalloon.getPosY() < 0) {
                AirBalloon.setVt(0);
            }
        } else {

        }
    }

    @Override
    public void GAME_PAINT(Graphics2D g2) {

        if (BackGround != null) {
            BackGround.Paint(g2);
        }
//        g2.drawImage(Thorns_image, (int) Thorns.getPosX(), (int) Thorns.getPosY(), this);
        ThornGroup.paint(g2);
        if (ground != null) {
            ground.Paint(g2);
        }

        if (AirBalloon != null) {
            AirBalloon_anim.PaintAnims((int) AirBalloon.getPosX(), (int) AirBalloon.getPosY(), AirBalloon_image, g2, 0, 0);
            //AirBalloon.paint(g2);
        }
//        if (AirBalloon != null) {
//            AirBalloon.paint(g2);
//        }
        if (AirBalloon.getIsFlying()) {
            AirBalloon_anim.PaintAnims((int) AirBalloon.getPosX(), (int) AirBalloon.getPosY(), AirBalloon_image, g2, 0, 0);
        } else {
            AirBalloon_anim.PaintAnims((int) AirBalloon.getPosX(), (int) AirBalloon.getPosY(), AirBalloon_image, g2, 0, 0);
        }

        if (CurrentScreen == BEGIN_SCREEN) {
            g2.setColor(Color.red);
            // g2.drawString("Press space to play game", 200, 300);
            try {
                begin_screen = ImageIO.read(new File("Assets/beginscreen.png"));
            } catch (IOException ex) {
            }
            g2.drawImage(begin_screen, 250, 200, null);
            Font font = new Font("Cooper Std Black", Font.PLAIN, 24);
            g2.setFont(font);
            g2.setColor(Color.red);
            g2.drawString("Scores : " + scores, 30, 50);
        }
        if (CurrentScreen == GAMEPLAY_SCREEN) {
            Font font = new Font("Cooper Std Black", Font.PLAIN, 24);
            g2.setFont(font);
            g2.setColor(Color.red);
            g2.drawString("Scores : " + scores, 30, 50);
        }
        if (CurrentScreen == GAMEOVER_SCREEN) {
            g2.setColor(Color.red);
            try {
                gameover_screen = ImageIO.read(new File("Assets/gameover_screen.png"));
            } catch (IOException ex) {
            }
            g2.drawImage(gameover_screen, 250, 200, null);
            Font font = new Font("Cooper Std Black", Font.PLAIN, 27);
            g2.setFont(font);
            g2.setColor(Color.white);
            g2.drawString("Scores : " + scores, 360, 340);
        }
    }

    @Override
    public void KEY_ACTION(KeyEvent e, int Event) {
        if (Event == KEY_PRESSED) {
            //AirBalloon.fly();
            if (CurrentScreen == BEGIN_SCREEN) {
                CurrentScreen = GAMEPLAY_SCREEN;
            } else if (CurrentScreen == GAMEPLAY_SCREEN) {
                if (AirBalloon.getLive()) {
                    AirBalloon.fly();
                }
            } else if (CurrentScreen == GAMEOVER_SCREEN) {
                CurrentScreen = BEGIN_SCREEN;
            }
        }
    }
}
