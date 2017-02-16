package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.loader.ResourceLoader;
import ua.pp.blastorq.planebattle.objects.MovingBackground;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.sprite.Bullet;
import ua.pp.blastorq.planebattle.sprite.Bullets;
import ua.pp.blastorq.planebattle.sprite.Plane;

class GameScreen implements Screen
{
    private Plane bot, player;
    private boolean start = false;
    private Bullets bullets;
    private MovingBackground frontMovingBackground, backMovingBackground;
    private MovHandler movHandler;
    private BitmapFont font;
    private Bullet bullet;
    private Rectangle bulletRectangle, botRectangle, playerRectangle;
    PlaneBattle pb;
    private long lastAddPlayer, lastAddBot;
    GameScreen(PlaneBattle pb) {
        this.pb = pb;
        movHandler = new MovHandler(0, -100);
        frontMovingBackground = movHandler.getFrontMovingBackground();
        backMovingBackground = movHandler.getBackMovingBackground();
        bullets = ResourceLoader.bullets;
        player = ResourceLoader.player;
        bot = ResourceLoader.bot;
        font = ResourceLoader.font;
        player.setPosition((Gdx.graphics.getWidth() / 2) - (player.getWidth() / 2), 0);
        player.setSize(64, 64);
        bot.setPosition((Gdx.graphics.getWidth() / 2) - (bot.getWidth() / 2), Gdx.graphics.getHeight() - 100);
        bot.setSize(64, 64);
        bulletRectangle = ResourceLoader.bulletRectangle;
        botRectangle = ResourceLoader.botRectangle;
        playerRectangle  =ResourceLoader.playerRectangle;
    }
    private void handleInput(){
        if (Gdx.input.isTouched()) {
            ResourceLoader.touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            ResourceLoader.camera.unproject(ResourceLoader.touchPos);
            float touchX = (ResourceLoader.touchPos.x + 1) / 2 * Gdx.graphics.getWidth();
            if(touchX <= Gdx.graphics.getWidth()/3){
                player.left();
            }else if(touchX <= Gdx.graphics.getWidth()*2/3){
                player.shot();
            }else if(touchX > Gdx.graphics.getWidth()*2/3){
                player.right();
            }
        }
    }

    @Override
    public void render(float delta) {
        makeCoordinates();
        botRectangle.y = botRectangle.y +1;
        playerRectangle.y = playerRectangle.y -1;
        if(Gdx.input.justTouched()){
            start = true;
       }
        if(start) {
            handleInput();
            player.calculate(delta);
            bot.calculate(delta);
            movHandler.update(delta);
            ResourceLoader.batch.begin();
            ResourceLoader.batch.draw(ResourceLoader.getBackground(), ResourceLoader.getFrontMovingBackground().getX(), frontMovingBackground.getY(), frontMovingBackground.getWidth(), frontMovingBackground.getHeight());
            ResourceLoader.batch.draw(ResourceLoader.getBackground(), backMovingBackground.getX(), backMovingBackground.getY(), backMovingBackground.getWidth(), backMovingBackground.getHeight());
            bullets.render();
            player.render();
            bot.render();
            ResourceLoader.batch.end();
      }else {
            ResourceLoader.batch.begin();
            ResourceLoader.batch.draw(ResourceLoader.getBackground(), ResourceLoader.getFrontMovingBackground().getX(), frontMovingBackground.getY(), frontMovingBackground.getWidth(), frontMovingBackground.getHeight());
            ResourceLoader.batch.draw(ResourceLoader.getBackground(), backMovingBackground.getX(), backMovingBackground.getY(), backMovingBackground.getWidth(), backMovingBackground.getHeight());
            bullets.render();
            player.render();
            bot.render();
            ResourceLoader.batch.end();
            }
        }
    private void makeCoordinates(){
      playerRectangle.x = player.getX();
      playerRectangle.y = player.getY();
      botRectangle.x = bot.getX();
      botRectangle.y =  bot.getY();


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