package ua.pp.blastorq.planebattle.objects;


import com.badlogic.gdx.Gdx;

public class MovHandler {
    private MovingBackground frontMovingBackground, backMovingBackground;

    public MovHandler(float yPos, int MOV_SPEED) {
        frontMovingBackground = new MovingBackground(0, yPos, Gdx.graphics.getWidth() + 150, Gdx.graphics.getHeight() + 150, MOV_SPEED);
        backMovingBackground = new MovingBackground(0, frontMovingBackground.getTailY(), Gdx.graphics.getWidth() + 150, Gdx.graphics.getHeight() + 150, MOV_SPEED);

    }

    public void update(float delta) {
        frontMovingBackground.update(delta);
        backMovingBackground.update(delta);
        if (frontMovingBackground.isScrolledLeft()) {
            frontMovingBackground.reset(backMovingBackground.getTailY());
        } else if (backMovingBackground.isScrolledLeft()) {
            backMovingBackground.reset(frontMovingBackground.getTailY());
        }
    }

    public MovingBackground getFrontMovingBackground() {
        return frontMovingBackground;
    }

    public MovingBackground getBackMovingBackground() {
        return backMovingBackground;
    }


}

