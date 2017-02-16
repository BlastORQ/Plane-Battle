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
import ua.pp.blastorq.planebattle.loader.ResourceLoader;
import ua.pp.blastorq.planebattle.Bill;
import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.actors.Button;
import ua.pp.blastorq.planebattle.actors.Cancel;
import ua.pp.blastorq.planebattle.actors.Off;
import ua.pp.blastorq.planebattle.actors.OffMusicButton;
import ua.pp.blastorq.planebattle.actors.On;
import ua.pp.blastorq.planebattle.actors.StartButton;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.objects.MovingBackground;

public class MainMenuScreen implements Screen, Bill {
    static float SCR_WIDTH = Gdx.graphics.getWidth(), SCR_HEIGHT = Gdx.graphics.getHeight();
    private BitmapFont font;
    private MovingBackground frontBackground, backBackground;
    private MovHandler movHandler;
    private PlaneBattle pb;
    private Texture PlayButton;
    private Texture bg;
    private Texture player;
    private SpriteBatch batch;
    private Stage stage;
    private StartButton startButton;
    private Off off;
    private On onb;
    private Cancel cancel;
    public MainMenuScreen(PlaneBattle planeBattle){
        this.pb = planeBattle;
        movHandler = new MovHandler(0, -20);
        frontBackground = movHandler.getFrontMovingBackground();
        backBackground = movHandler.getBackMovingBackground();
        Texture on = new Texture("volume-control.png");
        PlayButton = new Texture("start.png");
        player = new Texture("Plane.png");
        batch = new SpriteBatch();
        bg = new Texture("bg.png");
        Viewport viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage = new Stage(viewport);
        startButton = new StartButton(PlayButton, (SCR_WIDTH / 2) - (PlayButton.getWidth() / 2), (SCR_HEIGHT / 2) - (PlayButton.getHeight() / 2) + 50, PlayButton.getWidth(), PlayButton.getHeight());
        Texture OffMusicButton = new Texture("btn.png");
        OffMusicButton offMusicButton = new OffMusicButton(OffMusicButton, startButton.getX(), startButton.getY() - 10 - OffMusicButton.getHeight(), OffMusicButton.getWidth(), OffMusicButton.getHeight());
        Texture CancelMusic = new Texture("volume-adjustment-mute.png");
        off = new Off(CancelMusic, startButton.getX() + 10, startButton.getY() - 10 - OffMusicButton.getHeight() + 5, CancelMusic.getWidth(), CancelMusic.getHeight());
        onb = new On(on, startButton.getX() + 10, startButton.getY() - 10 - OffMusicButton.getHeight() + 5, CancelMusic.getWidth(), CancelMusic.getHeight());
        Button button = new Button(OffMusicButton, offMusicButton.getX() + OffMusicButton.getWidth() + 10, offMusicButton.getY(), OffMusicButton.getWidth(), OffMusicButton.getHeight());
        Texture CancelImage = new Texture("no-ads-icon.png");
        cancel = new Cancel(CancelImage, button.getX(), button.getY(), CancelImage.getWidth(), CancelImage.getHeight());
        stage.addActor(startButton);
        stage.addActor(offMusicButton);
        stage.addActor(off);
        stage.addActor(button);
        stage.addActor(cancel);
        font = new BitmapFont(Gdx.files.internal("font/font.fnt"));
        Gdx.input.setInputProcessor(stage);
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        SCR_WIDTH = gameWidth;
        SCR_HEIGHT = gameHeight;
        Listener();
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        ResourceLoader.menu.play();
    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(bg, frontBackground.getX(), frontBackground.getY(), frontBackground.getWidth() + 150, frontBackground.getHeight() + 150);
        batch.draw(bg, backBackground.getX(), backBackground.getY(), backBackground.getWidth() + 150, backBackground.getHeight() + 150);
        batch.end();
        stage.act(delta);
        stage.draw();
        batch.begin();
        batch.draw(ResourceLoader.player, (SCR_WIDTH / 2) - (player.getWidth() / 2), 0);
        font.draw(batch, "PLAY", (SCR_WIDTH / 2) - (PlayButton.getWidth() / 2) + 25, (SCR_HEIGHT / 2) - (PlayButton.getHeight() / 2) + 155);
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
                pb.setScreen(new GameScreen(pb));
                ResourceLoader.menu.stop();
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
                ResourceLoader.menu.pause();

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
                                ResourceLoader.menu.play();
                                onb.remove();
                                stage.addActor(off);
                                super.touchUp(event, x, y, pointer, button);
                            }
                        }
        );
        cancel.addListener(new ClickListener() {
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

    @Override
    public void OnClick() {

    }
}
