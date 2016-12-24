package ua.pp.blastorq.planebattle.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
public class Blue extends Actor {
    Texture texture;

    public Blue(Texture texture) {
        this.texture = texture;
        setBounds((Gdx.graphics.getWidth()/2) -200, (Gdx.graphics.getHeight()/2) + 100, 64, 64);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
        super.draw(batch, parentAlpha);
    }
}
