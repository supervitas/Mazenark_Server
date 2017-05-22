package Users;

import org.bson.Document;
import org.json.JSONObject;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;


public interface UnityMongoSerializable {
    /*static*/ UnityMongoSerializable UpdateFromDocument(Document data);
    /*static*/ UnityMongoSerializable UpdateFromJSON(JSONObject data);

    Document ToDocument();
    JSONObject ToJSON();
}
