package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import ua.pp.blastorq.planebattle.loader.ResourceLoader;

public class Plane extends Sprite {
    private float acceleration = 0;
    private boolean isBot = false;

    public Plane(Texture texture, boolean bot) {
        super(texture);
        setPosition((Gdx.graphics.getWidth() /2) - (getWidth()/2), 64);
        this.isBot = bot;
    }

    private void _calculateBot(){
        if(Math.random()<0.3){
            this.left();
        }else if(Math.random()<0.3){
            this.right();
        }else if(Math.random()<0.1){
            this.shot();
        }
    }
    public void left(){
        this.acceleration -= 0.09;
        if (this.acceleration < -2) {
            this.acceleration = -2;
        }
    }
    public void right(){
        this.acceleration += 0.09;
        if (this.acceleration > 2) {
            this.acceleration = 2;
        }
    }
    public void shot(){
        Bullet bullet = new Bullet(
            this.getX() + (this.getWidth() / 2) - (ResourceLoader.player.getWidth() / 2),
            this.getY(),
            this.isBot ? 1 : -1
        );
        ResourceLoader.bullets.append(bullet, this.isBot);
    }
    public void calculate(float dt){
        if(this.isBot){
            this._calculateBot();
        }
        if (Math.abs(this.acceleration) >= 0.07) {
            this.acceleration /= 1.8;
        } else {
            this.acceleration = 0;
        }

        this.setPosition(this.getX() + (300 * 10 * this.acceleration * dt), this.getY());
        if (this.getX() < 0) {
            this.acceleration *= -0.9;
            this.setPosition(0, this.getY());
        } else if (this.getX() + this.getWidth() > Gdx.graphics.getWidth()) {
            this.acceleration *= -0.9;
            this.setPosition(Gdx.graphics.getWidth() - 64 - 1, this.getY());
        }
    }
    public void render(){
        this.draw(ResourceLoader.batch);
    }
}
