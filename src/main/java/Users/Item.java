package Users;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

public class Item implements UnityMongoSerializable {
    public String id = "REPORT AS A BUG";
    public Integer chargesLeft = 0;

    public Item() {

    }

    public Item(String id) {
        this.id = id;
    }

    @Override
    public UnityMongoSerializable UpdateFromDocument(Document data) {
        id = data.getString("id");
        chargesLeft = data.getInteger("chargesLeft");
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



        } catch (JSONException e) {
            thereWereNoErrors = false;
            System.out.println("An error has occurred when updating item \"" + id + "\" from JSON when reading \"" + currentFieldBeingRead + "\" field.");
            System.out.println(data.toString());
            e.printStackTrace();
        }

        if (thereWereNoErrors) {
            id = tmpID;
            chargesLeft = tmpChargesLeft;
        }

        return this;
    }

    @Override
    public Document ToDocument() {
        Document result = new Document();

        result.append("id", id);
        result.append("chargesLeft", chargesLeft);

        return result;
    }

    @Override
    public JSONObject ToJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("id", id);
            result.put("chargesLeft", chargesLeft);

        } catch (JSONException e) {
            System.out.println("An error has occurred when converting item \"" + id + "\" to JSON! O_o");
            e.printStackTrace();
        }

        return result;
    }
}
