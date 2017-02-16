package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import ua.pp.blastorq.planebattle.loader.ResourceLoader;

public class Bullet extends Rectangle {
    private int direction;
     Bullet(float x, float y, int dir){
        this.x = x;
        this.y = y;
        this.direction = dir;

    }

    public float getY(){return this.y;}
    void calculate(float dt){
        ResourceLoader.bulletRectangle.y = this.y -= 1000 * dt * this.direction;
        this.y -= 0.3 * dt * this.direction;
        ResourceLoader.bulletRectangle.y -= 1000 * dt * this.direction;
    }
}