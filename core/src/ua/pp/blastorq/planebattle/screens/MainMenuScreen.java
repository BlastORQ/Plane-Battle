package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
    private MovingBackground frontBackground, backBackground;
    private MovHandler movHandler;
    private PlaneBattle pb;
    private Texture t_bg, t_unmute, t_mute, t_noads;
    private SpriteBatch batch;
    private Stage stage;
    private Button btn_start_back, btn_start_front, btn_mute, btn_unmute, btn_noads;
    private float t_size;
    Viewport viewport;
    Texture t_btn_bg_back, t_btn_bg_front;

    public MainMenuScreen(PlaneBattle planeBattle){
        this.pb = planeBattle;

        t_unmute = new Texture("t_unmute.png");
        t_bg = new Texture("bg.png");
        t_mute = new Texture("t_mute.png");
        t_noads = new Texture("t_no-ads.png");
        t_btn_bg_back = new Texture("t_btn_gb_back.png");
        t_btn_bg_front = new Texture("t_btn_gb_front.png");
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));

        t_size = t_mute.getHeight()*data.scale;

        movHandler = new MovHandler(0, -20);
        frontBackground = movHandler.getFrontMovingBackground();
        backBackground = movHandler.getBackMovingBackground();

        btn_start_back = new Button(t_btn_bg_back, 64, data.vh/2, data.vw - 128, 128*data.scale);
        btn_start_front = new Button(t_btn_bg_front, 64+8, data.vh/2 + 8, data.vw - 128 - 16, 128*data.scale - 16);

        btn_mute = new Button(t_mute, 64, data.vh/2-t_size-16, t_size, t_size);
        btn_unmute = new Button(t_unmute, 64, data.vh/2-t_size-16, t_size, t_size);

        btn_noads = new Button(t_noads, 64+t_size+16, data.vh/2-t_size-16, t_size, t_size);

        viewport = new StretchViewport(data.vw, data.vh);
        batch = new SpriteBatch();
        stage = new Stage(viewport);
        stage.addActor(btn_start_back);
        stage.addActor(btn_start_front);
        stage.addActor(btn_mute);
        stage.addActor(btn_noads);
        Gdx.input.setInputProcessor(stage);
        initListeners();
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        data.menuAudio.play();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(t_bg, frontBackground.getX(), frontBackground.getY(), frontBackground.getWidth() + 150, frontBackground.getHeight() + 150);
        batch.draw(t_bg, backBackground.getX(), backBackground.getY(), backBackground.getWidth() + 150, backBackground.getHeight() + 150);
        batch.end();

        stage.act(delta);
        stage.draw();

        batch.begin();
        font.draw(batch, "PLAY", (data.vw - btn_start_front.getWidth())/2 + 50, (data.vh - btn_start_front.getHeight())/2 + 155);
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
