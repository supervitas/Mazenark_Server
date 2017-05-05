package Constants;

import Users.User;
import org.json.JSONObject;

/**
 * Created by nikolaev on 02.05.17.
 */
public class Response {
    public static String OK = JsonUtils.ToJson("status", "OK");
    public static String BAD_JSON = JsonUtils.ToJson("error", "Bad Json");
    public static String NO_USER = JsonUtils.ToJson("error", "No Such User");
    public static String USER_EXISTS = JsonUtils.ToJson("error", "User already exists");
    public static String GUEST_QUOTA_REACHED = JsonUtils.ToJson("error", "Guest names overflowed. Try to register");

    public static String OkPlusUserInfo(User user) {
        String response = OK;
        try {
            JSONObject jsonObject = new JSONObject(OK);
            jsonObject.put("username", user.getUsername());
            jsonObject.put("id", user.getId());
            jsonObject.put("isGuest", user.isGuest());
            response = jsonObject.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }
}
