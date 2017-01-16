package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.HashMap;
import java.util.Iterator;

import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.loader.ResourceLoader;
import ua.pp.blastorq.planebattle.objects.Background;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.sprite.Plane;
public class GameScreen implements Screen
{

    Background frontBackground, backBackground;
    OrthographicCamera camera;
    private MovHandler movHandler;
    public GameScreen(PlaneBattle pb) {
        movHandler = new MovHandler(0, -100);
        initGameObjects();
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        int midPointY = (int) (gameHeight / 2);
        int midPointX = (int) (gameWidth / 2);
        ResourceLoader.player.setPosition((Gdx.graphics.getWidth() / 2) - (ResourceLoader.playerShip.getWidth() / 2), 0);
        ResourceLoader.player.setSize(64, 64);
        camera = new OrthographicCamera(gameWidth, gameHeight);


    }
    private void drawAllPlayers(){
        for (HashMap.Entry<String, Plane> entry : ResourceLoader.friendlyPlayers.entrySet()) {
            entry.getValue().draw(ResourceLoader.batch);
        }
    }
    private void drawStage(){
        ResourceLoader.stage.draw();
        ResourceLoader.stage.act(Gdx.graphics.getDeltaTime());
    }
    private void drawPlayer(){
        ResourceLoader.player.draw(ResourceLoader.batch);
        ResourceLoader.bulletx = ResourceLoader.player.getX() / 2;
    }

    private void initGameObjects() {
        frontBackground = movHandler.getFrontBackground();
        backBackground = movHandler.getBackBackground();
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
            ResourceLoader.batch.begin();
            ResourceLoader.batch.draw(ResourceLoader.Bullet, raindrop.x, raindrop.y);
            ResourceLoader.batch.end();
        }
        Iterator<Rectangle> iter = ResourceLoader.raindrops.iterator();
        while (iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y += 1000* Gdx.graphics.getDeltaTime();


        }
    }
    private void handleInput(final float dt){
        if (Math.abs(ResourceLoader.accel) >= 0.07) {
            ResourceLoader.accel /= 1.8;
        } else {
            ResourceLoader.accel = 0;
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
                ResourceLoader.player.setPosition(0, ResourceLoader.player.getY());
            } else if (ResourceLoader.player.getX() + ResourceLoader.playerShip.getWidth() > Gdx.graphics.getWidth()) {
                ResourceLoader.accel *= -0.9;
                ResourceLoader.player.setPosition(Gdx.graphics.getWidth() - 64 - 1, ResourceLoader.player.getY());
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
            } else {
                ResourceLoader.ismiddle = false;
                ResourceLoader.isRight = false;
                ResourceLoader.isLeft = false;
            }
        } else {
            ResourceLoader.ismiddle = false;
            ResourceLoader.isRight = false;
            ResourceLoader.isLeft = false;
        }
    }
    @Override
    public void render(float delta) {
        handleInput(Gdx.graphics.getDeltaTime());
        Listener();
        movHandler.update(delta);
        if (Gdx.input.justTouched() && ResourceLoader.ismiddle) {
            spawnBullet();
        }
        //ResourceLoader.batch.setProjectionMatrix(camera.combined);
        //camera.update();
        ResourceLoader.batch.begin();
        ResourceLoader.batch.draw(ResourceLoader.background, ResourceLoader.frontBackground.getX(), frontBackground.getY(), frontBackground.getWidth(), frontBackground.getHeight());
        ResourceLoader.batch.draw(ResourceLoader.background, backBackground.getX(), backBackground.getY(), backBackground.getWidth(), backBackground.getHeight());
        ResourceLoader.batch.end();
        drawBullet();
        ResourceLoader.batch.begin();
        drawPlayer();
        ResourceLoader.batch.draw(ResourceLoader.playerShip1, (MainMenuScreen.SCR_WIDTH / 2) - (ResourceLoader.playerShip1.getWidth() / 2), 440, 64, 64);
        ResourceLoader.batch.end();

        //drawStage();
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
        ResourceLoader.menu.stop();
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