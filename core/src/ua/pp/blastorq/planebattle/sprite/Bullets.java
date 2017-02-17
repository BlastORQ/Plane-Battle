package ua.pp.blastorq.planebattle.sprite;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import ua.pp.blastorq.planebattle.loader.data;
import ua.pp.blastorq.planebattle.screens.MainMenuScreen;

public class Bullets extends Array<Bullet> {
    public long lastAddPlayer, lastAddBot;
    public void render(){
        for (Bullet bullet : this) {
            data.batch.draw(data.getBulletTexture(), bullet.x, bullet.y, 64, 64);
            bullet.calculate(Gdx.graphics.getDeltaTime());
            if(Intersector.overlaps(data.player.getBoundingRectangle(), bullet)){
                this.onOverlap(false);
            }else if(Intersector.overlaps(data.bot.getBoundingRectangle(), bullet)){
                this.onOverlap(true);
            }
        }
    }
    void onOverlap(boolean isBot){
        if(isBot){
            data.bot.shot();
        }else{
            data.player.shot();
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
}
