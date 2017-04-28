import static spark.Spark.*;
import Rooms.RoomManager;

public class main {
    public static void main(String[] args) {
        RoomManager roomManager = new RoomManager(20);

        port(9000);
        get("/port", (req, res) -> {
            res.type("application/json");
            return "{\"port\":\"9000\"}";
        });
    }
}