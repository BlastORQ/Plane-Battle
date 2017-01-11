package ua.pp.blastorq.planebattle.actors;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class StartButton extends Actor {
    Texture image;
    private float x, y, width, height;

    public StartButton(Texture image, float x, float y, float width, float height) {
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.setPosition(x, y);
        setBounds(x, y, width, height);

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(image, x, y, width, height);
    }
}
