package DB;

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

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.inc;
import static com.mongodb.client.model.Updates.set;
import static java.util.Arrays.asList;

/**
 * Created by nikolaev on 16.05.17.
 */
public class MongoDriver {
    SingleResultCallback<Document> callbackPrintDocuments = new SingleResultCallback<Document>() {
        @Override
        public void onResult(final Document document, final Throwable t) {
            System.out.println(document.toJson());
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
    public MongoDriver(){
        MongoClient mongoClient = MongoClients.create(new ConnectionString("mongodb://localhost"));
        MongoDatabase database = mongoClient.getDatabase("mazenark");
        MongoCollection<Document> collection = database.getCollection("user");
        //http://mongodb.github.io/mongo-java-driver/3.4/driver-async/getting-started/quick-start/
    }


}
