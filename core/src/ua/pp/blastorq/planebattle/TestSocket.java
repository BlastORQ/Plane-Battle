package ua.pp.blastorq.planebattle;
import io.socket.client.Ack;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

class TestSocket {
    public Socket socket;
    Player player = null, oppositePlayer = null;
    public static void main(String[] args) {
        TestSocket main = new TestSocket();
        main.connectSocket();
        main.configSocketEvents();
        main.lifecycle();
    }
    private void connectSocket(){
        try {
            socket = IO.socket("http://192.168.1.105:8080");
            socket.connect();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    private void lifecycle(){
        Runnable run = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if (player != null) {
                            socket.emit("playerMoved", player.getId(), "right");
                            socket.emit("getPlayerPosition", player.getId());
                        }
                        if (oppositePlayer != null) {
                            socket.emit("getPlayerPosition", oppositePlayer.getId(), "top");
                            socket.emit("getPlayerPosition", oppositePlayer.getId(), "left");
                            socket.emit("getPlayerPosition", oppositePlayer.getId(), "right");
                            socket.emit("getPlayerPosition", oppositePlayer.getId(), "down  ");
                        }
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        Thread thread = new Thread(run);
        thread.start();
    }

    private void configSocketEvents(){
        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Success connection!");
            }
        }).on("createPlayer", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("Creating player");
                JSONObject object = (JSONObject) args[0];
                try {
                    player = new Player(object.getString("id"), object.getInt("x"), object.getInt("y"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                socket.emit("playerMoved", player.getId(), 30, 30);
                System.out.println("Waiting for teamate!");
            }
        }).on("createdRoom", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                System.out.println("ROOM CREATED!!");
                JSONObject oppositeplayerJsonObject = (JSONObject) args[0];
                try {
                    oppositePlayer = new Player(oppositeplayerJsonObject.getString("id"), oppositeplayerJsonObject.getInt("x"), oppositeplayerJsonObject.getInt("y"));
                } catch (Exception e) {
                    System.out.println("Json Exception while creating room");
                }
            }
        }).on("getPlayerPositionSuccess", new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                JSONObject jsonData = (JSONObject) args[0];
                try {
                    if (jsonData.getString("id").equals(player.getId())) {
                        player.setX(jsonData.getInt("x"));
                        player.setY(jsonData.getInt("y"));
                        System.out.println("Player moved: Nex X is - " + player.getX() + " Nex Y is - " + player.getY());
                    } else if (jsonData.getString("id").equals(oppositePlayer.getId())) {
                        oppositePlayer.setX(jsonData.getInt("x"));
                        oppositePlayer.setY(jsonData.getInt("y"));
                        System.out.println(String.format("Opposite player moved to (%s, %s)", oppositePlayer.getX(), oppositePlayer.getY()));
                    }
                } catch (Exception e) {
                    System.out.println("error setting position");
                }
            }
        });
    }
}
class Player{

    private float x,y;
    private String id;

    Player(String id, float x, float y ){
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}