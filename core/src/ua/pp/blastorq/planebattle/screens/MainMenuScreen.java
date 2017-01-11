package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import ua.pp.blastorq.planebattle.PlaneBattle;

public class MainMenuScreen implements Screen {
    PlaneBattle pb;
    Texture playbutton, bg;
    SpriteBatch batch;
    float SCR_WIDTH = Gdx.graphics.getWidth(), SCR_HEIGHT = Gdx.graphics.getHeight();
    public MainMenuScreen(PlaneBattle planeBattle){
        this.pb = planeBattle;
        playbutton = new Texture("btn.png");
        batch = new SpriteBatch();
        bg = new Texture("bg.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(bg, 0 ,0 , Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(playbutton ,(SCR_WIDTH/2)- (playbutton.getWidth()) - 100, (SCR_HEIGHT/2) - (playbutton.getHeight()/2)+ 50, 350, playbutton.getHeight()+ 30);
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
