package ua.pp.blastorq.planebattle.objects;


import com.badlogic.gdx.Gdx;

public class MovHandler {
    public static final int MOV_SPEED = -59;
    private Background frontBackground, backBackground;

    public MovHandler(float yPos) {
        frontBackground = new Background(0, yPos, Gdx.graphics.getWidth() + 150, Gdx.graphics.getHeight() + 150, MOV_SPEED);
        backBackground = new Background(0, frontBackground.getTailY(), Gdx.graphics.getWidth() + 150, Gdx.graphics.getHeight() + 150, MOV_SPEED);

    }

    public void update(float delta) {
        frontBackground.update(delta);
        backBackground.update(delta);
        if (frontBackground.isScrolledLeft()) {
            frontBackground.reset(backBackground.getTailY());
        } else if (backBackground.isScrolledLeft()) {
            backBackground.reset(frontBackground.getTailY());
        }
    }

    public Background getFrontBackground() {
        return frontBackground;
    }

    public Background getBackBackground() {
        return backBackground;
    }

    private void stop() {
        frontBackground.stop();
        backBackground.stop();
    }
}

