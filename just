package ua.pp.blastorq.touchpadcalibrator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
public class GameScreen implements Screen{
    private SpriteBatch batch;
    private Texture red;
    private Stage stage;
    private Viewport viewport;
    private RedActor redActor1, redActor2, redActor3, redActor4;
    private float count = 4;
    Clibrator game;
    public GameScreen(Clibrator gm) {
        this.game = gm;
        batch = new SpriteBatch();
        red = new Texture("red.png");
        viewport = new StretchViewport(Constants.V_WIDTH, Constants.V_HEIGHT);
        redActor1 = new RedActor(red, 0, 0, red.getWidth(), red.getHeight());
        redActor2 = new RedActor(red, 0, Constants.V_HEIGHT - red.getHeight(), red.getWidth(), red.getHeight());
        redActor3 = new RedActor(red, Constants.V_WIDTH - red.getWidth(), Constants.V_HEIGHT - red.getHeight(), red.getWidth(), red.getHeight());
        redActor4 = new RedActor(red, Constants.V_WIDTH - red.getWidth(), 0, red.getWidth(), red.getHeight());
        stage = new Stage(viewport);
        stage.addActor(redActor1);
        redActor1.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                redActor1.remove();
                stage.addActor(redActor2);

                super.touchUp(event, x, y, pointer, button);
            }
        });
        redActor2.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                redActor2.remove();
                stage.addActor(redActor3);
                super.touchUp(event, x, y, pointer, button);
            }
        });
        redActor3.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                redActor3.remove();
                stage.addActor(redActor4);

                super.touchUp(event, x, y, pointer, button);
            }
        });
        redActor4.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                redActor4.remove();
                game.setScreen(new EndScreen(count));
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }
    @Override
    public void render (float delta) {
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
        if(Gdx.input.justTouched()){
            count ++;
        }
    }

    @Override
    public void resize(int width, int height) {

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
    public void dispose () {
        batch.dispose();
    }
}
