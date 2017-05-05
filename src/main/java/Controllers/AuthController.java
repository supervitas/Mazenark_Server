package Controllers;

import Users.User;
import Users.UserManager;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;

import static Constants.Response.*;

public class AuthController {
    private UserManager userManager;

    public AuthController(UserManager userManager) {
        this.userManager = userManager;
    }

    public String LogIn(Request req, Response res) {
        String username;
        String password;
        try {
            JSONObject obj = new JSONObject(req.body());
            username = obj.getString("username");
            password = obj.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
            res.status(400);
            return BAD_JSON;
        }

        User user = userManager.GetUser(username, password);
        if (user == null) {
            res.status(401);
            return NO_USER;
        }

        userManager.LogIn(user);

        return OkPlusUserInfo(user);
    }

    public String Register(Request req, Response res) {
        String username;
        String password;
        try {
            JSONObject obj = new JSONObject(req.body());
            username = obj.getString("username");
            password = obj.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
            res.status(400);
            return BAD_JSON;
        }

        User user = userManager.Register(username, password);
        if (user == null) {
            res.status(400);
            return USER_EXISTS;
        }

        userManager.LogIn(user);

        return OkPlusUserInfo(user);
    }

    public String LogOut(Request req, Response res) {
        String username;
        String password;
        try {
            JSONObject obj = new JSONObject(req.body());
            username = obj.getString("username");
            password = obj.getString("password");
        } catch (JSONException e) {
            e.printStackTrace();
            res.status(400);
            return BAD_JSON;
        }

        User user = userManager.GetUser(username, password);
        if (user == null) {
            res.status(401);
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

}
