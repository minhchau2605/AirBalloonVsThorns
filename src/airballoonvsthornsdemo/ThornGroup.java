/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airballoonvsthornsdemo;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import module.QueueList;

/**
 *
 * @author Hadm
 */
public class ThornGroup {

    private QueueList<Thorns> Q_Thorns;

    private BufferedImage thornsImage, thornsImage2;

    public static int SIZE = 6;

    private int topThornsY = -350;
    private int bottomThornsY = 200;

    public Thorns getThorns(int i) {
        return Q_Thorns.get(i);
    }

    //Tạo độ cao ngẫu nhiên cho thorns
    public int getRandomY() {
        Random random = new Random();
        int a;
        a = random.nextInt(10);

        return a * 35;
    }

    public int getSize() {
        return SIZE;
    }

    public ThornGroup() {
        try {
            thornsImage = ImageIO.read(new File("Assets/thorn.png"));
            thornsImage2 = ImageIO.read(new File("Assets/thorn.png"));
        } catch (IOException ex) {
        }

        Q_Thorns = new QueueList<Thorns>();

        Thorns thorns;
        //Tạo 3 cặp ống khói 
        for (int i = 0; i < SIZE / 2; i++) {

            int deltaY = getRandomY();

            thorns = new Thorns(830 + i * 300, bottomThornsY + deltaY, 74, 400);
            Q_Thorns.push(thorns);

            thorns = new Thorns(830 + i * 300, topThornsY + deltaY, 74, 400);
            Q_Thorns.push(thorns);
        }

    }
    //reset Thorns ve vi tri cu sau khi GameOver
    public void resetThorns() {
        Q_Thorns = new QueueList<Thorns>();

        Thorns thorn;

        for (int i = 0; i < SIZE / 2; i++) {

            int deltaY = getRandomY();

            thorn = new Thorns(830 + i * 300, bottomThornsY + deltaY, 74, 400);
            Q_Thorns.push(thorn);

            thorn = new Thorns(830 + i * 300, topThornsY + deltaY, 74, 400);
            Q_Thorns.push(thorn);
        }
    }

    public void update() {
        for (int i = 0; i < SIZE; i++) {
            Q_Thorns.get(i).update();
        }
        //Sau khi cặp thorn đầu tiên đi hết màn hình, chuyển cặp thorn ra đằng sau
        //Thorn dưới lấy tọa độ X Thorn cuối cùng  + 300
        //Thorn trên lấy tọa độ Thorn dưới vừa được đưa ra sau
        if (Q_Thorns.get(0).getPosX() < -74) {

            int deltaY = getRandomY();

            Thorns thorns;
            thorns = Q_Thorns.pop();
            thorns.setPosX(Q_Thorns.get(4).getPosX() + 300);
            thorns.setPosY(bottomThornsY + deltaY);
            thorns.setIsBehindAirBalloon(false);
            Q_Thorns.push(thorns);

            thorns = Q_Thorns.pop();
            thorns.setPosX(Q_Thorns.get(4).getPosX());
            thorns.setPosY(topThornsY + deltaY);
            thorns.setIsBehindAirBalloon(false);
            Q_Thorns.push(thorns);
        }
    }

    public void paint(Graphics2D g2) {
        //Paint ra 1 cặp thorn trên và thorn dưới
        for (int i = 0; i < SIZE; i++) {
            if (i % 2 == 0) {
                g2.drawImage(thornsImage, (int) Q_Thorns.get(i).getPosX(), (int) Q_Thorns.get(i).getPosY(), null);
            } else {
                g2.drawImage(thornsImage2, (int) Q_Thorns.get(i).getPosX(), (int) Q_Thorns.get(i).getPosY(), null);
            }
        }
    }
}
