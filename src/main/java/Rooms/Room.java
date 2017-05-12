package Rooms;
import java.io.File;

/**
 * Created by nikolaev on 25.04.17.
 */
public class Room {
    private int maxPlayers = 4;
    private int port;
    private int playersCount = 0;
    private int roomID;

    private boolean inGame = false;

    Room(int port, int roomId) {
        this.port = port;
        this.roomID = roomId;
//        new Thread(this::CreateUnityInstance).run();
    }

    public void SetInGame(boolean inGame){
        this.inGame = inGame;
    }

    //this method is platform dependent - https://docs.unity3d.com/Manual/CommandLineArguments.html
    private void CreateUnityInstance() {

        String[] command = {"./mazenark.x86", "-batchmode", "-nographics", "-server", "true",
                "-port", Integer.toString(this.port), "-instanceid", Integer.toString(this.roomID)};
        ProcessBuilder probuilder = new ProcessBuilder(command).inheritIO();

        // change this to yours build location
        probuilder.directory(new File("/home/frog/mazenark/linux"));

        try {
            probuilder.start();
        } catch (Exception e) {
          e.printStackTrace();
        }
    }

    public boolean AddPlayer() {
        if (playersCount < maxPlayers){
            playersCount++;
            return true;
        }
        return false;
    }

    boolean CanAddPlayer() {
        return playersCount < maxPlayers && !inGame;
    }

    public boolean RemovePlayer() {
        if (playersCount > 0) {
            playersCount--;
            return true;
        }
        return false;
    }

    public void RemoveAllPlayers() {
        playersCount = 0;
    }


    int getPlayersCount() {
        return playersCount;
    }

    public int getPort() {
        return port;
    }
    int getRoomID() {
        return roomID;
    }
}
