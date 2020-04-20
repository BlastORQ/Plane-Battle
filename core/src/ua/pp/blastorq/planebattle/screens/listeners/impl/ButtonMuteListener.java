package ua.pp.blastorq.planebattle.screens.listeners.impl;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import ua.pp.blastorq.planebattle.actors.Button;
import ua.pp.blastorq.planebattle.loader.data;

public class ButtonMuteListener extends ClickListener {

    private final Button buttonMute, buttonUnmute;
    private final Stage stage;

    public ButtonMuteListener(Button buttonMute, Button buttonUnmute, Stage stage) {
        this.buttonMute = buttonMute;
        this.buttonUnmute = buttonUnmute;
        this.stage = stage;
    }

    @Override
    public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
        data.menuAudio.pause();
        Preferences preferences = Gdx.app.getPreferences("soundSettings");
        preferences.putBoolean("enabled", false);
        preferences.flush();
        buttonMute.remove();
        stage.addActor(buttonUnmute);
        super.touchUp(event, x, y, pointer, button);
    }
}
