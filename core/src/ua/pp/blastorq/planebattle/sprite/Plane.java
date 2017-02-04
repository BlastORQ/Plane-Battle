package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import ua.pp.blastorq.planebattle.loader.ResourceLoader;

public class Plane extends Sprite {
    Vector2 previousPosition;
    public float accel = 0;
    public Plane(Texture texture) {
        super(texture);
        previousPosition = new Vector2(getX(), getY());
        setPosition((Gdx.graphics.getWidth() /2) - (getWidth()/2), 64);
    }
    public void left(){
        this.accel -= 0.09;
        if (this.accel < -2) {
            this.accel = -2;
        }
    }
    public void right(){
        this.accel += 0.09;
        if (this.accel > 2) {
            this.accel = 2;
        }
    }
    public void shot(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = this.getX() + (this.getWidth() / 2) - (ResourceLoader.Bullet.getWidth() / 2);
        raindrop.y = 64;
        raindrop.width = 64;
        raindrop.height = 64;
        ResourceLoader.raindrops.add(raindrop);
    }
    public void render(float dt){
        if (Math.abs(this.accel) >= 0.07) {
            this.accel /= 1.8;
        } else {
            this.accel = 0;
        }

        this.setPosition(this.getX() + (300 * 10 * this.accel * dt), this.getY());
        if (this.getX() < 0) {
            this.accel *= -0.9;
            this.setPosition(0, this.getY());
        } else if (this.getX() + this.getWidth() > Gdx.graphics.getWidth()) {
            this.accel *= -0.9;
            this.setPosition(Gdx.graphics.getWidth() - 64 - 1, this.getY());
        }
    }
}
