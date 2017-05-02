import static spark.Spark.*;
import static spark.route.HttpMethod.delete;


import Constants.Response;
import Rooms.Room;
import Rooms.RoomManager;
import org.json.JSONObject;

public class main {
    public static void main(String[] args) {
        //Initialisation
        port(9000);
        threadPool(Runtime.getRuntime().availableProcessors());


        RoomManager roomManager = new RoomManager(1);


        path("/api", () -> {
            before("/*", (req, res) -> res.type("application/json"));

            get("/getRoom", (req, res) -> {
                Room active = roomManager.GetActiveRoom();
                if(active != null) {
                    active.AddPlayer();
                    return String.format("{\"port\":\"%s\"}", Integer.toString(active.getPort()));
                } else {
                    res.status(400);
                    return "{\"error\":\"Sorry, all mazes are filled\"}";
                }
            });

            post("/gameStarted", (req, res) -> {
                JSONObject obj = new JSONObject(req.body());
                int roomId = Integer.parseInt(obj.getString("room"));
                Room room = roomManager.GetRoomById(roomId);
                if(room != null) {
                    room.SetInGame(true);
                }
                return Response.OK;
            });

            post("/room/playerLeft", (req, res) -> {
                JSONObject obj = new JSONObject(req.body());
                int roomId = Integer.parseInt(obj.getString("room"));
                Room room = roomManager.GetRoomById(roomId);
                if(room != null) {
                    room.RemovePlayer();
                }
                return Response.OK;
            });

            post("/gameresult", (req, res) -> "{\"status\":\"OK\"}");
        });


    }
}