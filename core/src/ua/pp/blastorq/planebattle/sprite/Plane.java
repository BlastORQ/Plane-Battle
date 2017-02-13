package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

import ua.pp.blastorq.planebattle.loader.ResourceLoader;

public class Plane extends Sprite {
    private float Acceleration = 0;
    public Plane(Texture texture) {
        super(texture);
        setPosition((Gdx.graphics.getWidth() /2) - (getWidth()/2), 64);
    }
    public void left(){
        this.Acceleration -= 0.09;
        if (this.Acceleration < -2) {
            this.Acceleration = -2;
        }
    }
    public void right(){
        this.Acceleration += 0.09;
        if (this.Acceleration > 2) {
            this.Acceleration = 2;
        }
    }
    public void shot(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = this.getX() + (this.getWidth() / 2) - (ResourceLoader.player.getWidth() / 2);
        raindrop.y = 64;
        raindrop.width = 64;
        raindrop.height = 64;
        ResourceLoader.raindrops.add(raindrop);
    }
    public void render(float dt){
        if (Math.abs(this.Acceleration) >= 0.07) {
            this.Acceleration /= 1.8;
        } else {
            this.Acceleration = 0;
        }

        this.setPosition(this.getX() + (300 * 10 * this.Acceleration * dt), this.getY());
        if (this.getX() < 0) {
            this.Acceleration *= -0.9;
            this.setPosition(0, this.getY());
        } else if (this.getX() + this.getWidth() > Gdx.graphics.getWidth()) {
            this.Acceleration *= -0.9;
            this.setPosition(Gdx.graphics.getWidth() - 64 - 1, this.getY());
        }
    }
}
