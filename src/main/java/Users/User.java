package Users;

import org.bson.Document;
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

        return this;
    }

    @Override
    public UnityMongoSerializable UpdateFromJSON(JSONObject data) {
        return null;
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

        return result;
    }

    @Override
    public JSONObject ToJSON() {
        return null;
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
