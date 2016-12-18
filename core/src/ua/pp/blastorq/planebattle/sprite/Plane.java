package ua.pp.blastorq.planebattle.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Plane extends Sprite {
    Vector2 previousPosition;
    public Plane(Texture texture) {
        super(texture);
        previousPosition = new Vector2(getX(), getY());
        setPosition((Gdx.graphics.getWidth() /2) - (getWidth()/2), 64);
    }
    public boolean hasMoved(){
        if(previousPosition.x != getX() || previousPosition.y != getY()){
            previousPosition.x = getX();
            previousPosition.y = getY();
            return true;
        }
        return false;
    }
}
