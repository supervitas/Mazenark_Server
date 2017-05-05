package Controllers;

import Users.User;
import Users.UserManager;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import java.util.HashMap;

import static Constants.Response.*;

public class AuthController {
    private UserManager userManager;

    public AuthController(UserManager userManager) {
        this.userManager = userManager;
    }

    public String LogIn(Request req, Response res) {
        HashMap<String, String> userData = ParseUserData(req.body());
        if (userData == null || !userData.containsKey("username") || !userData.containsKey("password")) {
            res.status(400);
            return BAD_JSON;
        }

        User user = userManager.GetUser(userData.get("username"), userData.get("password"));
        if (user == null) {
            res.status(400);
            return NO_USER;
        }

        userManager.LogIn(user);

        return OkPlusUserInfo(user);
    }

    public String Register(Request req, Response res) {
        HashMap<String, String> userData = ParseUserData(req.body());
        if (userData == null || !userData.containsKey("username") || !userData.containsKey("password")) {
            res.status(400);
            return BAD_JSON;
        }

        User user = userManager.Register(userData.get("username"), userData.get("password"));
        if (user == null) {
            res.status(400);
            return USER_EXISTS;
        }

        userManager.LogIn(user);

        return OkPlusUserInfo(user);
    }

    public String LogOut(Request req, Response res) {
        HashMap<String, String> userData = ParseUserData(req.body());
        if (userData == null || !userData.containsKey("username") || !userData.containsKey("password")) {
            res.status(400);
            return BAD_JSON;
        }

        User user = userManager.GetUser(userData.get("username"), userData.get("password"));
        if (user == null) {
            res.status(400);
            return NO_USER;
        }

        userManager.LogOut(user);

        return OK;
    }

    public String BecomeGuest(Request req, Response res) {
        User user = userManager.NewGuest();
        if (user == null) {
            res.status(500);
            return GUEST_QUOTA_REACHED;
        }

        userManager.LogIn(user);

        return OkPlusUserInfo(user);
    }

    private HashMap<String, String> ParseUserData(String data) {
        HashMap<String, String> userData = new HashMap<>();
        try {
            JSONObject obj = new JSONObject(data);
            userData.put("username", obj.getString("username"));
            userData.put("password", obj.getString("password"));
        } catch (JSONException e) {
            return null;
        }
        return userData;
    }


}
