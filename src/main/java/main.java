import static spark.Spark.*;

import Rooms.Room;
import Rooms.RoomManager;

public class main {
    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager(20);

        port(9000);
        get("/port", (req, res) -> {
            Room active = roomManager.GetActiveRoom();
            res.type("application/json");

            return String.format("{\"port\":\"%s\"}", Integer.toString(active.getPort()));
        });

    }
}