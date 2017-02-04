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

import ua.pp.blastorq.planebattle.loader.ResourceLoader;
import ua.pp.blastorq.planebattle.objects.Background;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.sprite.Plane;
public class GameScreen implements Screen
{

    Background frontBackground, backBackground;
    OrthographicCamera camera;
    private MovHandler movHandler;

    public GameScreen() {
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
    public void drawBullet(){
        for (Rectangle raindrop : ResourceLoader.raindrops) {
            ResourceLoader.batch.begin();
            ResourceLoader.batch.draw(ResourceLoader.Bullet, raindrop.x, raindrop.y);
            ResourceLoader.batch.end();
        }
        Iterator<Rectangle> iter = ResourceLoader.raindrops.iterator();
        while (iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y += 700 * Gdx.graphics.getDeltaTime();


        }
    }
    private void handleInput(final float dt){
        ResourceLoader.player.render(dt);
        if (Gdx.input.isTouched()) {
            ResourceLoader.touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            ResourceLoader.camera.unproject(ResourceLoader.touchPos);
            float touchX = (ResourceLoader.touchPos.x + 1) / 2 * Gdx.graphics.getWidth();

            if(touchX <= Gdx.graphics.getWidth()/3){
                ResourceLoader.player.left();
            }else if(touchX <= Gdx.graphics.getWidth()*2/3){
                ResourceLoader.player.shot();
            }else if(touchX > Gdx.graphics.getWidth()*2/3){
                ResourceLoader.player.right();
            }
        }
    }
    @Override
    public void render(float delta) {
        handleInput(Gdx.graphics.getDeltaTime());
        Listener();
        movHandler.update(delta);
        if (Gdx.input.justTouched() && ResourceLoader.ismiddle) {

        }
        ResourceLoader.batch.begin();
        ResourceLoader.batch.draw(ResourceLoader.background, ResourceLoader.frontBackground.getX(), frontBackground.getY(), frontBackground.getWidth(), frontBackground.getHeight());
        ResourceLoader.batch.draw(ResourceLoader.background, backBackground.getX(), backBackground.getY(), backBackground.getWidth(), backBackground.getHeight());
        ResourceLoader.batch.end();
        drawBullet();
        ResourceLoader.batch.begin();
        drawPlayer();
        ResourceLoader.batch.draw(ResourceLoader.playerShip1, (MainMenuScreen.SCR_WIDTH / 2) - (ResourceLoader.playerShip1.getWidth() / 2), 440, 64, 64);
        ResourceLoader.batch.end();
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