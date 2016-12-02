package ua.pp.blastorq.planebattle;

//TODO зробити перевірку зьеднання з інтернетом (на андроїді)
//TODO зробити плавний перехід між екраном літачка
//TODO зробити синхронний мультиплеєр
//TODO зробити хп
import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.viewport.*;
import org.json.*;
import java.util.HashMap;
import io.socket.client.*;
import io.socket.emitter.*;
import ua.pp.blastorq.planebattle.actors.*;
import ua.pp.blastorq.planebattle.sprite.Plane;

public class PlaneBattle extends Game {
	private final float UPDATE_TIME = 1/60f;
	float timer;
	private Plane player;
	private Left LeftButton;
	private boolean isLeft = false, isRight = false;
	private SpriteBatch batch;
	private Stage stage;
	private Socket socket;
	private String id;
	private Right RightButton;
	private  Texture playerShip, friendlyShip;

	private HashMap<String, Plane> friendlyPlayers;
	@Override
	public void create() {
		Texture right = new Texture(Gdx.files.internal("right.png"));
		Texture left = new Texture("left.png");
		Viewport viewport = new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		stage = new Stage(viewport);
		RightButton = new Right(right);
		LeftButton = new Left(left);
		Listeners();
		stage.addActor(LeftButton);
		batch = new SpriteBatch();
		playerShip = new Texture("Plane.png");
		friendlyShip = new Texture("Plane1.png");
		friendlyPlayers = new HashMap<String, Plane>();
		connectSocket();
		configSocketEvents();
		stage.addActor(RightButton);
		stage.addActor(LeftButton);
		Gdx.input.setInputProcessor(stage);
	}


	private void handleInput(final float dt){
		if(player != null) {
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || isLeft) {
				player.setPosition(player.getX() + (-200 * dt), player.getY());
			} else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || isRight){
				player.setPosition(player.getX() + (+200 * dt), player.getY());
			}else if(Gdx.input.isKeyPressed(Input.Keys.UP)){
				player.setPosition(player.getX(), player.getY() + (200* dt));
			}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
				player.setPosition(player.getX(), player.getY() - 200*dt);
			}
			if(player.getX() <0){
				player.setPosition(Gdx.graphics.getWidth() - player.getWidth(), player.getY());
			}else if(player.getX() + playerShip.getWidth()> Gdx.graphics.getWidth()){
				player.setPosition(0, 64);
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
	public void render() {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		handleInput(Gdx.graphics.getDeltaTime());
		updateServer(Gdx.graphics.getDeltaTime());
		batch.begin();
		drawPlayer();
		drawAllPlayers();
		batch.end();
		drawStage();
	}
	private void Listeners(){
		RightButton.addListener(new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
				isRight = true;
				return super.touchDown(event, x, y, pointer, button);
			}

			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				isRight = false;
				super.touchUp(event, x, y, pointer, button);
			}
		});
		LeftButton.addListener(new ClickListener(){
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
	}
	private void drawStage(){
		stage.draw();
		stage.act(Gdx.graphics.getDeltaTime());
	}
	private void drawPlayer(){
		if (player != null) {
			player.draw(batch);
		}
	}
	private void drawAllPlayers(){
		for(HashMap.Entry<String, Plane> entry : friendlyPlayers.entrySet()){
			entry.getValue().draw(batch);
		}
	}
	@Override
	public void pause() {
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {

	}

	private void connectSocket(){
		try {
			socket = IO.socket("http://195.133.147.164:8080");
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
