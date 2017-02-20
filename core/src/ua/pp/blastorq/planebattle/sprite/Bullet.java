package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.math.Rectangle;

public class Bullet extends Rectangle {
    private float directionX = 0, directionY = 0;
    private boolean bot = false;
    Bullet(float x, float y, float dirX, float dirY){
        this.x = x;
        this.y = y;
        this.directionX = dirX;
        this.directionY = dirY;
        this.bot = directionY > 0;
    }
    void calculate(float dt){
        this.x -= 500 * dt * this.directionX;
        this.y -= 1250 * dt * this.directionY;
    }
    boolean isBot(){return this.bot;}
}