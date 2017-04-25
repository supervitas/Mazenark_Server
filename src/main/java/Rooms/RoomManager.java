package Rooms;

import java.util.List;

/**
 * Created by nikolaev on 25.04.17.
 */
public class RoomManager {
    private List<Room> activeRooms;
    private int roomsCount = 0;
    private int roomLimit;

    public RoomManager(int roomLimit){
        this.roomLimit = roomLimit;
    }

    public List<Room> GetActiveRooms(){
        return activeRooms;
    }

    public void AddRoom(Room room){
          activeRooms.add(room);
    }
    public void CreateRoom(){
        if (roomsCount <= roomLimit){
            AddRoom(new Room(9000, roomsCount++)); //todo
        }
    }
}
