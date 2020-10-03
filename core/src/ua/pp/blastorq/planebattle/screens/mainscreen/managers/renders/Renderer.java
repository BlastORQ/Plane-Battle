package ua.pp.blastorq.planebattle.screens.mainscreen.managers.renders;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface Renderer {

    void render(float delta);

    void resize(int width, int height);

    Stage getStage();

    void show();
}
