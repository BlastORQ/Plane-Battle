package ua.pp.blastorq.planebattle.screens.renders;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.actors.Button;
import ua.pp.blastorq.planebattle.loader.data;
import ua.pp.blastorq.planebattle.objects.MovingBackground;
import ua.pp.blastorq.planebattle.screens.managers.MainMenuScreenObjectsManager;

import static ua.pp.blastorq.planebattle.loader.data.camera;

public class MainMenuScreenRender implements Renderer {

    private Button startButtonForeground;
    private Button buttonMute;
    private Button buttonUnmute;
    private Button btnDisableAds;
    private BitmapFont playButtonText;

    private MainMenuScreenObjectsManager mainMenuScreenObjectsManager;

    private Stage stage;
    private SpriteBatch batch;
    private PlaneBattle planeBattle;
    private final Texture backgroundTexture;
    private final MovingBackground frontBackground;
    private final MovingBackground backBackground;

    public MainMenuScreenRender(MainMenuScreenObjectsManager mainMenuScreenObjectsManager, PlaneBattle planeBattle) {
        this.mainMenuScreenObjectsManager = mainMenuScreenObjectsManager;
        Viewport viewport = new StretchViewport(data.vw, data.vh);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, data.vw, data.vh);

        batch = new SpriteBatch();
        stage = new Stage(viewport);

        Button startButtonBackground = mainMenuScreenObjectsManager.getStartButtonBackground();
        startButtonForeground = mainMenuScreenObjectsManager.getStartButtonForeground();
        buttonMute = mainMenuScreenObjectsManager.getButtonMute();
        buttonUnmute = mainMenuScreenObjectsManager.getButtonUnmute();
        btnDisableAds = mainMenuScreenObjectsManager.getBtnDisableAds();
        Button buttonShare = mainMenuScreenObjectsManager.getButtonShare();
        playButtonText = mainMenuScreenObjectsManager.getPlayButtonText();

        backgroundTexture = mainMenuScreenObjectsManager.getBackgroundTexture();
        frontBackground = mainMenuScreenObjectsManager.getFrontBackground();
        backBackground = mainMenuScreenObjectsManager.getBackBackground();

        this.planeBattle = planeBattle;

        stage.addActor(startButtonBackground);
        stage.addActor(startButtonForeground);
        stage.addActor(buttonMute);
        stage.addActor(btnDisableAds);
        stage.addActor(buttonShare);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        Preferences prefs = Gdx.app.getPreferences("soundSettings");
        if(!prefs.contains("enabled")){
            prefs.putBoolean("enabled", true);
        }
        prefs.flush();
        if(prefs.getBoolean("enabled")) {
            data.menuAudio.play();
            buttonUnmute.remove();
            stage.addActor(buttonMute);
        }else{
            buttonMute.remove();
            stage.addActor(buttonUnmute);
        }
    }

    @Override
    public void render(float delta) {
        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(backgroundTexture, frontBackground.getX(), frontBackground.getY(), frontBackground.getWidth() + 150, frontBackground.getHeight() + 150);
        batch.draw(backgroundTexture, backBackground.getX(), backBackground.getY(), backBackground.getWidth() + 150, backBackground.getHeight() + 150);
        batch.end();

        stage.act(delta);
        stage.draw();

        batch.begin();
        playButtonText.draw(batch, "PLAY", startButtonForeground.getX() + (startButtonForeground.getWidth()/3) , startButtonForeground.getY() + (startButtonForeground.getHeight()/1.5f));
        batch.end();

        mainMenuScreenObjectsManager.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        data.vw = Gdx.graphics.getWidth();
        data.vh = Gdx.graphics.getHeight();
    }


    @Override
    public Stage getStage() {
        return stage;
    }
}
