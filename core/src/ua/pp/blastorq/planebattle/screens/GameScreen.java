package ua.pp.blastorq.planebattle.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Rectangle;
import ua.pp.blastorq.planebattle.loader.ResourceLoader;
import ua.pp.blastorq.planebattle.objects.MovingBackground;
import ua.pp.blastorq.planebattle.objects.MovHandler;
 class GameScreen implements Screen
{

    private MovingBackground frontMovingBackground, backMovingBackground;
    private MovHandler movHandler;
     GameScreen() {
        movHandler = new MovHandler(0, -100);
        initGameObjects();
        ResourceLoader.player.setPosition((Gdx.graphics.getWidth() / 2) - (ResourceLoader.player.getWidth() / 2), 0);
        ResourceLoader.player.setSize(64, 64);
    }
    private void drawPlayer(){
        ResourceLoader.player.draw(ResourceLoader.batch);
        ResourceLoader.BulletX = ResourceLoader.player.getX() / 2;
    }

    private void initGameObjects() {
        frontMovingBackground = movHandler.getFrontMovingBackground();
        backMovingBackground = movHandler.getBackMovingBackground();
    }
    private void drawBullet() {
        for (Rectangle raindrop : ResourceLoader.raindrops) {
            ResourceLoader.batch.begin();
            ResourceLoader.batch.draw(ResourceLoader.getBullet(), raindrop.x, raindrop.y);
            ResourceLoader.batch.end();
        }
        for (Rectangle bullet : ResourceLoader.raindrops) {
            bullet.y += 700 * Gdx.graphics.getDeltaTime();
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

        ResourceLoader.batch.begin();
        ResourceLoader.batch.draw(ResourceLoader.getBackground(), ResourceLoader.getFrontMovingBackground().getX(), frontMovingBackground.getY(), frontMovingBackground.getWidth(), frontMovingBackground.getHeight());
        ResourceLoader.batch.draw(ResourceLoader.getBackground(), backMovingBackground.getX(), backMovingBackground.getY(), backMovingBackground.getWidth(), backMovingBackground.getHeight());
        ResourceLoader.batch.end();
        drawBullet();
        ResourceLoader.batch.begin();
        drawPlayer();
        ResourceLoader.batch.draw(ResourceLoader.player1, (MainMenuScreen.SCR_WIDTH / 2) - (ResourceLoader.player1.getWidth() / 2), 440, 64, 64);
        ResourceLoader.batch.end();
    }
    private void Listener() {

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