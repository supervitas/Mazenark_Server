package Controllers;
import static spark.Spark.*;


import Constants.Response;
import Rooms.Room;
import Rooms.RoomManager;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Spark;

/**
 * Created by nikolaev on 03.05.17.
 */
public class RoomController {
    private RoomManager roomManager;

    public RoomController(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public String PlayerLeft(Request req, spark.Response res) {
        Room room = this.GetRoomFromJsonById(req.body());
        if (room != null) {
            room.RemovePlayer();
        }
        return Response.OK;
    }

    public String PlayerJoined(Request req, spark.Response res) {
        Room room = this.GetRoomFromJsonById(req.body());
        if (room != null) {
            room.AddPlayer();
        }
        return Response.OK;
    }

    public String GetRoom(Request req, spark.Response res) {
        Room active = roomManager.GetActiveRoom();
        if(active != null) {
            return String.format("{\"port\":\"%s\"}", Integer.toString(active.getPort()));
        } else {
            res.status(400);
            return "{\"error\":\"Sorry, all mazes are filled\"}";
        }
    }

    public String GameStarted(Request req, spark.Response res) {
        Room room = this.GetRoomFromJsonById(req.body());
        if(room != null) {
            room.SetInGame(true);
        }
        return Response.OK;
    }

    public String GameEnded(Request req, spark.Response res) {
        Room room = this.GetRoomFromJsonById(req.body());
        if(room != null) {
            room.SetInGame(false);
            room.RemoveAllPlayers();
        }
        return Response.OK;
    }


    private Room GetRoomFromJsonById(String body) {
        Room room = null;
        try {
            JSONObject obj = new JSONObject(body);
            int roomId = Integer.parseInt(obj.getString("room"));
            room = roomManager.GetRoomById(roomId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return room;
    }
}
