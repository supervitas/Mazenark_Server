package Rooms;

import java.io.Console;
import java.util.List;

/**
 * Created by nikolaev on 25.04.17.
 */
public class RoomManager {
    private List<Room> activeRooms;
    private int roomsCount = 0;
    private int roomLimit;

    private int roomsStartPort = 10000;

    public RoomManager(int roomLimit) {
        this.roomLimit = roomLimit;

        for (int i = 0; i < this.roomLimit; i++) {
            if(CreateRoom(roomsStartPort)) {
                roomsStartPort++;
            } else {
                System.out.println("Error in creating rooms");
            }
        }
    }

    public Room GetActiveRoom() {
        Room suitableRoom = null;
        int maxPlayersInPreviousRoom = 0;
        for (Room room : activeRooms) {
            if(room.getPlayersCount() > maxPlayersInPreviousRoom && room.CanAddPlayer()) {
                maxPlayersInPreviousRoom = room.getPlayersCount();
                suitableRoom = room;
            }
        }
        // if null - all rooms are ocupied;
        return suitableRoom;
    }

    public List<Room> GetActiveRooms(){
        return activeRooms;
    }

    private void AddRoom(Room room){
          activeRooms.add(room);
    }

    private boolean CreateRoom(int port) {
        if (roomsCount < roomLimit) {
            AddRoom(new Room(port, roomsCount++));
            return true;
        }
        return false;
    }
}
