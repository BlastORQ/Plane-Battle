package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.actors.Background;
import ua.pp.blastorq.planebattle.actors.Off;
import ua.pp.blastorq.planebattle.actors.OffMusicButton;
import ua.pp.blastorq.planebattle.actors.On;
import ua.pp.blastorq.planebattle.actors.StartButton;

public class MainMenuScreen implements Screen {
    public static float SCR_WIDTH = Gdx.graphics.getWidth(), SCR_HEIGHT = Gdx.graphics.getHeight();
    BitmapFont font;
    Music music;
    boolean volume;
    private PlaneBattle pb;
    private Texture playbutton, bg, offmusicbutton, cancelmusic, player, on;
    private SpriteBatch batch;
    private Viewport viewport;
    private Stage stage;
    private Background background;
    private OffMusicButton offMusicButton;
    private StartButton startButton;
    private Off off;
    private On onb;
    public MainMenuScreen(PlaneBattle planeBattle){
        this.pb = planeBattle;
        on = new Texture("volume-control.png");
        music = Gdx.audio.newMusic(Gdx.files.internal("proj1_menu.ogg"));
        music.setLooping(true);
        music.play();
        playbutton = new Texture("start.png");
        player = new Texture("Plane.png");
        batch = new SpriteBatch();
        bg = new Texture("bg.png");
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        startButton = new StartButton(playbutton, (SCR_WIDTH / 2) - (playbutton.getWidth() / 2), (SCR_HEIGHT / 2) - (playbutton.getHeight() / 2) + 50, playbutton.getWidth(), playbutton.getHeight());
        background = new Background(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        offmusicbutton = new Texture("btn.png");
        offMusicButton = new OffMusicButton(offmusicbutton, startButton.getX(), startButton.getY() - 10 - offmusicbutton.getHeight(), offmusicbutton.getWidth(), offmusicbutton.getHeight());
        cancelmusic = new Texture("volume-adjustment-mute.png");
        off = new Off(cancelmusic, startButton.getX() + 10, startButton.getY() - 10 - offmusicbutton.getHeight() + 5, cancelmusic.getWidth(), cancelmusic.getHeight());
        onb = new On(on, startButton.getX() + 10, startButton.getY() - 10 - offmusicbutton.getHeight() + 5, cancelmusic.getWidth(), cancelmusic.getHeight());
        stage.addActor(background);
        stage.addActor(startButton);
        stage.addActor(offMusicButton);
        stage.addActor(off);
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
        batch.begin();
        batch.draw(player, (SCR_WIDTH / 2) - (player.getWidth() / 2), 0);
        font.draw(batch, "PLAY", (SCR_WIDTH / 2) - (playbutton.getWidth() / 2) + 50, (SCR_HEIGHT / 2) - (playbutton.getHeight() / 2) + 155);
        batch.end();
        Listener();
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
                super.touchUp(event, x, y, pointer, button);
            }
        });
        off.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                music.pause();
                off.remove();
                stage.addActor(onb);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        onb.addListener(new ClickListener() {
                            @Override
                            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                                return super.touchDown(event, x, y, pointer, button);
                            }

                            @Override
                            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                                music.play();
                                onb.remove();
                                stage.addActor(off);
                                super.touchUp(event, x, y, pointer, button);
                            }
                        }
        );

    }
    @Override
    public void resize(int width, int height) {
        SCR_WIDTH = Gdx.graphics.getWidth();
        SCR_HEIGHT = Gdx.graphics.getHeight();
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

}
