import static spark.Spark.*;


import Constants.Response;
import Controllers.RoomController;
import Rooms.Room;
import Rooms.RoomManager;
import org.json.JSONObject;

public class main {
    public static void main(String[] args) {
        //Initialisation
        port(7000);
        threadPool(Runtime.getRuntime().availableProcessors());

        RoomManager roomManager = new RoomManager(1);

        RoomController roomController = new RoomController(roomManager);


        path("/api", () -> {
            before("/*", (req, res) -> {
                System.out.println(req.host());
                res.type("application/json");
            });

            get("/getRoom", roomController::GetRoom); // public method

            path("/room", () -> { // unity instance methods accepted only  from localhost
                before("/*", (req, res) -> {
                    if(!req.host().toLowerCase().contains("localhost")) {
                        halt(401);
                    }
                });
                post("/playerLeft", roomController::PlayerLeft);
                post("/playerJoined", roomController::PlayerJoined);
                post("/gameStarted", roomController::GameStarted);
                post("/gameEnded", roomController::GameEnded);
            });


            post("/gameresult", (req, res) -> Response.OK);
        });


    }
}