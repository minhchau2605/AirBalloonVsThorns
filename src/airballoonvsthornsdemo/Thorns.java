/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package airballoonvsthornsdemo;

import java.awt.Rectangle;
import module.Objects;

/**
 *
 * @author Hadm
 */
public class Thorns extends Objects {

    private Rectangle rect;

    //Kiểm tra xem thorns nằm phía sau airballoon chưa
    private boolean isBehindAirBalloon = false;

    public Thorns(int x, int y, int w, int h) {
        super(x, y, w, h);
        rect = new Rectangle(x, y, w, h);
    }

    public Rectangle getRect() {
        return rect;
    }

    public boolean getIsBehindAirBalloon() {
        return isBehindAirBalloon;
    }

    public void setIsBehindAirBalloon(boolean isBehindAirBalloon) {
        this.isBehindAirBalloon = isBehindAirBalloon;
    }

    public void update() {
        setPosX(getPosX() - 3);
        rect.setLocation((int) this.getPosX(), (int) this.getPosY());
    }
}
