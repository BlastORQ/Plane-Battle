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

import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.actors.Background;
import ua.pp.blastorq.planebattle.actors.OffMusicButton;
import ua.pp.blastorq.planebattle.actors.StartButton;

public class MainMenuScreen implements Screen {
    public static float SCR_WIDTH = Gdx.graphics.getWidth(), SCR_HEIGHT = Gdx.graphics.getHeight();
    BitmapFont font;
    private PlaneBattle pb;
    private Texture playbutton, bg, offmusicbutton;
    private SpriteBatch batch;
    private Viewport viewport;
    private Stage stage;
    private Background background;
    private OffMusicButton offMusicButton;
    private StartButton startButton;
    public MainMenuScreen(PlaneBattle planeBattle){
        this.pb = planeBattle;
        playbutton = new Texture("start.png");
        batch = new SpriteBatch();
        bg = new Texture("bg.png");
        viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        startButton = new StartButton(playbutton, (SCR_WIDTH / 2) - (playbutton.getWidth() / 2), (SCR_HEIGHT / 2) - (playbutton.getHeight() / 2) + 50, playbutton.getWidth(), playbutton.getHeight());
        background = new Background(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        offmusicbutton = new Texture("btn.png");
        offMusicButton = new OffMusicButton(offmusicbutton, startButton.getX(), startButton.getY() - 10 - offmusicbutton.getHeight(), offmusicbutton.getWidth(), offmusicbutton.getHeight());
        stage.addActor(background);
        stage.addActor(startButton);
        stage.addActor(offMusicButton);
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
