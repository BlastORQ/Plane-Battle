package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import ua.pp.blastorq.planebattle.loader.data;
import ua.pp.blastorq.planebattle.objects.MovingBackground;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.sprite.Bullets;
import ua.pp.blastorq.planebattle.sprite.Plane;

class GameScreen implements Screen
{
    private Plane bot, player;
    private Bullets bullets;
    private MovingBackground frontMovingBackground, backMovingBackground;
    private MovHandler movHandler;

    GameScreen() {
        movHandler = new MovHandler(0, -100);
        frontMovingBackground = movHandler.getFrontMovingBackground();
        backMovingBackground = movHandler.getBackMovingBackground();
        bullets = data.bullets;
        player = data.player;
        bot = data.bot;

        player.setSize(64*data.scale, 64*data.scale);
        player.setPosition((data.vw - player.getWidth()) / 2, 0);

        bot.setSize(64*data.scale, 64*data.scale);
        bot.setPosition((data.vw - bot.getWidth()) / 2, data.vh-160-(64*data.scale));
    }
    private void handleInput(){
        if (Gdx.input.isTouched()) {
            data.touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            data.camera.unproject(data.touchPos);
            float touchX = (data.touchPos.x + 1) / 2 * data.vw;
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
        movHandler.update(delta);
        data.batch.begin();
        data.batch.draw(data.getBackgroundTexture(), data.getFrontMovingBackground().getX(), frontMovingBackground.getY(), frontMovingBackground.getWidth(), frontMovingBackground.getHeight());
        data.batch.draw(data.getBackgroundTexture(), backMovingBackground.getX(), backMovingBackground.getY(), backMovingBackground.getWidth(), backMovingBackground.getHeight());
        bullets.render();
        player.render();
        bot.render();
        data.batch.end();
    }
    @Override
    public void pause() {
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(data.stage);
        data.menuAudio.stop();
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