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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.set;
import static java.util.Arrays.asList;

/**
 * Created by nikolaev on 16.05.17.
 */

//http://mongodb.github.io/mongo-java-driver/3.4/driver-async/getting-started/quick-start/
public class MongoDriver {
    private MongoDatabase db;
    MongoCollection<Document> users;

    SingleResultCallback<Document> callbackPrintDocuments = new SingleResultCallback<Document>() {
        @Override
        public void onResult(final Document document, final Throwable t) {
            if(document != null) {
                System.out.println(document.toJson());
            }
        }
    };

    SingleResultCallback<Void> callbackWhenFinished = new SingleResultCallback<Void>() {
        @Override
        public void onResult(final Void result, final Throwable t) {
            System.out.println("Operation Finished!");
        }
    };

    Block<Document> printDocumentBlock = new Block<Document>() {
        @Override
        public void apply(final Document document) {
            System.out.println(document.toJson());
        }
    };

    public MongoDriver() {
        MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost"));
        db = mongoClient.getDatabase("mazenark");
        users = db.getCollection("users");
    }

    public void RegisterUser(User user) {
        Document doc = new Document();
        doc.append("username", user.getUsername());
        doc.append("password", user.getPassword());
        doc.append("isGuest", user.isGuest());
        doc.append("token", user.getToken());
        users.insertOne(doc, (result, t) -> {});
    }

    public String FindUser(String user) {
        CompletableFuture<String> result = new CompletableFuture<>();

        SingleResultCallback<Document> callback = (document, t) -> {
            if(document != null) {
                result.complete(document.toJson());
            } else {
                result.complete(null);
            }
        };

        users.find(eq("username", user)).first(callback);

        try {
            return result.get();
        } catch (Exception e) {
            return null;
        }
    }
}