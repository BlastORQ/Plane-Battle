package ua.pp.blastorq.planebattle.screens.mainscreen.listeners.impl;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.pp.blastorq.planebattle.loader.data;
import ua.pp.blastorq.planebattle.screens.GameScreen;

public class StartButtonForegroundListener extends ClickListener {

    private Game game;

    public StartButtonForegroundListener(Game game) {
        this.game = game;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        game.setScreen(new GameScreen());
        data.menuAudio.stop();

        super.touchUp(event, x, y, pointer, button);
    }
}
