import static spark.Spark.*;

import Rooms.Room;
import Rooms.RoomManager;

public class main {
    public static void main(String[] args) {
        //Initialisation
        port(9000);
        threadPool(Runtime.getRuntime().availableProcessors());


        RoomManager roomManager = new RoomManager(1);


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