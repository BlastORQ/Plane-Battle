package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.*;

public class TeamChoose implements Screen {
   private BitmapFont font;
   private SpriteBatch  batch;
   private Texture blue;
   public TeamChoose() {
        font = new BitmapFont(Gdx.files.internal("font/font.fnt") ,false);
        batch = new SpriteBatch();
        blue = new Texture("blue.png");
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.setColor(Color.BLACK);
        font.draw(batch, "Виберіть команду:",(Gdx.graphics.getWidth()/2) -200 , (Gdx.graphics.getHeight()/2) + 150);
        batch.draw(blue,(Gdx.graphics.getWidth()/2) -200 , (Gdx.graphics.getHeight()/2) + 100);
        batch.end();
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
