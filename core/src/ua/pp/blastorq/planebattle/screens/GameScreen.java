package ua.pp.blastorq.planebattle.screens;

//TODO зробити перевірку зьеднання з інтернетом (на андроїді)
//TODO зробити плавний перехід між екраном літачка
//TODO зробити синхронний мультиплеєр
//TODO зробити хп
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.Date;
import java.util.Iterator;
import java.util.Timer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.StretchViewport;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import ua.pp.blastorq.planebattle.actors.HitButton;
import ua.pp.blastorq.planebattle.actors.Left;
import ua.pp.blastorq.planebattle.actors.Right;
import ua.pp.blastorq.planebattle.sprite.Plane;
public class GameScreen implements Screen
{

    private Texture[] bullmass = new Texture[10];
    private float timer;
    private OrthographicCamera camera = new OrthographicCamera();
    private Plane player;
    private Left LeftButton;
    private boolean isLeft = false, isRight = false, isShoot;
    private Timer time = new Timer();
    private SpriteBatch batch;
    private Stage stage;
    private Socket socket;
    private String id;
    private  Texture playerShip, friendlyShip, HitButtonImage, Bullet;
    Array<Rectangle> raindrops;
    private final float UPDATE_TIME = 1/60f;
    float timing;
    private double bulletx , bullety = 64 + 128;
    boolean ismiddle = false;
    private HashMap<String, Plane> friendlyPlayers;
    boolean isHit = false;
    private Vector3 touchPos;
    private float accel = 0;
    public GameScreen() {
        Right RightButton;

        HitButton hitButton;
        raindrops = new Array<Rectangle>();
        Texture  rightimage, leftimage;
        final float UPDATE_TIME = 1/60f;
        touchPos = new Vector3();
        leftimage = new Texture("left.png");
        LeftButton = new Left(leftimage);
        rightimage = new Texture("right.png");
        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        playerShip = new Texture("Plane.png");
        friendlyShip = new Texture("Plane1.png");
        hitButton = new HitButton(HitButtonImage);
        RightButton = new Right(rightimage);
        HitButtonImage = new Texture("icon.png");
        Bullet = new Texture("bullet.png");
        friendlyPlayers = new HashMap<String, Plane>();
        connectSocket();
        configSocketEvents();
        stage.addActor(RightButton);
        stage.addActor(LeftButton);
       // stage.addActor(hitButton);
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
    private void spawnRaindrop(){
        Rectangle raindrop = new Rectangle();
        raindrop.x = player.getX() + (player.getWidth()/2) - (Bullet.getWidth()/2);
        raindrop.y = 192;
        raindrop.width = 64;
        raindrop.height = 64;
        raindrops.add(raindrop);
    }
    public void drawdrops(){
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
    public void SendRequest(){
        Net.HttpRequest request = new Net.HttpRequest(Net.HttpMethods.GET);
        request.setUrl("https://libgdx.badlogicgames.com");
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                Gdx.app.log("Sucsessful connect", "");
            }

            @Override
            public void failed(Throwable t) {
                Gdx.app.log("Failed to connect", "");
                Gdx.app.exit();

            }

            @Override
            public void cancelled() {
                Gdx.app.log("Failed to connect", "");
            }
        });
    }


    private void timermethod(){
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;

        while (elapsedTime < 60*1000) {
            //perform db poll/check
            Gdx.app.log("dc", "df");
            elapsedTime = (new Date()).getTime() - startTime;
        }
    }
    private void handleInput(final float dt){
        if(accel != 0){
            accel /= 1.4;
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
                    //Gdx.app.log("LOG", "M");
                     //   spawnRaindrop();
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

    private void updateServer(float dt){
        timer += dt;
        if(timer >= UPDATE_TIME && player !=null && player.hasMoved()){
            JSONObject data = new JSONObject();
            try {
                data.put("x", player.getX());
                data.put("y", player.getY());
                socket.emit("playerMoved", data);
            }catch (JSONException e){
                Gdx.app.log("SOCKET IO", "Error sending update data");
            }
        }
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        handleInput(Gdx.graphics.getDeltaTime());
        updateServer(Gdx.graphics.getDeltaTime());

        if(player == null) {
            connectSocket();
            Gdx.app.log("FAIL", "CONNECT");
        }
        timing +=delta;
        Listener();
        if(Gdx.input.justTouched() && ismiddle){
            spawnRaindrop();
        }
        drawdrops();
        batch.begin();
        drawPlayer();
        drawAllPlayers();
        batch.end();
        drawStage();
    }
    public void Listener() {
        LeftButton.addListener(new ClickListener()
        {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.log("LEFT", "PRESS");
                return super.touchDown(event, x, y, pointer, button);
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

                super.touchUp(event, x, y, pointer, button);
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

    private void connectSocket(){
        try {
            socket = IO.socket("http://35.163.56.214:8080/");
            socket.connect();
        }catch(Exception e){
            Gdx.app.log("NO", "");
        }
    }

    private void configSocketEvents(){
        try{
            socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    Gdx.app.log("SocketIO", "Connected");
                    player = new Plane(playerShip);
                }
            }).on("socketID", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        id = data.getString("id");
                        Gdx.app.log("SocketIO", "My ID: " + id);
                    } catch (JSONException e) {
                        Gdx.app.log("SocketIO", "Error getting ID");
                    }
                }
            }).on("newPlayer", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String playerId = data.getString("id");
                        Gdx.app.log("SocketIO", "New Player Connect: " + playerId);
                        friendlyPlayers.put(playerId, new Plane(friendlyShip));
                    } catch (JSONException e) {
                        Gdx.app.log("SocketIO", "Error getting New PlayerID");
                    }
                }
            }).on("playerDisconnected", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        id = data.getString("id");
                        friendlyPlayers.remove(id);
                    } catch (JSONException e) {
                        Gdx.app.log("SocketIO", "Error getting disconnected PlayerID");
                    }
                }
            }).on("playerMoved", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        String playerId = data.getString("id");
                        Double x = data.getDouble("x");
                        Double y = data.getDouble("y");

                        bulletx = (Double) x/2;
                        if(friendlyPlayers.get(playerId) !=null){
                            friendlyPlayers.get(playerId).setPosition(x.floatValue(), y.floatValue());
                        }
                    } catch (JSONException e) {
                        Gdx.app.log("SocketIO", "Error getting disconnected PlayerID");
                    }
                }
            }).on("getPlayers", new Emitter.Listener() {
                @Override
                public void call(Object... args) {
                    JSONArray objects = (JSONArray) args[0];
                    try {
                        for (int i = 0; i < objects.length(); i++) {
                            Plane coopPlayer = new Plane(friendlyShip);
                            Vector2 position = new Vector2();
                            position.x = ((Double) objects.getJSONObject(i).getDouble("x")).floatValue();
                            position.y = ((Double) objects.getJSONObject(i).getDouble("y")).floatValue();
                            coopPlayer.setPosition(position.x, position.y);
                            friendlyPlayers.put(objects.getJSONObject(i).getString("id"), coopPlayer);
                        }
                    } catch (JSONException e) {
                        Gdx.app.log("" + e.getMessage(), "");
                    }
                }
            }); }catch(Exception e) {
            Gdx.app.log("NO CONFIG EVENT", "");
        }
    }
}