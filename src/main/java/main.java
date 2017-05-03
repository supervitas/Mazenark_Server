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

            //Public Methods
            get("/getRoom", roomController::GetRoom);

            path("/auth", () -> {
                post("/register", (req, res) -> Response.OK); // todo
                post("/login", (req, res) -> Response.OK); // todo
            });


            // Unity Server Methods
            path("/room", () -> { // this methods sending from unity instance and accepted only from localhost
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

            path("/game", () -> {
                before("/*", (req, res) -> {
                    if(!req.host().toLowerCase().contains("localhost")) {
                        halt(401);
                    }
                });
                post("/gameResults", (req, res) -> Response.OK); // todo
            });

        });


    }
}