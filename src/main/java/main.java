import static spark.Spark.*;

import DB.MongoDriver;

import Constants.Response;
import Controllers.AuthController;
import Controllers.RoomController;
import Rooms.RoomManager;
import Users.UserManager;


public class main {
    public static void main(String[] args) {
        //Initialisation

        port(7000);
        threadPool(Runtime.getRuntime().availableProcessors());

        MongoDriver mongoDriver = new MongoDriver();

        RoomManager roomManager = new RoomManager(1);
        UserManager userManager = new UserManager(mongoDriver);

        RoomController roomController = new RoomController(roomManager);

        AuthController authController = new AuthController(userManager);

        path("/api", () -> {
            before("/*", (req, res) -> {
                res.type("application/json");
            });
            // Public Methods
            get("/getRoom", roomController::GetRoom);

            path("/auth", () -> {
                post("/register", authController::Register);
                post("/login", authController::LogIn);
                post("/logout", authController::LogOut);
                post("/guest", authController::BecomeGuest);
                //post("/deguest", authController::RiseFromGuest);
            });

            path("/user", () -> {
                post("/id", authController::GetUserByID);
                post("/token", authController::GetUserByToken);
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