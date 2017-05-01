import static spark.Spark.*;
import static spark.route.HttpMethod.delete;


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
                active.AddPlayer();
                return String.format("{\"port\":\"%s\"}", Integer.toString(active.getPort()));
            });

            delete("/room/player", (req, res) -> {
                //todo parse json and get roomID from which player lefted
//                Room room = roomManager.GetRoomById(res.)
                return "{\"status\":\"OK\"}";
            });

            post("/gameresult", (req, res) -> "{\"status\":\"OK\"}");
        });


    }
}