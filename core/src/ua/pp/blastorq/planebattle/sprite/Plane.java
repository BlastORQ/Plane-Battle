package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Plane extends Sprite {
    public Plane(Texture texture) {
        super(texture);

        setPosition((Gdx.graphics.getWidth() /2) - (getWidth()/2), 128);

    }
}
