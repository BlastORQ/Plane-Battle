package ua.pp.blastorq.planebattle.sprite;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import org.w3c.dom.css.Rect;

import ua.pp.blastorq.planebattle.screens.GameOver;
import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.loader.ResourceLoader;

public class Bullets extends Array<Bullet> {
    private long lastAddPlayer, lastAddBot;
    private PlaneBattle planeBattle;
    public Bullets(PlaneBattle pb){
        planeBattle = pb;
    }

    public void render(){
        for (Bullet bullet : this) {
            ResourceLoader.batch.draw(ResourceLoader.getBullet(), bullet.x, bullet.y, 64, 64);
            bullet.calculate(Gdx.graphics.getDeltaTime());
            ResourceLoader.bulletRectangle.x = bullet.x;
            ResourceLoader.bulletRectangle.y = bullet.y ;
            Rectangle boundingRect2 = ResourceLoader.player.getBoundingRectangle();
            Rectangle bot = ResourceLoader.bot.getBoundingRectangle();
            if (Intersector.overlaps(bullet, boundingRect2)) {
                Gdx.app.log("Player", "lap");
                planeBattle.setScreen(new GameOver());
            }else if(Intersector.overlaps(bullet, bot)){
                Gdx.app.log("Bot", "Overlaps");
                planeBattle.setScreen(new GameOver());
            }
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
