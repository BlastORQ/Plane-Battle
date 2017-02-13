package ua.pp.blastorq.planebattle.objects;


import com.badlogic.gdx.math.Vector2;

 class Moving {
    private Vector2 position;
    private Vector2 velocity;
    protected int width;
    protected int height;
    private boolean isMovingDown;

     Moving(float x, float y, int width, int height, float movSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(0, movSpeed);
        this.width = width;
        this.height = height;
        isMovingDown = false;
    }
    void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        if (position.y + height < 0) {
            isMovingDown = true;
        }
    }

     void reset(float newY) {
        position.y = newY;
        isMovingDown = false;
    }


     boolean isScrolledLeft() {
        return isMovingDown;
    }

     float getTailY() {
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
