package Rooms;

import java.io.Console;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by nikolaev on 25.04.17.
 */
public class RoomManager {
    private List<Room> activeRooms = new ArrayList<>();
    private int roomLimit;
    private AtomicInteger idGenerator = new AtomicInteger();

    private int roomsStartPort = 8000;

    public RoomManager(int roomLimit) {
        this.roomLimit = roomLimit;
        /*for (int i = 0; i < this.roomLimit; i++) {
            if(CreateRoom(GetFreePort())) {

            } else {
                System.out.println("Error in creating rooms");
            }
        }*/
    }

    public Room GetActiveRoom() {
        Room suitableRoom = null;
        int maxPlayersInPreviousRoom = 0;
        for (Room room : activeRooms) {
            if(room.getPlayersCount() > maxPlayersInPreviousRoom && room.CanAddPlayer()) {
                maxPlayersInPreviousRoom = room.getPlayersCount();
                suitableRoom = room;
            }
            if (suitableRoom == null && room.CanAddPlayer()) {
                suitableRoom = room;
            }
        }

        if (suitableRoom == null) {
            int port = GetFreePort();
            if (port != 0) {
                suitableRoom = TryCreateRoom(port);
            }
        }

        return suitableRoom;
    }

    public Room GetRoomById(int id) {
        for (Room room : activeRooms) {
            if(room.getRoomID() == id){
                return room;
            }
        }
        return null;
    }

    public void DeleteRoom(Room room) {
        activeRooms.remove(room);
        room.KillRoomProcess();
    }


    private Room TryCreateRoom(int port) {
        if (GetRoomsCount() < roomLimit) {
            Room newRoom = new Room(this, port, idGenerator.getAndIncrement());

            activeRooms.add(newRoom);
            return newRoom;
        }
        return null;
    }

    private int GetFreePort() {
        Set<Integer> freePorts = new HashSet<>();
        for (int i = 0; i < roomLimit; i++) {
            freePorts.add(roomsStartPort + i);
        }

        for (Room room: activeRooms) {
            freePorts.remove(room.getPort());
        }

        if (freePorts.isEmpty())
            return 0;

        return (Integer) freePorts.toArray()[0];
    }

    private int GetRoomsCount() {
        return activeRooms.size();
    }

}
