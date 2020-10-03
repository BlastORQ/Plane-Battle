package ua.pp.blastorq.planebattle.screens.mainscreen.listeners;

import com.badlogic.gdx.scenes.scene2d.Stage;

import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.actors.Button;
import ua.pp.blastorq.planebattle.screens.mainscreen.listeners.impl.ButtonDisableAdsListener;
import ua.pp.blastorq.planebattle.screens.mainscreen.listeners.impl.ButtonMuteListener;
import ua.pp.blastorq.planebattle.screens.mainscreen.listeners.impl.ButtonUnmuteListener;
import ua.pp.blastorq.planebattle.screens.mainscreen.listeners.impl.StartButtonForegroundListener;
import ua.pp.blastorq.planebattle.screens.mainscreen.managers.MainMenuScreenObjectsManager;

public class MainScreenListenerManager {

    private PlaneBattle game;

    private Button startButtonForeground;
    private Button buttonMute;
    private Button buttonUnmute;
    private Button btnDisableAds;
    private Stage stage;

    public MainScreenListenerManager(PlaneBattle game, MainMenuScreenObjectsManager mainMenuScreenObjectsManager, Stage stage) {
        this.game = game;
        this.startButtonForeground = mainMenuScreenObjectsManager.getStartButtonForeground();
        this.buttonMute = mainMenuScreenObjectsManager.getButtonMute();
        this.buttonUnmute = mainMenuScreenObjectsManager.getButtonUnmute();
        this.btnDisableAds = mainMenuScreenObjectsManager.getBtnDisableAds();
        this.stage = stage;
    }

    public void initListeners() {
        startButtonForeground.addListener(new StartButtonForegroundListener(game));
        buttonMute.addListener(new ButtonMuteListener(buttonMute, buttonUnmute, stage));
        buttonUnmute.addListener(new ButtonUnmuteListener(buttonMute, buttonUnmute, stage));
        btnDisableAds.addListener(new ButtonDisableAdsListener(game));
    }
}
