package ua.pp.blastorq.planebattle.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ua.pp.blastorq.planebattle.objects.MovingBackground;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.sprite.Bullets;
import ua.pp.blastorq.planebattle.sprite.Plane;


public class data {
    private static Texture backgroundTexture, bulletTexture, playerShipTexture;
    private static MovingBackground frontMovingBackground;

    public static Music menuAudio;
    public static OrthographicCamera camera = new OrthographicCamera();
    public static MovHandler movHandler;
    public static Plane player, bot;
    public static SpriteBatch batch;
    public static Stage stage;
    public static Bullets bullets;
    public static Vector3 touchPos;
    public static int vw, vh;
    public static float scale;

    public static MovingBackground getFrontMovingBackground(){return frontMovingBackground;}
    public static Texture getBackgroundTexture(){return backgroundTexture;}
    public static Texture getBulletTexture(){ return bulletTexture;}

    public void load(){
        vw = Gdx.graphics.getWidth();
        vh = Gdx.graphics.getHeight();
        scale = vw/320;

        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(vw, vh));
        touchPos = new Vector3();
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("bg.png");
        playerShipTexture = new Texture("Plane.png");
        bulletTexture = new Texture("bulletTexture.png");
        menuAudio = Gdx.audio.newMusic(Gdx.files.internal("MenuMusic.ogg"));

        movHandler = new MovHandler(0, -100);
        frontMovingBackground = movHandler.getFrontMovingBackground();

        player = new Plane(playerShipTexture, false);
        player.flip(false, true);
        bot = new Plane(playerShipTexture, true);
        bullets = new Bullets();

        menuAudio.setLooping(true);
    }
}
