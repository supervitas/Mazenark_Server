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

    private String[] loginAndPass = {"username", "password"};

    public AuthController(UserManager userManager) {
        this.userManager = userManager;
    }

    public String LogIn(Request req, Response res) {
        HashMap<String, String> userData = ParseUserData(req.body(), loginAndPass);
        // Check if all fields in request are present
        if (userData == null || !userData.containsKey("username") || !userData.containsKey("password")) {
            res.status(400);    // If something is wrong => 400 Bad Request
            return BAD_JSON;
        }

        // Get user with these credentials from DB
        User user = userManager.GetUser(userData.get("username"), userData.get("password"));
        if (user == null) {
            res.status(400);    // If no such user => 401 Unauthorized
            return NO_USER;
        }

        userManager.LogIn(user);

        return OkPlusSessionToken(user);
    }

    public String Register(Request req, Response res) {
        HashMap<String, String> userData = ParseUserData(req.body(), loginAndPass);
        // Check if all fields in request are present
        if (userData == null || !userData.containsKey("username") || !userData.containsKey("password")) {
            res.status(400);    // If something is wrong => 400 Bad Request
            return BAD_JSON;
        }

        // Get user with these credentials from DB
        User user = userManager.Register(userData.get("username"), userData.get("password"));
        if (user == null) {
            res.status(400);    // If no such user => 401 Unauthorized
            return USER_EXISTS;
        }
        userManager.LogIn(user);

        return OkPlusUserInfo(user);
    }

    public String GetUserByName(Request req, Response res) {
        String name;
        try {
            JSONObject obj = new JSONObject(req.body());
            name = obj.getString("username");
        } catch (JSONException e) {
            res.status(400);    // If something is wrong => 400 Bad Request
            return BAD_JSON;
        }

        // Get user with these credentials from DB
        User user = userManager.GetUser(name, null);
        if (user == null) {
            res.status(400);    // If no such user => 401 Unauthorized
            return NO_USER;
        }
        user.setToken(null); // dont provide credentials

        return OkPlusUserInfo(user);
    }

    public String GetUserByToken(Request req, Response res) {
            String token = null;
        try {
            JSONObject obj = new JSONObject(req.body());
            token = obj.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (token == null) {
            res.status(400);    // If something is wrong => 400 Bad Request
            return BAD_JSON;
        }

        User user = userManager.GetLoggedInUser(token);
        if (user == null) {
            res.status(400);    // If no such user => 401 Unauthorized
            return NO_USER;
        }

        return OkPlusUserInfo(user);
    }

    public String LogOut(Request req, Response res) {
        HashMap<String, String> userData = ParseUserData(req.body(), new String[] {"token"});

        if (userData == null || !userData.containsKey("token")) {
            res.status(400);    // If something is wrong => 400 Bad Request
            return BAD_JSON;
        }

        User user = userManager.GetLoggedInUser(userData.get("token"));
        if (user == null) {
            res.status(400);    // If no such user => 401 Unauthorized
            return NO_USER;
        }

        userManager.LogOut(user);

        return OK;
    }

    public String BecomeGuest(Request req, Response res) {
        User user = userManager.NewGuest();
        userManager.LogIn(user);
        if (user == null) {
            res.status(400);    // Very unlikely. `user == null` may happen only if ~10000 users with name "Guest #666" exist.
            return GUEST_QUOTA_REACHED;
        }

        return OkPlusUserInfo(user);
    }

    public String GetUserData(Request req, Response res) {
        String name;
        try {
            JSONObject obj = new JSONObject(req.body());
            name = obj.getString("username");
        } catch (JSONException e) {
            res.status(400);    // If something is wrong => 400 Bad Request
            return BAD_JSON;
        }

        // Get user with these credentials from DB
        User user = userManager.GetUser(name, null);
        if (user == null) {
            res.status(400);    // If no such user => 401 Unauthorized
            return NO_USER;
        }

        return user.ToJSON().toString();
    }

    public String UpdateUserData(Request req, Response res) {
        String name;
        try {
            JSONObject obj = new JSONObject(req.body());
            name = obj.getString("username");
        } catch (JSONException e) {
            res.status(400);    // If something is wrong => 400 Bad Request
            return BAD_JSON;
        }

        // Get user with these credentials from DB
        User user = userManager.GetUser(name, null);
        if (user == null) {
            res.status(400);    // If no such user => 401 Unauthorized
            return NO_USER;
        }

        try {
            JSONObject obj = new JSONObject(req.body());
            user.UpdateFromJSON(obj);
            userManager.UpdateUser(user);
        } catch (JSONException e) {
            res.status(400);    // If something is wrong => 400 Bad Request
            return BAD_JSON;
        }

        return OK;
    }

    private HashMap<String, String> ParseUserData(String data, String [] fields) {
        HashMap<String, String> userData = new HashMap<>();
        try {
            JSONObject obj = new JSONObject(data);
            for (String field : fields) {
                userData.put(field, obj.getString(field));
            }

        } catch (JSONException e) {
            return null;
        }
        return userData;
    }

}
