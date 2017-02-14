package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Rectangle {
    private int direction;
    Bullet(float x, float y, int dir){
        this.x = x;
        this.y = y;
        this.direction = dir;

    }
    void calculate(float dt){
        this.y -= 1000 * dt * this.direction;
    }
}