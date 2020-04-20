package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Screen;

import ua.pp.blastorq.planebattle.Bill;
import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.screens.listeners.MainScreenListenerManager;
import ua.pp.blastorq.planebattle.screens.managers.MainMenuScreenObjectsManager;
import ua.pp.blastorq.planebattle.screens.renders.MainMenuScreenRender;
import ua.pp.blastorq.planebattle.screens.renders.Renderer;

public class MainMenuScreen implements Screen, Bill {

    private Renderer renderer;

    public MainMenuScreen(PlaneBattle planeBattle){
        MainMenuScreenObjectsManager mainMenuScreenObjectsManager = new MainMenuScreenObjectsManager();
        renderer = new MainMenuScreenRender(mainMenuScreenObjectsManager, planeBattle);
        MainScreenListenerManager mainScreenListenerManager = new MainScreenListenerManager(planeBattle,mainMenuScreenObjectsManager, renderer.getStage());
        mainScreenListenerManager.initListeners();
    }
    @Override
    public void show() {
        renderer.show();
    }

    @Override
    public void render(float delta) {
        renderer.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        renderer.resize(width, height);
    }


    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void OnClick() {

    }
}
