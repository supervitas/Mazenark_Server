package Users;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class User implements UnityMongoSerializable {
    private String username;
    private String password;
    private boolean isGuest;
    private String token = null;
    private ArrayList<StatisticsRecord> statistics = new ArrayList<>();
    private ArrayList<Item> itemsInInventory = new ArrayList<>();
    private ArrayList<Item> itemsInStorage = new ArrayList<>();
    private long timeWhenDailiesGenerated = 0;
    private ArrayList<Daily> dailies = new ArrayList<>();

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
        timeWhenDailiesGenerated = data.getLong("timeWhenDailiesGenerated");

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

        UpdateDailies();

        List<Document> documentedDailies = (List<Document>) data.get("dailies", ArrayList.class);
        for (Document rawRecord : documentedDailies) {
            Daily daily = new Daily();
            daily.UpdateFromDocument(rawRecord);
            dailies.add(daily);
        }

        return this;
    }

    @Override
    public UnityMongoSerializable UpdateFromJSON(JSONObject data) {
        String currentFieldBeingRead = "";
        String tmpUsername = "";
        String tmpPassword = "";
        boolean tmpIsGuest = false;
        long tmpLastTimeLoggedIn = 0;
        ArrayList<StatisticsRecord> tmpStatistics = new ArrayList<>();
        ArrayList<Item> tmpItemsInInventory = new ArrayList<>();
        ArrayList<Item> tmpItemsInStorage = new ArrayList<>();
        ArrayList<Daily> tmpDailies = new ArrayList<>();

        boolean thereWereNoErrors = true;
        try {
            currentFieldBeingRead = "username";
            tmpUsername = data.getString(currentFieldBeingRead);

            currentFieldBeingRead = "password";
            tmpPassword = data.getString(currentFieldBeingRead);

            currentFieldBeingRead = "isGuest";
            tmpIsGuest = data.getBoolean(currentFieldBeingRead);

            currentFieldBeingRead = "timeWhenDailiesGenerated";
            tmpLastTimeLoggedIn = data.optLong(currentFieldBeingRead, 0);

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

            UpdateDailies();

            currentFieldBeingRead = "dailies";
            JSONArray jsonedDailies = data.getJSONArray(currentFieldBeingRead);
            for (int i = 0; i < jsonedItemsInStorage.length(); i++) {
                Daily daily = new Daily();
                daily.UpdateFromJSON(jsonedDailies.getJSONObject(i));
                tmpDailies.add(daily);
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
            timeWhenDailiesGenerated = tmpLastTimeLoggedIn;
            statistics = tmpStatistics;
            itemsInInventory = tmpItemsInInventory;
            itemsInStorage = tmpItemsInStorage;
            dailies = tmpDailies;
        }

        return this;
    }

    @Override
    public Document ToDocument() {
        Document result = new Document();

        result.append("username", username);
        result.append("password", password);
        result.append("isGuest", isGuest);
        result.append("timeWhenDailiesGenerated", timeWhenDailiesGenerated);

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

        List<Document> documentedDailies = new ArrayList<>();
        for (Daily daily : dailies) {
            documentedDailies.add(daily.ToDocument());
        }
        result.append("dailies", documentedDailies);

        return result;
    }

    @Override
    public JSONObject ToJSON() {
        JSONObject result = new JSONObject();
        try {
            result.put("username", username);
            //result.put("password", password); // better not to send this field to everyone. :)
            result.put("isGuest", isGuest);
            result.put("timeWhenDailiesGenerated", timeWhenDailiesGenerated);

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

            JSONArray jsonedDailies = new JSONArray();
            for (Daily daily : dailies) {
                jsonedDailies.put(daily.ToJSON());
            }
            result.put("dailies", jsonedDailies);
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

    public void GenerateDailiesIfNeeded() {
        long eighteenHours = 18 * 1000 * 60 * 60;    // 18 * msec * sec * min
        long now = System.currentTimeMillis();
        if (now > timeWhenDailiesGenerated + eighteenHours) {
            timeWhenDailiesGenerated = now;
            dailies = DailyGenerator.GetRandomDailies(3, this);
        }
    }

    public void UpdateDailies() {
        for (Daily daily : dailies) {
            daily.TryAccompishDaily(this);
        }
    }
}
