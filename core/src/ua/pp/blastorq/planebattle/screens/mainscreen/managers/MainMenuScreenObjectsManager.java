package ua.pp.blastorq.planebattle.screens.mainscreen.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import ua.pp.blastorq.planebattle.actors.Button;
import ua.pp.blastorq.planebattle.loader.data;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.objects.MovingBackground;

public class MainMenuScreenObjectsManager {

    private BitmapFont playButtonText;
    private MovingBackground frontBackground, backBackground;
    private Texture backgroundTexture;
    private Button startButtonForeground;
    private Button startButtonBackground;
    private Button buttonMute;
    private Button buttonUnmute;
    private Button btnDisableAds;
    private Button buttonShare;
    private MovHandler movHandler;

    public MainMenuScreenObjectsManager() {

        Texture unmuteTexture = new Texture("unmuteTexture.png");
        backgroundTexture = new Texture("bg.png");
        Texture muteTexture = new Texture("muteTexture.png");
        Texture disableAdsTexture = new Texture("disableAdsTexture.png");
        Texture shareTexture = new Texture("shareTexture.png");
        Texture buttonBackgroundTexture = new Texture("buttonBackgroundTexture.png");
        Texture buttonForegroundTexture = new Texture("buttonForegroundTexture.png");
        playButtonText = new BitmapFont(Gdx.files.internal("font/font.fnt"));

        Gdx.app.log("SCALE", String.valueOf(data.scale));

        playButtonText.getData().setScale(2.0f, 2.0f);

        float sizeOfTexture = muteTexture.getHeight() * data.scale;

        movHandler = new MovHandler(0, -20);
        frontBackground = movHandler.getFrontMovingBackground();
        backBackground = movHandler.getBackMovingBackground();

        startButtonBackground = new Button(buttonBackgroundTexture, 64, data.vh / 2f, data.vw - 128, 128 * data.scale);
        startButtonForeground = new Button(buttonForegroundTexture, startButtonBackground.getX()+8, startButtonBackground.getY() + 8, data.vw - 128 - 16, 128*data.scale - 16);

        buttonMute = new Button(muteTexture, startButtonBackground.getX(), startButtonBackground.getY()- sizeOfTexture -16, sizeOfTexture, sizeOfTexture);
        buttonUnmute = new Button(unmuteTexture, buttonMute.getX(), buttonMute.getY(), sizeOfTexture, sizeOfTexture);

        btnDisableAds = new Button(disableAdsTexture, buttonMute.getX()+ buttonMute.getWidth() + (startButtonBackground.getWidth()/8), buttonMute.getY(), sizeOfTexture, sizeOfTexture);

        buttonShare = new Button(shareTexture, btnDisableAds.getX() + btnDisableAds.getWidth() + (startButtonBackground.getWidth() / 8), btnDisableAds.getY(), sizeOfTexture, sizeOfTexture);
    }

    public BitmapFont getPlayButtonText() {
        return playButtonText;
    }

    public MovingBackground getFrontBackground() {
        return frontBackground;
    }

    public MovingBackground getBackBackground() {
        return backBackground;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public Button getStartButtonForeground() {
        return startButtonForeground;
    }

    public Button getButtonMute() {
        return buttonMute;
    }

    public Button getButtonUnmute() {
        return buttonUnmute;
    }

    public Button getBtnDisableAds() {
        return btnDisableAds;
    }

    public Button getButtonShare() {
        return buttonShare;
    }

    public Button getStartButtonBackground() {
        return startButtonBackground;
    }

    public void update(float delta) {
        movHandler.update(delta);
    }
}
