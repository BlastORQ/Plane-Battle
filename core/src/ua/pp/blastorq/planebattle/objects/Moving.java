package ua.pp.blastorq.planebattle.objects;


import com.badlogic.gdx.math.Vector2;

public class Moving {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;
    protected int height;
    protected boolean isMovingDown;

    public Moving(float x, float y, int width, int height, float movSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, movSpeed);
        this.width = width;
        this.height = height;
        isMovingDown = false;
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        if (position.y + height < 0) {
            isMovingDown = true;
        }
    }

    public void reset(float newY) {
        position.y = newY;
        isMovingDown = false;
    }

    public void stop() {
        velocity.y = 0;
    }

    public boolean isScrolledLeft() {
        return isMovingDown;
    }

    public float getTailY() {
        return position.y + height;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
