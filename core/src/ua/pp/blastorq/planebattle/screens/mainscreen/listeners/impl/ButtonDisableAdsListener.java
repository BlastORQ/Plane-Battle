package ua.pp.blastorq.planebattle.screens.mainscreen.listeners.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.pp.blastorq.planebattle.PlaneBattle;

public class ButtonDisableAdsListener extends ClickListener {

    private final PlaneBattle game;

    public ButtonDisableAdsListener(PlaneBattle game) {
        this.game = game;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        if (!game.getDesktop()) {
            game.getB().OnClick();
        } else {
            Gdx.app.log("Desktop", "RUN");
        }
        super.touchUp(event, x, y, pointer, button);
    }
}
