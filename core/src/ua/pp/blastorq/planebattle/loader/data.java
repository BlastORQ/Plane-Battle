package ua.pp.blastorq.planebattle.loader;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import ua.pp.blastorq.planebattle.PlaneBattle;
import ua.pp.blastorq.planebattle.objects.MovingBackground;
import ua.pp.blastorq.planebattle.objects.MovHandler;
import ua.pp.blastorq.planebattle.sprite.Bullets;
import ua.pp.blastorq.planebattle.sprite.HP;
import ua.pp.blastorq.planebattle.sprite.Plane;


public class data {
    private static Texture backgroundTexture, bulletTexture, playerShipTexture, playerHPtexture, botHPtexture;
    private static MovingBackground frontMovingBackground;

    public static Music menuAudio;
    public static OrthographicCamera camera = new OrthographicCamera();
    public static MovHandler movHandler;
    public static Plane player, bot;
    public static HP playerHP, botHP;
    public static SpriteBatch batch;
    public static Stage stage;
    public static Bullets bullets;
    public static Vector3 touchPos;
    public static int vw, vh;
    public static float scale;

    public static MovingBackground getFrontMovingBackground(){return frontMovingBackground;}
    public static Texture getBackgroundTexture(){return backgroundTexture;}
    public static Texture getBulletTexture(){ return bulletTexture;}
    public static PlaneBattle game;

    public void load(PlaneBattle game){
        this.game = game;
        vw = Gdx.graphics.getWidth();
        vh = Gdx.graphics.getHeight();
        scale = vw/320;

        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(vw, vh));
        touchPos = new Vector3();
        Gdx.input.setInputProcessor(stage);

        backgroundTexture = new Texture("bg.png");
        playerShipTexture = new Texture("spacecraft.png");
        bulletTexture = new Texture("bullet.png");
        playerHPtexture = new Texture("hp_player.png");
        botHPtexture = new Texture("hp_bot.png");
        menuAudio = Gdx.audio.newMusic(Gdx.files.internal("menu.ogg"));

        movHandler = new MovHandler(0, -100);
        frontMovingBackground = movHandler.getFrontMovingBackground();

        player = new Plane(playerShipTexture, false);
        player.setSize(64*scale, 64*scale);
        player.setPosition((vw - player.getWidth()) / 2, 0);

        bot = new Plane(playerShipTexture, true);
        bot.flip(false, true);
        bot.setSize(64*scale, 64*scale);
        bot.setPosition((vw - bot.getWidth()) / 2, vh-160-(64*scale));
        bullets = new Bullets();

        playerHP = new HP(playerHPtexture, player, false);
        playerHP.setSize(vw/2 - 64, 24*scale);
        playerHP.setStartWidth(vw/2 - 64);
        playerHP.setPosition(32, vh - 64);

        botHP = new HP(botHPtexture, bot, true);
        botHP.setSize(vw/2 - 64, 24*scale);
        botHP.setStartWidth(vw/2 - 64);
        botHP.setPosition(vw/2 + 32, vh - 64);

        menuAudio.setLooping(true);
    }
}
