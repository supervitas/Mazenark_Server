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
import java.util.concurrent.ExecutionException;

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

    public boolean ClearGuests() {
        CompletableFuture<Boolean> result = new CompletableFuture<>();
        users.deleteMany(eq("isGuest", true), (done, t) -> result.complete(true));
        try {
            return result.get();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
    }

    public void RegisterUser(User user) {
        Document doc = user.ToDocument();

        users.insertOne(doc, (result, t) -> {});

        FindUser(user.getUsername(), user.getPassword());
    }

    public User FindUser(String userName, String password) {
        CompletableFuture<User> result = new CompletableFuture<>();

        // Here goes some function
        SingleResultCallback<Document> callback = (document, t) -> {
            if (document != null) {
                User user = new User();
                user.UpdateFromDocument(document);
                result.complete(user);  // sets value returned by "get()"
            } else {
                result.complete(null);
            }
        };
        // Here some function ends

        if (password != null) {
            //                                                                       ↓ Here some function executes
            users.find(and(eq("username", userName),eq("password", password))).first(callback);
        } else {
            users.find(eq("username", userName)).first(callback);
        }

        try {
            return result.get();    // Returns value from "complete(T value)"
        } catch (Exception e) {
            return null;
        }
    }
}