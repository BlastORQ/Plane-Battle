package ua.pp.blastorq.planebattle.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ua.pp.blastorq.planebattle.objects.MovingBackground;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.sprite.Plane;


public class ResourceLoader {
    public static Music menu;
    private static Texture background;
    public static OrthographicCamera camera = new OrthographicCamera();
    public static Plane player, player1;
    public static SpriteBatch batch;
    public static Stage stage;
    private static Texture Bullet;
    public static Array<Rectangle> raindrops;
    public static double BulletX;
    public static Vector3 touchPos;

    public static MovingBackground getFrontMovingBackground() {
        return frontMovingBackground;
    }
    public static Texture getBackground() {
        return background;
    }
    private static MovingBackground frontMovingBackground;

    public static Texture getBullet(){ return Bullet;}
    public void load() {
        MovHandler movHandler = new MovHandler(0, -100);
        frontMovingBackground = movHandler.getFrontMovingBackground();
        background = new Texture("bg.png");
        Texture playerShip = new Texture("Plane.png");
        raindrops = new Array<Rectangle>();
        Texture playerShip1 = new Texture("Plane.png");
        menu = Gdx.audio.newMusic(Gdx.files.internal("MenuMusic.ogg"));
        menu.setLooping(true);
        touchPos = new Vector3();
        Bullet  = new Texture("bullet.png");
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));

        player = new Plane(playerShip);
        Gdx.input.setInputProcessor(stage);
        player1 = new Plane(playerShip1);
    }


}
