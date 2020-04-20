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
    OrthographicCamera camera;
    private MovingBackground frontBackground, backBackground;
    private MovHandler movHandler;
    private PlaneBattle pb;
    private Texture t_bg, t_unmute, t_mute, t_noads, t_share;
    private SpriteBatch batch;
    private Stage stage;
    private Button btn_start_back, btn_start_front, btn_mute, btn_unmute, btn_noads, btn_share;
    private float t_size;
    Viewport viewport;
    Texture t_btn_bg_back, t_btn_bg_front;

    public MainMenuScreen(PlaneBattle planeBattle){
        this.pb = planeBattle;

        t_unmute = new Texture("t_unmute.png");
        t_bg = new Texture("bg.png");
        t_mute = new Texture("t_mute.png");
        t_noads = new Texture("t_no-ads.png");
        t_share = new Texture("t_share.png");
        t_btn_bg_back = new Texture("t_btn_gb_back.png");
        t_btn_bg_front = new Texture("t_btn_gb_front.png");
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.app.log("SCALE", String.valueOf(data.scale));
        font.getData().setScale(2.0f, 2.0f);
        t_size = t_mute.getHeight()*data.scale;

        movHandler = new MovHandler(0, -20);
        frontBackground = movHandler.getFrontMovingBackground();
        backBackground = movHandler.getBackMovingBackground();

        btn_start_back = new Button(t_btn_bg_back, 64, data.vh/2f, data.vw - 128, 128*data.scale);
        btn_start_front = new Button(t_btn_bg_front, btn_start_back.getX()+8, btn_start_back.getY() + 8, data.vw - 128 - 16, 128*data.scale - 16);

        btn_mute = new Button(t_mute, btn_start_back.getX(), btn_start_back.getY()-t_size-16, t_size, t_size);
        btn_unmute = new Button(t_unmute, btn_mute.getX(), btn_mute.getY(), t_size, t_size);

        btn_noads = new Button(t_noads, btn_mute.getX()+btn_mute.getWidth() + (btn_start_back.getWidth()/8), btn_mute.getY(), t_size, t_size);

        btn_share = new Button(t_share, btn_noads.getX()+btn_noads.getWidth() + (btn_start_back.getWidth()/8), btn_noads.getY(), t_size, t_size);

        viewport = new StretchViewport(data.vw, data.vh);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, data.vw, data.vh);
        batch = new SpriteBatch();
        stage = new Stage(viewport);
        stage.addActor(btn_start_back);
        stage.addActor(btn_start_front);
        stage.addActor(btn_mute);
        stage.addActor(btn_noads);
        stage.addActor(btn_share);
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
            btn_unmute.remove();
            stage.addActor(btn_mute);
        }else{
            btn_mute.remove();
            stage.addActor(btn_unmute);
        }
    }

    @Override
    public void render(float delta) {

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(t_bg, frontBackground.getX(), frontBackground.getY(), frontBackground.getWidth() + 150, frontBackground.getHeight() + 150);
        batch.draw(t_bg, backBackground.getX(), backBackground.getY(), backBackground.getWidth() + 150, backBackground.getHeight() + 150);
        batch.end();

        stage.act(delta);
        stage.draw();

        batch.begin();
        font.draw(batch, "PLAY", btn_start_front.getX() + (btn_start_front.getWidth()/3) ,btn_start_front.getY() + (btn_start_front.getHeight()/1.5f));
        batch.end();

        movHandler.update(delta);
    }
    private void initListeners() {
        btn_start_front.addListener(new ClickListener() {
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
        btn_mute.addListener(new ClickListener() {
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
                btn_mute.remove();
                stage.addActor(btn_unmute);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        btn_unmute.addListener(new ClickListener() {
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
                                btn_unmute.remove();
                                stage.addActor(btn_mute);
                                super.touchUp(event, x, y, pointer, button);
                            }
                        }
        );
        btn_noads.addListener(new ClickListener() {
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
