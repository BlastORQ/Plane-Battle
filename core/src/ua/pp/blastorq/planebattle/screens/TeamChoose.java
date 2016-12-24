package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.actors.Blue;

public class TeamChoose implements Screen {
   private BitmapFont font;
   private SpriteBatch  batch;
    private Blue blueActor;
    private Stage stage;
   private PlaneBattle pb;
   public TeamChoose(PlaneBattle pb) {
       this.pb = pb;
        font = new BitmapFont(Gdx.files.internal("font/font.fnt") ,false);
        batch = new SpriteBatch();
       Texture blue = new Texture("blue.png");
       blueActor = new Blue(blue);
       Viewport viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
       stage = new Stage(viewport);
       stage.addActor(blueActor);
       Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.setColor(Color.BLACK);
        font.draw(batch, "Choose Your Team",100 , 100);
        stage.draw();
        stage.act(delta);
        batch.end();
        blueActor.addListener(new ClickListener(){
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                pb.setScreen(new GameScreen());
                return super.touchDown(event, x, y, pointer, button);
            }
        });
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
    public void dispose() {

    }
}
