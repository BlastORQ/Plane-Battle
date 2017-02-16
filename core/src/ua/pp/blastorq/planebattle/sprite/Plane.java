package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;

import ua.pp.blastorq.planebattle.loader.ResourceLoader;

public class Plane extends Sprite {
    private float acceleration = 0;
    private boolean isBot = false;
    public static int[] a = new int[10];
    private long lastBotMoving;
    private long second = 1000000000;
    private long lastShot = 0;
    private int count = 0;
    boolean ak;
    public Plane(Texture texture, boolean bot) {
        super(texture);
        setPosition((Gdx.graphics.getWidth() /2) - (getWidth()/2), 64);
        this.isBot = bot;
    }

    private void _calculateBot() {
        for (int i = 0; i <= 9; i++) {
            a[i] = i;
        }
        long time = TimeUtils.nanoTime() / 1000000;
            if(lastBotMoving <time -20 ){
                lastBotMoving = time;
        int random = MathUtils.random(0, 9);
        if (a[random] == 0 || a[random] == 1 || a[random] == 2 || a[random] == 3) {
            this.left();
        } else if (a[random] == 4 || a[random] == 5 || a[random] == 6 || a[random] == 7 || a[random] == 8) {
            this.right();
        }
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
        long time = TimeUtils.nanoTime();

        if(time - second*5 > lastShot && count >=0){
            count++;
            if(count==1){
                lastShot = time;
            }else if(count>2) {
                lastShot = time;
                this.shot();
                count = 5;
            }
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
