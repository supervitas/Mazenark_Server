package Users;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class User implements UnityMongoSerializable {
    private String username;
    private String password;
    private boolean isGuest;
    private String token = null;
    private ArrayList<StatisticsRecord> statistics = new ArrayList<>();
    private ArrayList<Item> itemsInInventory = new ArrayList<>();
    private ArrayList<Item> itemsInStorage = new ArrayList<>();

    User(String username, String password) {
        this.username = username;
        this.password = password;
        // Attention! Do not initialize statistics array with elements! Otherwise there bound to be duplicates when deserializing using UpdateFromDocument or UpdateFromJSON methods. It is better to let Unity create and update those.
        // statistics.add(new StatisticsRecord("score", 0));
    }
    public User() {
        // statistics.add(new StatisticsRecord("score", 0));
    }

    public int getStatisticsRecord(String name) {
        for (StatisticsRecord record : statistics) {
            if (record.name.equals(name)) {
                return record.value;
            }
        }

        return 0; // Or shall there be created new record and then added to ArrayList?
    }
    public void setStatisticsRecord(String name, int value) {
        for (StatisticsRecord record : statistics) {
            if (record.name.equals(name)) {
                record.value = value;
                return;
            }
        }

        StatisticsRecord record = new StatisticsRecord(name, value);
        statistics.add(record);
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public boolean isGuest() {
        return isGuest;
    }
    public void setGuest(boolean guest) {isGuest = guest;}

    @Override
    public UnityMongoSerializable UpdateFromDocument(Document data) {
        username = data.getString("username");
        password = data.getString("password");
        isGuest = data.getBoolean("isGuest");

        List<Document> documentedStatistics = (List<Document>) data.get("statistics", ArrayList.class);
        for (Document rawRecord : documentedStatistics) {
            StatisticsRecord record = new StatisticsRecord();
            record.UpdateFromDocument(rawRecord);
            statistics.add(record);
        }

        List<Document> documentedItemsInInventory = (List<Document>) data.get("itemsInInventory", ArrayList.class);
        for (Document rawRecord : documentedItemsInInventory) {
            Item item = new Item();
            item.UpdateFromDocument(rawRecord);
            itemsInInventory.add(item);
        }

        List<Document> documentedItemsInStorage = (List<Document>) data.get("itemsInStorage", ArrayList.class);
        for (Document rawRecord : documentedItemsInStorage) {
            Item item = new Item();
            item.UpdateFromDocument(rawRecord);
            itemsInStorage.add(item);
        }

        return this;
    }

    @Override
    public UnityMongoSerializable UpdateFromJSON(JSONObject data) {
        String currentFieldBeingRead = "";
        String tmpUsername = "";
        String tmpPassword = "";
        boolean tmpIsGuest = false;
        ArrayList<StatisticsRecord> tmpStatistics = new ArrayList<>();
        ArrayList<Item> tmpItemsInInventory = new ArrayList<>();
        ArrayList<Item> tmpItemsInStorage = new ArrayList<>();

        boolean thereWereNoErrors = true;
        try {
            currentFieldBeingRead = "username";
            tmpUsername = data.getString(currentFieldBeingRead);

            currentFieldBeingRead = "password";
            tmpPassword = data.getString(currentFieldBeingRead);

            currentFieldBeingRead = "isGuest";
            tmpIsGuest = data.getBoolean(currentFieldBeingRead);

            currentFieldBeingRead = "statistics";
            JSONArray jsonedStatistics = data.getJSONArray(currentFieldBeingRead);
            for (int i = 0; i < jsonedStatistics.length(); i++) {
                StatisticsRecord record = new StatisticsRecord();
                record.UpdateFromJSON(jsonedStatistics.getJSONObject(i));
                tmpStatistics.add(record);
            }

            currentFieldBeingRead = "itemsInInventory";
            JSONArray jsonedItemsInInventory = data.getJSONArray(currentFieldBeingRead);
            for (int i = 0; i < jsonedItemsInInventory.length(); i++) {
                Item item = new Item();
                item.UpdateFromJSON(jsonedItemsInInventory.getJSONObject(i));
                tmpItemsInInventory.add(item);
            }

            currentFieldBeingRead = "itemsInStorage";
            JSONArray jsonedItemsInStorage = data.getJSONArray(currentFieldBeingRead);
            for (int i = 0; i < jsonedItemsInStorage.length(); i++) {
                Item item = new Item();
                item.UpdateFromJSON(jsonedItemsInStorage.getJSONObject(i));
                tmpItemsInStorage.add(item);
            }

        } catch (JSONException e) {
            thereWereNoErrors = false;
            System.out.println("An error has occurred when updating user " + username + " from JSON when reading " + currentFieldBeingRead + " field.");
            System.out.println(data.toString());
            e.printStackTrace();
        }

        if (thereWereNoErrors) {
            // Apply credentials changes only if they are not null.
            if (!tmpUsername.equals(""))
                username = tmpUsername;
            if (!tmpPassword.equals(""))
                password = tmpPassword;

            isGuest = tmpIsGuest;
            statistics = tmpStatistics;
            itemsInInventory = tmpItemsInInventory;
            itemsInStorage = tmpItemsInStorage;
        }

        return this;
    }

    @Override
    public Document ToDocument() {
        Document result = new Document();

        result.append("username", username);
        result.append("password", password);
        result.append("isGuest", isGuest);

        List<Document> documentedStatistics = new ArrayList<>();
        for (StatisticsRecord record : statistics) {
            documentedStatistics.add(record.ToDocument());
        }
        result.append("statistics", documentedStatistics);

        List<Document> documentedItemsInInventory = new ArrayList<>();
        for (Item item : itemsInInventory) {
            documentedItemsInInventory.add(item.ToDocument());
        }
        result.append("itemsInInventory", documentedItemsInInventory);

        List<Document> documentedItemsInStorage = new ArrayList<>();
        for (Item item : itemsInStorage) {
            documentedItemsInStorage.add(item.ToDocument());
        }
        result.append("itemsInStorage", documentedItemsInStorage);

        return result;
    }

    @Override
    public JSONObject ToJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("username", username);
            //result.put("password", password); // better not to send this field to everyone. :)
            result.put("isGuest", isGuest);

            JSONArray jsonedStatistics = new JSONArray();
            for (StatisticsRecord record : statistics) {
                jsonedStatistics.put(record.ToJSON());
            }
            result.put("statistics", jsonedStatistics);

            JSONArray jsonedItemsInInventory = new JSONArray();
            for (Item item : itemsInInventory) {
                jsonedItemsInInventory.put(item.ToJSON());
            }
            result.put("itemsInInventory", jsonedItemsInInventory);

            JSONArray jsonedItemsInStorage = new JSONArray();
            for (Item item : itemsInStorage) {
                jsonedItemsInStorage.put(item.ToJSON());
            }
            result.put("itemsInStorage", jsonedItemsInStorage);
        } catch (JSONException e) {
            System.out.println("An error has occurred when converting user " + username + " to JSON! O_o");
            e.printStackTrace();
        }

        return result;
    }

    // Auto-generated by IntelliJ
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return isGuest == user.isGuest && username.equals(user.username) && password.equals(user.password);

    }
}
