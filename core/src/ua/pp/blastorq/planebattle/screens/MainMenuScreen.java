package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ua.pp.blastorq.planebattle.actors.Button;
import ua.pp.blastorq.planebattle.loader.data;
import ua.pp.blastorq.planebattle.Bill;
import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.objects.MovingBackground;

public class MainMenuScreen implements Screen, Bill {
    private BitmapFont font;
    private OrthographicCamera camera;
    private MovingBackground frontBackground, backBackground;
    private MovHandler movHandler;
    private PlaneBattle pb;
    private Texture backgroundTexture;
    private SpriteBatch batch;
    private Stage stage;
    private Button startButtonForeground;
    private Button buttonMute;
    private Button buttonUnmute;
    private Button btnDisableAds;

    public MainMenuScreen(PlaneBattle planeBattle){
        this.pb = planeBattle;

        Texture unmuteTexture = new Texture("unmuteTexture.png");
        backgroundTexture = new Texture("bg.png");
        Texture muteTexture = new Texture("muteTexture.png");
        Texture disableAdsTexture = new Texture("disableAdsTexture.png");
        Texture shareTexture = new Texture("shareTexture.png");
        Texture buttonBackgroundTexture = new Texture("buttonBackgroundTexture.png");
        Texture buttonForegroundTexture = new Texture("buttonForegroundTexture.png");
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.app.log("SCALE", String.valueOf(data.scale));
        font.getData().setScale(2.0f, 2.0f);
        float sizeOfTexture = muteTexture.getHeight() * data.scale;

        movHandler = new MovHandler(0, -20);
        frontBackground = movHandler.getFrontMovingBackground();
        backBackground = movHandler.getBackMovingBackground();

        Button startButtonBackground = new Button(buttonBackgroundTexture, 64, data.vh / 2f, data.vw - 128, 128 * data.scale);
        startButtonForeground = new Button(buttonForegroundTexture, startButtonBackground.getX()+8, startButtonBackground.getY() + 8, data.vw - 128 - 16, 128*data.scale - 16);

        buttonMute = new Button(muteTexture, startButtonBackground.getX(), startButtonBackground.getY()- sizeOfTexture -16, sizeOfTexture, sizeOfTexture);
        buttonUnmute = new Button(unmuteTexture, buttonMute.getX(), buttonMute.getY(), sizeOfTexture, sizeOfTexture);

        btnDisableAds = new Button(disableAdsTexture, buttonMute.getX()+ buttonMute.getWidth() + (startButtonBackground.getWidth()/8), buttonMute.getY(), sizeOfTexture, sizeOfTexture);

        Button buttonShare = new Button(shareTexture, btnDisableAds.getX() + btnDisableAds.getWidth() + (startButtonBackground.getWidth() / 8), btnDisableAds.getY(), sizeOfTexture, sizeOfTexture);

        Viewport viewport = new StretchViewport(data.vw, data.vh);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, data.vw, data.vh);
        batch = new SpriteBatch();
        stage = new Stage(viewport);
        stage.addActor(startButtonBackground);
        stage.addActor(startButtonForeground);
        stage.addActor(buttonMute);
        stage.addActor(btnDisableAds);
        stage.addActor(buttonShare);
        Gdx.input.setInputProcessor(stage);
        initListeners();
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
        font.draw(batch, "PLAY", startButtonForeground.getX() + (startButtonForeground.getWidth()/3) , startButtonForeground.getY() + (startButtonForeground.getHeight()/1.5f));
        batch.end();

        movHandler.update(delta);
    }
    private void initListeners() {
        startButtonForeground.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                pb.setScreen(new GameScreen());
                data.menuAudio.stop();

                super.touchUp(event, x, y, pointer, button);
            }
        });
        buttonMute.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return super.touchDown(event, x, y, pointer, button);
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
        });
        buttonUnmute.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                return super.touchDown(event, x, y, pointer, button);
                            }

                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                data.menuAudio.play();
                                Preferences preferences = Gdx.app.getPreferences("soundSettings");
                                preferences.putBoolean("enabled", true);
                                preferences.flush();
                                buttonUnmute.remove();
                                stage.addActor(buttonMute);
                                super.touchUp(event, x, y, pointer, button);
                            }
                        }
        );
        btnDisableAds.addListener(new ClickListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (!pb.getDesktop()) {
                pb.getB().OnClick();
             } else {
                 Gdx.app.log("Desktop", "RUN");
            }
                super.touchUp(event, x, y, pointer, button);
             }
        }
        );
    }
    @Override
    public void resize(int width, int height) {
        data.vw = Gdx.graphics.getWidth();
        data.vh = Gdx.graphics.getHeight();
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
