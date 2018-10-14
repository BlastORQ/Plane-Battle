var app = require('express')();
var server = require('http').Server(app);
var io = require('socket.io')(server);
var players = [];
var rooms = [];
var playercount = 0;
var sockets = {};

server.listen(8080, function(){
    console.log("Server is now running...");
});
io.on('connection', function(socket){
    console.log('Connect!');
    sockets[socket.id] = socket;
    players.push(new player(socket.id, 0, 0));
    playercount++;
    if(playercount % 2 == 0){
        var player1 = players[playercount-1];
        var player2 = players[playercount-2];
        rooms.push(new room(player1, player2 ) );
        sockets[player1.id].emit('createdRoom', player2);
        sockets[player2.id].emit('createdRoom', player1);
    }
    socket.emit('createPlayer',players[playercount-1]);
    socket.on('playerMoved', function(id, direction){
        var player = getPlayerById(id);
        if(player == null){
            console.log("player is null at playerMoved method");
            return;
        }
        if(direction == "right"){
            player.x +=1;
        }else if(direction == "left"){
            player.x -=1;
        }else if(direction == "top"){
            player.y +=1;
        }else if(direction == "down"){
            player.y -=1;
        }else{
            console.log("invalid direction");
        }
    });
    socket.on('getPlayerPosition', function (id) {
        var player = getPlayerById(id);
        if(player == null) {
            console.log('null player at getPlayerPosition method');
            return;
        }
        socket.emit('getPlayerPositionSuccess', player);
    });

    socket.on('disconnect', function(){
        delete sockets[socket.id];
        delete players[socket.id];
    });
});

function player(id, x, y){
    this.id = id;
    this.x = x;
    this.y = y;
}
function room(player1, player2){
    this.roomId = player1.id + player2.id;
    this.player1 = player1;
    this.player2 = player2;
}

function getPlayerById(id){
    for(var i = 0;i<players.length;i++){
        if(players[i].id == id){
            return players[i];
        }
    }
    return null;
}