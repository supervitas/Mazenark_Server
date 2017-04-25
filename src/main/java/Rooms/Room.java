package Rooms;

/**
 * Created by nikolaev on 25.04.17.
 */
public class Room {
    private static int maxPlayers = 4;
    private int port;
    private int playersCount = 0;
    private int roomID;

    public Room(int port, int roomId) {
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

    public boolean RemovePlayer(){
        if (playersCount > 0) {
            playersCount--;
            return true;
        }
        return false;
    }

    public int getPort() {
        return port;
    }
}
