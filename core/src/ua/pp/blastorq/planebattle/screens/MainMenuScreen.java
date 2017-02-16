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
import ua.pp.blastorq.planebattle.loader.data;
import ua.pp.blastorq.planebattle.Bill;
import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.actors.Button;
import ua.pp.blastorq.planebattle.actors.Cancel;
import ua.pp.blastorq.planebattle.actors.Mute;
import ua.pp.blastorq.planebattle.actors.OffMusicButton;
import ua.pp.blastorq.planebattle.actors.Unmute;
import ua.pp.blastorq.planebattle.actors.StartButton;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.objects.MovingBackground;

public class MainMenuScreen implements Screen, Bill {
    private BitmapFont font;
    private MovingBackground frontBackground, backBackground;
    private MovHandler movHandler;
    private PlaneBattle pb;
    private Texture t_bg, t_start, t_plane, t_unmute, t_btn, t_mute, t_noads;
    private SpriteBatch batch;
    private Stage stage;
    private StartButton startButton;
    private Mute btn_mute;
    private Unmute btn_unmute;
    private Cancel btn_noads;
    Viewport viewport;
    OffMusicButton btn_bg_mute;
    Button btn_bg_noads;

    public MainMenuScreen(PlaneBattle planeBattle){
        this.pb = planeBattle;

        t_unmute = new Texture("unmute.png");
        t_start = new Texture("start.png");
        t_plane = new Texture("spacecraft.png");
        t_btn = new Texture("btn.png");
        t_bg = new Texture("bg.png");
        t_mute = new Texture("mute.png");
        t_noads = new Texture("no-ads.png");
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));

        movHandler = new MovHandler(0, -20);
        frontBackground = movHandler.getFrontMovingBackground();
        backBackground = movHandler.getBackMovingBackground();

        startButton = new StartButton(t_start, (data.vw / 2) - (t_start.getWidth() / 2), (data.vh / 2) - (t_start.getHeight() / 2) + 50, t_start.getWidth(), t_start.getHeight());

        btn_bg_mute = new OffMusicButton(t_btn, startButton.getX(), startButton.getY() - 10 - t_btn.getHeight(), t_btn.getWidth(), t_btn.getHeight());
        btn_mute = new Mute(t_mute, startButton.getX() + 10, startButton.getY() - 10 - t_btn.getHeight() + 5, t_mute.getWidth(), t_mute.getHeight());
        btn_unmute = new Unmute(t_unmute, startButton.getX() + 10, startButton.getY() - 10 - t_btn.getHeight() + 5, t_mute.getWidth(), t_mute.getHeight());

        btn_bg_noads = new Button(t_btn, btn_bg_mute.getX() + t_btn.getWidth() + 10, btn_bg_mute.getY(), t_btn.getWidth(), t_btn.getHeight());
        btn_noads = new Cancel(t_noads, btn_bg_noads.getX(), btn_bg_noads.getY(), t_noads.getWidth(), t_noads.getHeight());

        viewport = new StretchViewport(data.vw, data.vh);
        batch = new SpriteBatch();
        stage = new Stage(viewport);
        stage.addActor(startButton);
        stage.addActor(btn_bg_mute);
        stage.addActor(btn_mute);
        stage.addActor(btn_bg_noads);
        stage.addActor(btn_noads);
        Gdx.input.setInputProcessor(stage);
        Listener();
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
        data.player.draw(batch);
        font.draw(batch, "PLAY", (data.vw / 2) - (t_start.getWidth() / 2) + 50, (data.vh / 2) - (t_start.getHeight() / 2) + 155);
        batch.end();
        movHandler.update(delta);
    }
    private void Listener() {
        startButton.addListener(new ClickListener() {
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
