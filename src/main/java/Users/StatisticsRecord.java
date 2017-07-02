package Users;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class StatisticsRecord implements UnityMongoSerializable {
    public String name = "REPORT AS A BUG";
    public Integer value = 0;

    public StatisticsRecord() {

    }

    public StatisticsRecord(String name, Integer value) {
        this.name = name;
        if (value != null) {
            this.value = value;
        }
    }

    @Override
    public UnityMongoSerializable UpdateFromDocument(Document data) {
        name = data.getString("name");
        value = data.getInteger("value");
        return this;
    }

    @Override
    public UnityMongoSerializable UpdateFromJSON(JSONObject data) {
        String currentFieldBeingRead = "";
        String tmpName = "";
        Integer tmpValue = 0;

        boolean thereWereNoErrors = true;
        try {
            currentFieldBeingRead = "name";
            tmpName = data.getString(currentFieldBeingRead);

            currentFieldBeingRead = "value";
            tmpValue = data.getInt(currentFieldBeingRead);

        } catch (JSONException e) {
            thereWereNoErrors = false;
            System.out.println("An error has occurred when updating statistics record \"" + name + "\" from JSON when reading \"" + currentFieldBeingRead + "\" field.");
            System.out.println(data.toString());
            e.printStackTrace();
        }

        if (thereWereNoErrors) {
            name = tmpName;
            value = tmpValue;
        }

        return this;
    }

    @Override
    public Document ToDocument() {
        Document result = new Document();

        result.append("name", name);
        result.append("value", value);

        return result;
    }

    @Override
    public JSONObject ToJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("name", name);
            result.put("value", value);

        } catch (JSONException e) {
            System.out.println("An error has occurred when converting statistics record \"" + name + "\" to JSON! O_o");
            e.printStackTrace();
        }

        return result;
    }
}
