package ua.pp.blastorq.planebattle.sprite;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import ua.pp.blastorq.planebattle.loader.data;

public class Bullets extends Array<Bullet> {
    public long lastAddPlayer, lastAddBot;
    public void render(){
        if(this.size==0)return;
        for (Bullet bullet : this) {
            data.batch.draw(data.getBulletTexture(), bullet.x, bullet.y, 64, 64);
            bullet.calculate(Gdx.graphics.getDeltaTime());
            if(bullet.isBot() && Intersector.overlaps(data.player.getBoundingRectangle(), bullet)){
                this.onOverlap(false);
                this.removeValue(bullet, true);
            }else if(!bullet.isBot() && Intersector.overlaps(data.bot.getBoundingRectangle(), bullet)){
                this.onOverlap(true);
                this.removeValue(bullet, true);
            }else if(bullet.getY() > data.vh-150){
                this.removeValue(bullet, true);
            }
        }
    }
    void onOverlap(boolean isBot){
        if(isBot){
            data.bot.setHp(data.bot.getHp()-0.02f);
            if(Math.random()<0.2){
                data.bot.shot();
            }
        }else{
            data.player.setHp(data.player.getHp()-0.1f);
        }
    }
    void append(Bullet b, boolean bot){
        long time = TimeUtils.nanoTime()/1000000;
        if(bot){
            if(this.lastAddBot < time-200){
                this.lastAddBot = time;
                this.add(b);
            }
        }else{
            if(this.lastAddPlayer < time-200){
                this.lastAddPlayer = time;
                this.add(b);
            }
        }
    }
    public void reset() {
        this.clear();
    }
}
