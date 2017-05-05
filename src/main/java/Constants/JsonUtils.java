package Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by nikolaev on 05.05.17.
 */
public class JsonUtils {
    public static String ToJson(String key, String value) {
        JSONObject json = new JSONObject();
        try {
            json.put("port", value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json.toString();
    }
}
