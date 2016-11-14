package ua.pp.blastorq.planebattle.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class Right extends Actor {
    private Texture right;

    public Right(Texture right) {
        this.right = right;
        setBounds(Gdx.graphics.getWidth() - 128, 0, 128, 128);
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(right, Gdx.graphics.getWidth() - 128, 0, 128, 128);

        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
