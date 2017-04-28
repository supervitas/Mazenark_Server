package Rooms;

/**
 * Created by nikolaev on 25.04.17.
 */
public class Room {
    private int maxPlayers = 4;
    private int readyPlayers = 0;
    private int port;
    private int playersCount = 0;
    private int roomID;

    private boolean inGame = false;

    Room(int port, int roomId) {
        this.port = port;
        this.roomID = roomId;
    }

    public boolean AddPlayer() {
        if (playersCount < maxPlayers){
            playersCount++;
            return true;
        }
        return false;
    }

    boolean CanAddPlayer(){
        return playersCount < maxPlayers && !inGame;
    }

    public boolean RemovePlayer() {
        if (playersCount > 0) {
            playersCount--;
            return true;
        }
        return false;
    }

    public void PlayerReady() {
        readyPlayers++;
        if(readyPlayers == playersCount){
            inGame = true;
        }
    }

    public void PlayerUnReady(){
        readyPlayers--;
    }

    int getPlayersCount() {
        return playersCount;
    }

    public int getPort() {
        return port;
    }
}
