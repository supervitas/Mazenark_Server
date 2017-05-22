package Users;

import org.bson.Document;
import org.json.JSONObject;

public class Item implements UnityMongoSerializable {
    public String id = "REPORT AS A BUG";
    public Integer chargesLeft = 0;
    public Integer chargesTotal = 0;

    public Item() {

    }

    public Item(String id) {
        this.id = id;
    }

    @Override
    public UnityMongoSerializable UpdateFromDocument(Document data) {
        id = data.getString("id");
        chargesLeft = data.getInteger("chargesLeft");
        chargesTotal = data.getInteger("chargesTotal");
        return this;
    }

    @Override
    public UnityMongoSerializable UpdateFromJSON(JSONObject data) {
        return null;
    }

    @Override
    public Document ToDocument() {
        Document result = new Document();

        result.append("id", id);
        result.append("chargesLeft", chargesLeft);
        result.append("chargesTotal", chargesTotal);

        return result;
    }

    @Override
    public JSONObject ToJSON() {
        return null;
    }
}
