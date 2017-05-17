package DB;

import Users.User;
import com.mongodb.Block;
import com.mongodb.ServerAddress;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.mongodb.connection.ClusterSettings;
import com.mongodb.ConnectionString;
import org.bson.Document;
import java.util.concurrent.CompletableFuture;
import static com.mongodb.client.model.Filters.*;

/**
 * Created by nikolaev on 16.05.17.
 */

//http://mongodb.github.io/mongo-java-driver/3.4/driver-async/getting-started/quick-start/
public class MongoDriver {
    private MongoDatabase db;
    MongoCollection<Document> users;

    public MongoDriver() {
        MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost"));
        db = mongoClient.getDatabase("mazenark"); // db name
        users = db.getCollection("users"); // collection name
    }

    public void RegisterUser(User user) {
        Document doc = new Document();

        doc.append("username", user.getUsername());
        doc.append("score", user.getScore());
        doc.append("password", user.getPassword());
        doc.append("isGuest", user.isGuest());
        doc.append("token", user.getToken());

        users.insertOne(doc, (result, t) -> {});
    }

    public User FindUser(String userName, String password) {
        CompletableFuture<User> result = new CompletableFuture<>();

        SingleResultCallback<Document> callback = (document, t) -> {
            if (document != null) {
                User user = new User();
                user.setUsername(document.getString("username"));
                user.setToken(document.getString("token"));
                user.setScore(document.getInteger("score"));
                result.complete(user);
            } else {
                result.complete(null);
            }
        };
        if (password != null) {
            users.find(and(eq("username", userName),eq("password", password))).first(callback);
        } else {
            users.find(eq("username", userName)).first(callback);
        }

        try {
            return result.get();
        } catch (Exception e) {
            return null;
        }
    }
}