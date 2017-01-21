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

import java.util.HashMap;

import ua.pp.blastorq.planebattle.actors.Button;
import ua.pp.blastorq.planebattle.actors.HitButton;
import ua.pp.blastorq.planebattle.actors.Left;
import ua.pp.blastorq.planebattle.actors.Right;
import ua.pp.blastorq.planebattle.objects.Background;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.sprite.Plane;


public class ResourceLoader {
    public static Music menu, game;
    public static Texture background, btn, line;
    public static HitButton hitButton;
    public static Background frontBackground, backBackground;
    public static OrthographicCamera camera = new OrthographicCamera();
    public static Plane player, player1;
    public static Left LeftButton;
    public static boolean isLeft = false, isRight = false, isShoot;
    public static SpriteBatch batch;
    public static Stage stage;
    public static Right RightButton;
    public static Texture playerShip, HitButtonImage, Bullet, playerShip1;
    public static Array<Rectangle> raindrops;
    public static double bulletx, bullety = 64 + 128;
    public static boolean ismiddle = false;
    public static HashMap<String, Plane> friendlyPlayers;
    public static Vector3 touchPos;
    public static Texture BulletImage;
    public static float accel = 0;
    public static MovHandler movHandler;
    public static Button button;
    private final float UPDATE_TIME = 1 / 60f;
    boolean isHit = false;

    public void load() {
        background = new Texture("bg.png");
        BulletImage = new Texture("icon.png");
        btn = new Texture("btn.png");
        playerShip1 = new Texture("Plane1.png");
        hitButton = new HitButton(BulletImage);
        line = new Texture("line.png");
        movHandler = new MovHandler(0, -100);
        raindrops = new Array<Rectangle>();
        menu = Gdx.audio.newMusic(Gdx.files.internal("proj1_menu.ogg"));
        menu.setLooping(true);
        RightButton = new Right(new Texture("right.png"));
        Texture rightimage, leftimage;
        touchPos = new Vector3();
        leftimage = new Texture("left.png");
        LeftButton = new Left(leftimage);
        rightimage = new Texture("right.png");
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        playerShip = new Texture("Plane.png");
        player = new Plane(playerShip);
        RightButton = new Right(rightimage);
        HitButtonImage = new Texture("icon.png");
        Bullet = new Texture("bullet.png");
        friendlyPlayers = new HashMap<String, Plane>();
        stage.addActor(RightButton);
        stage.addActor(LeftButton);
        stage.addActor(hitButton);
        frontBackground = movHandler.getFrontBackground();
        backBackground = movHandler.getBackBackground();
        Gdx.input.setInputProcessor(stage);
        player1 = new Plane(playerShip1);
    }
}
