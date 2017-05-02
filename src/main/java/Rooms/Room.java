package Rooms;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

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
        new Thread(this::CreateUnityInstance).run();
    }

    //this method is platform dependent - https://docs.unity3d.com/Manual/CommandLineArguments.html
    private void CreateUnityInstance() {

        String[] command = {"./test", "-batchmode", "-nographics", "-server", "true",
                "-port", Integer.toString(this.port), "-instanceid", Integer.toString(this.roomID)};
        ProcessBuilder probuilder = new ProcessBuilder( command ).inheritIO();

        // change this to yours build location
        probuilder.directory(new File("/Users/nikolaev/Desktop/test.app/Contents/MacOS"));

        try {
            Process process = probuilder.start();
            //Read out dir output
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            System.out.printf("Output of running %s is:\n",
                    Arrays.toString(command));
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

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
