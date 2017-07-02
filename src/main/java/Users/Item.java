package Users;

import org.bson.Document;
import org.json.JSONException;
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
        String currentFieldBeingRead = "";
        String tmpID = "";
        Integer tmpChargesLeft = 0;
        Integer tmpChargesTotal = 0;

        boolean thereWereNoErrors = true;
        try {
            currentFieldBeingRead = "id";
            tmpID = data.getString(currentFieldBeingRead);

            currentFieldBeingRead = "chargesLeft";
            tmpChargesLeft = data.getInt(currentFieldBeingRead);

            currentFieldBeingRead = "chargesTotal";
            tmpChargesTotal = data.getInt(currentFieldBeingRead);

        } catch (JSONException e) {
            thereWereNoErrors = false;
            System.out.println("An error has occurred when updating statistics record " + id + " from JSON when reading " + currentFieldBeingRead + " field.");
            System.out.println(data.toString());
            e.printStackTrace();
        }

        if (thereWereNoErrors) {
            id = tmpID;
            chargesLeft = tmpChargesLeft;
            chargesTotal = tmpChargesTotal;
        }

        return this;
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
        JSONObject result = new JSONObject();
        try {
            result.put("id", id);
            result.put("chargesLeft", chargesLeft);
            result.put("chargesTotal", chargesTotal);

        } catch (JSONException e) {
            System.out.println("An error has occurred when converting statistics record " + id + " to JSON! O_o");
            e.printStackTrace();
        }

        return result;
    }
}
