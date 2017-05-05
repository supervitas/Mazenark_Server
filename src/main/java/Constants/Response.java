package Constants;

import Users.User;
import org.json.JSONObject;

/**
 * Created by nikolaev on 02.05.17.
 */
public class Response {
    public static String OK = "{\"status\":\"OK\"}";
    public static String BAD_JSON = "{\"status\":\"ERROR\";\"reason\":\"Bad JSON\"}";
    public static String NO_USER = "{\"status\":\"ERROR\";\"reason\":\"No such user!\"}";
    public static String USER_EXISTS = "{\"status\":\"ERROR\";\"reason\":\"User with same name already exists!\"}";
    public static String GUEST_QUOTA_REACHED = "{\"status\":\"ERROR\";\"reason\":\"Too many guests on server. Try registering.\"}";

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
