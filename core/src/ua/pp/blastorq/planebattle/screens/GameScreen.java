package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import java.util.HashMap;
import java.util.Iterator;

import ua.pp.blastorq.planebattle.actors.HitButton;
import ua.pp.blastorq.planebattle.actors.Left;
import ua.pp.blastorq.planebattle.actors.Right;
import ua.pp.blastorq.planebattle.sprite.Plane;
public class GameScreen implements Screen
{
    private final float UPDATE_TIME = 1 / 60f;
    boolean isHit = false;
    Texture background;
    HitButton hitButton;
    private OrthographicCamera camera = new OrthographicCamera();
    private Plane player;
    private Left LeftButton;
    private boolean isLeft = false, isRight = false, isShoot;
    private SpriteBatch batch;
    private Stage stage;
    private Right RightButton;
    private  Texture playerShip, friendlyShip, HitButtonImage, Bullet;
    private Array<Rectangle> raindrops;
    private double bulletx , bullety = 64 + 128;
    private boolean ismiddle = false;
    private HashMap<String, Plane> friendlyPlayers;
    private Vector3 touchPos;
    private Texture BulletImage;
    private float accel = 0;
    public GameScreen() {
        background = new Texture("bg.png");
        BulletImage = new Texture("icon.png");
        hitButton = new HitButton(BulletImage);
        raindrops = new Array<Rectangle>();
        RightButton = new Right(new Texture("right.png"));
        Texture  rightimage, leftimage;
        touchPos = new Vector3();
        leftimage = new Texture("left.png");
        LeftButton = new Left(leftimage);
        rightimage = new Texture("right.png");
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        playerShip = new Texture("Plane.png");
        player = new Plane(playerShip);
        friendlyShip = new Texture("Plane1.png");
        RightButton = new Right(rightimage);
        HitButtonImage = new Texture("icon.png");
        Bullet = new Texture("bullet.png");
        friendlyPlayers = new HashMap<String, Plane>();
        stage.addActor(RightButton);
        stage.addActor(LeftButton);
        stage.addActor(hitButton);
        Gdx.input.setInputProcessor(stage);
    }
    private void drawAllPlayers(){
        for(HashMap.Entry<String, Plane> entry : friendlyPlayers.entrySet()){
            entry.getValue().draw(batch);
        }
    }
    private void drawStage(){
        stage.draw();
        stage.act(Gdx.graphics.getDeltaTime());
    }
    private void drawPlayer(){
        if (player != null) {
            player.draw(batch);
            bulletx = player.getX()/2;
        }
    }
    private void spawnBullet(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = player.getX() + (player.getWidth()/2) - (Bullet.getWidth()/2);
        raindrop.y = 192;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
    }
    public void drawBullet(){
        for (Rectangle raindrop: raindrops){
            batch.begin();
            batch.draw(Bullet, raindrop.x, raindrop.y);
            batch.end();
        }
        Iterator<Rectangle> iter = raindrops.iterator();
        while (iter.hasNext()){
            Rectangle raindrop = iter.next();
            raindrop.y += 1000* Gdx.graphics.getDeltaTime();


        }
    }
    private void handleInput(final float dt){
        if(accel != 0){
            accel /= 1.8;
        }
        if(player != null) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || isLeft) {
                accel -= 0.09;
                if(accel<-2){
                    accel = -2;
                }
            } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || isRight){
                accel += 0.09;
                if(accel>2){
                    accel = 2;
                }
            }
            player.setPosition(player.getX() + (300 * 10 * accel * dt), player.getY());
            if(player.getX() <0){
                accel *= -0.9;
                player.setPosition(0, 64);
            }else if(player.getX() + playerShip.getWidth()> Gdx.graphics.getWidth()){
                accel *= -0.9;
                player.setPosition(Gdx.graphics.getWidth() - player.getWidth(), player.getY());
            }
            if (Gdx.input.isTouched()) {
                touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
                camera.unproject(touchPos);
                float touchX = (touchPos.x+1)/2*Gdx.graphics.getWidth();
                if(touchX <= Gdx.graphics.getWidth()/3){
                    isLeft = true;
                }else if(touchX <= Gdx.graphics.getWidth()*2/3){
                    ismiddle = true;
                }
                else if(touchX > Gdx.graphics.getWidth()*2/3){
                    isRight = true;
                }
            }
            else {
                ismiddle = false;
                isRight = false;
                isLeft = false;
            }
        }
    }
    @Override
    public void render(float delta) {
        handleInput(Gdx.graphics.getDeltaTime());
        Listener();
        if(Gdx.input.justTouched() && ismiddle){
            spawnBullet();
        }
        batch.begin();
        batch.draw(background, 0, 0, MainMenuScreen.SCR_WIDTH, MainMenuScreen.SCR_HEIGHT);
        batch.end();
        drawBullet();
        batch.begin();

        drawPlayer();
        batch.end();
        drawStage();

    }
    public void Listener() {
        LeftButton.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                isLeft = true;
                return super.touchDown(event, x, y, pointer, button);
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                isLeft = false;
                super.touchUp(event, x, y, pointer, button);
            }
        });
        RightButton.addListener(new ClickListener(){
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
        Gdx.input.setInputProcessor(stage);
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