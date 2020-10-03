package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;

import ua.pp.blastorq.planebattle.loader.data;
import ua.pp.blastorq.planebattle.objects.MovingBackground;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.screens.mainscreen.MainMenuScreen;
import ua.pp.blastorq.planebattle.sprite.Bullets;
import ua.pp.blastorq.planebattle.sprite.HP;
import ua.pp.blastorq.planebattle.sprite.Plane;

public class GameScreen implements Screen, InputProcessor
{
    private Plane bot, player;
    private Bullets bullets;
    private MovingBackground frontMovingBackground, backMovingBackground;
    private MovHandler movHandler;
    public static HP playerHP, botHP;

    public GameScreen() {
        movHandler = new MovHandler(0, -100);
        frontMovingBackground = movHandler.getFrontMovingBackground();
        backMovingBackground = movHandler.getBackMovingBackground();
        bullets = data.bullets;
        player = data.player;
        bot = data.bot;
        playerHP = data.playerHP;
        botHP = data.botHP;
    }
    private void handleInput(){
        if (Gdx.input.isTouched()) {
            data.touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);

            float touchX = (data.touchPos.x + 1) / 2;// * data.vw;
            if(touchX < data.vw/3){
                player.left();
            }else if(touchX < data.vw*2/3){
                player.shot();
            }else if(touchX > data.vw*2/3){
                player.right();
            }
            bot.canShut = true;
        }
    }
    @Override
    public void render(float delta) {
        handleInput();
        player.calculate(delta);
        bot.calculate(delta);
        playerHP.calculate();
        botHP.calculate();
        movHandler.update(delta);
        data.batch.begin();
        data.batch.draw(data.getBackgroundTexture(), data.getFrontMovingBackground().getX(), frontMovingBackground.getY(), frontMovingBackground.getWidth(), frontMovingBackground.getHeight());
        data.batch.draw(data.getBackgroundTexture(), backMovingBackground.getX(), backMovingBackground.getY(), backMovingBackground.getWidth(), backMovingBackground.getHeight());
        bullets.render();
        player.render();
        playerHP.render();
        botHP.render();
        bot.render();
        data.batch.end();
    }
    @Override
    public void pause() {
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this);
        Gdx.input.setCatchBackKey(true);
        data.menuAudio.stop();
    }
    @Override
    public void resize(int width, int height) {
        data.vw = Gdx.graphics.getWidth();
        data.vh = Gdx.graphics.getHeight();
        Gdx.app.log("", "" + data.vw);
        Gdx.app.log("", "" + data.vh);
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
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if(keycode == Input.Keys.BACK){
            data.game.setScreen(new MainMenuScreen(data.game));
            return false;
        }
        Gdx.app.log("keyUp", "smth - " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}