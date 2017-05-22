package Users;

import org.bson.Document;
import org.json.JSONObject;

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
        return null;
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
        return null;
    }
}
