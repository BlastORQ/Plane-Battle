package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;
import java.util.Iterator;

import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.loader.ResourceLoader;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.sprite.Plane;

import static ua.pp.blastorq.planebattle.loader.ResourceLoader.batch;
import static ua.pp.blastorq.planebattle.loader.ResourceLoader.friendlyPlayers;

public class GameScreen implements Screen
{
    MovHandler movHandler;
    PlaneBattle pb;

    public GameScreen(PlaneBattle pb) {
        this.pb = pb;
        movHandler = new MovHandler(0, -59);
    }
    private void drawAllPlayers(){
        for(HashMap.Entry<String, Plane> entry : friendlyPlayers.entrySet()){
            entry.getValue().draw(batch);
        }
    }
    private void drawStage(){
        ResourceLoader.stage.draw();
        ResourceLoader.stage.act(Gdx.graphics.getDeltaTime());
    }
    private void drawPlayer(){
        if (ResourceLoader.player != null) {
            ResourceLoader.player.draw(batch);
            ResourceLoader.bulletx = ResourceLoader.player.getX() / 2;
        }
    }


    private void spawnBullet(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = ResourceLoader.player.getX() + (ResourceLoader.player.getWidth() / 2) - (ResourceLoader.Bullet.getWidth() / 2);
        raindrop.y = 192;
        raindrop.width = 64;
        raindrop.height = 64;
        ResourceLoader.raindrops.add(raindrop);
    }
    public void drawBullet(){
        for (Rectangle raindrop : ResourceLoader.raindrops) {
            batch.begin();
            batch.draw(ResourceLoader.Bullet, raindrop.x, raindrop.y);
            batch.end();
        }
        Iterator<Rectangle> iter = ResourceLoader.raindrops.iterator();
        while (iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y += 1000* Gdx.graphics.getDeltaTime();


        }
    }
    private void handleInput(final float dt){
        if (ResourceLoader.accel != 0) {
            ResourceLoader.accel /= 1.8;
        }
        if (ResourceLoader.player != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || ResourceLoader.isLeft) {
                ResourceLoader.accel -= 0.09;
                if (ResourceLoader.accel < -2) {
                    ResourceLoader.accel = -2;
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || ResourceLoader.isRight) {
                ResourceLoader.accel += 0.09;
                if (ResourceLoader.accel > 2) {
                    ResourceLoader.accel = 2;
                }
            }
            ResourceLoader.player.setPosition(ResourceLoader.player.getX() + (300 * 10 * ResourceLoader.accel * dt), ResourceLoader.player.getY());
            if (ResourceLoader.player.getX() < 0) {
                ResourceLoader.accel *= -0.9;
                ResourceLoader.player.setPosition(0, 64);
            } else if (ResourceLoader.player.getX() + ResourceLoader.playerShip.getWidth() > Gdx.graphics.getWidth()) {
                ResourceLoader.accel *= -0.9;
                ResourceLoader.player.setPosition(Gdx.graphics.getWidth() - ResourceLoader.player.getWidth(), ResourceLoader.player.getY());
            }
            if (Gdx.input.isTouched()) {
                ResourceLoader.touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                ResourceLoader.camera.unproject(ResourceLoader.touchPos);
                float touchX = (ResourceLoader.touchPos.x + 1) / 2 * Gdx.graphics.getWidth();
                if(touchX <= Gdx.graphics.getWidth()/3){
                    ResourceLoader.isLeft = true;
                }else if(touchX <= Gdx.graphics.getWidth()*2/3){
                    ResourceLoader.ismiddle = true;
                }
                else if(touchX > Gdx.graphics.getWidth()*2/3){
                    ResourceLoader.isRight = true;
                }
            }
            else {
                ResourceLoader.ismiddle = false;
                ResourceLoader.isRight = false;
                ResourceLoader.isLeft = false;
            }
        }
    }
    @Override
    public void render(float delta) {
        handleInput(delta);
        Listener();
        if (Gdx.input.justTouched() && ResourceLoader.ismiddle) {
            spawnBullet();
        }
        batch.begin();
        batch.draw(ResourceLoader.background, movHandler.getFrontBackground().getX(), movHandler.getFrontBackground().getY(), movHandler.getFrontBackground().getWidth() + 150, movHandler.getFrontBackground().getHeight() + 150);
        batch.draw(ResourceLoader.background, movHandler.getBackBackground().getX(), movHandler.getBackBackground().getY(), movHandler.getBackBackground().getWidth(), movHandler.getBackBackground().getHeight());
        batch.end();
        drawBullet();
        batch.begin();
        drawPlayer();
        batch.end();
        drawStage();
        movHandler.update(delta);

    }
    public void Listener() {
        ResourceLoader.LeftButton.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                ResourceLoader.isLeft = true;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                ResourceLoader.isLeft = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        ResourceLoader.RightButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return super.touchDown(event, x, y, pointer, button);
            }
        });


    }
    @Override
    public void pause() {
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(ResourceLoader.stage);
    }
    @Override
    public void resize(int width, int height) {
        MainMenuScreen.SCR_WIDTH = Gdx.graphics.getWidth();
        MainMenuScreen.SCR_HEIGHT = Gdx.graphics.getHeight();
        Gdx.app.log("", "" + MainMenuScreen.SCR_WIDTH);
        Gdx.app.log("", "" + MainMenuScreen.SCR_HEIGHT);
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