import static spark.Spark.*;


import Constants.Response;
import Controllers.RoomController;
import Rooms.Room;
import Rooms.RoomManager;
import org.json.JSONObject;

public class main {
    public static void main(String[] args) {
        //Initialisation
        port(8000);
        threadPool(Runtime.getRuntime().availableProcessors());

        RoomManager roomManager = new RoomManager(1);

        RoomController roomController = new RoomController(roomManager);


        path("/api", () -> {
            before("/*", (req, res) -> res.type("application/json"));

            path("/room", () -> {
                get("/getRoom", roomController::GetRoom);
                post("/playerLeft", roomController::PlayerLeft);
                post("/playerJoined", roomController::PlayerJoined);
                post("/gameStarted", roomController::GameStarted);
                post("/gameEnded", roomController::GameEnded);
            });


            post("/gameresult", (req, res) -> Response.OK);
        });


    }
}