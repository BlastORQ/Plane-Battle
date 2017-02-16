package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import ua.pp.blastorq.planebattle.loader.data;

public class Plane extends Sprite {
    private float acceleration = 0;
    private boolean isBot = false;
    public boolean canShut;

    public Plane(Texture texture, boolean bot) {
        super(texture);
        setPosition((Gdx.graphics.getWidth() /2) - (getWidth()/2), 64);
        this.isBot = bot;
        this.canShut = !bot;
    }

    private void _calculateBot(){
        if(data.bot.getX() > data.player.getX() && Math.random()<0.4){
            this.left();
        }else if(data.bot.getX() < data.player.getX() && Math.random()<0.4){
            this.right();
        }
        if(this.canShut && Math.random()<0.02){
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
            this.getX() + 32,
            this.getY(),
            this.isBot ? 1 : -1
        );
        data.bullets.append(bullet, this.isBot);
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
        } else if (this.getX() > data.vw - this.getWidth() - 1) {
            this.acceleration *= -0.9;
            this.setPosition(data.vw - this.getWidth() - 1, this.getY());
        }
    }
    public void render(){
        this.draw(data.batch);
    }
}
