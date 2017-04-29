import static spark.Spark.*;

import Rooms.Room;
import Rooms.RoomManager;

public class main {
    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager(20);
        port(9000);

        path("/api", () -> {
            before("/*", (q, a) -> a.type("application/json"));

            get("/port", (req, res) -> {
                Room active = roomManager.GetActiveRoom();

                return String.format("{\"port\":\"%s\"}", Integer.toString(active.getPort()));
            });

            post("/gameresult", (req, res) -> "{\"status\":\"OK\"}");
        });


    }
}