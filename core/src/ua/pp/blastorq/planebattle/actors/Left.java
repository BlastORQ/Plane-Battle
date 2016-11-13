package ua.pp.blastorq.planebattle.actors;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
/**
 * Created by vector on 11.11.2016.
 */

public class Left extends Actor {
    Texture left;
    public Left(Texture left) {
        this.left = left;
        setBounds(0 , 0, 128, 128);
    }
    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(left, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
