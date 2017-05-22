package Users;

import DB.MongoDriver;
import com.mongodb.async.SingleResultCallback;
import org.bson.Document;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class UserManager {
    private int guestCounter = 0;
    private HashMap<String, User> loggedInUsers = new HashMap<>();
    private MongoDriver mongoDriver;

    public UserManager(MongoDriver mongoDriver){
        this.mongoDriver = mongoDriver;
    }

    public User GetUser(String username, String password) {
        return mongoDriver.FindUser(username, password);
    }

    public User GetLoggedInUser(String token) {
        return loggedInUsers.get(token);
    }

    public User Register(String username, String password) {
        if (GetUser(username, null) != null)
            return null;

        User user = new User(username, password);
        user.setToken(GenerateSessionTokenForUser());
        mongoDriver.RegisterUser(user);

        return user;
    }

    public User NewGuest() {
        User user = new User();
        user.setGuest(true);
        user.setUsername("Guest #" + ++guestCounter);
        user.setPassword("Guests have no passwords.");

        user.setToken(GenerateSessionTokenForUser());
        mongoDriver.RegisterUser(user);
        return user;
    }

    public void LogIn(User user) {
        user.setToken(GenerateSessionTokenForUser());
        loggedInUsers.put(user.getToken(), user);
    }

    public void LogOut(User user) {
        loggedInUsers.remove(user.getToken());
        user.setToken(null);
    }

    public void ClearGuests() {
        guestCounter = 0;
        mongoDriver.ClearGuests();
    }

    private String GenerateSessionTokenForUser() {
        return new BigInteger(128, new Random()).toString(32);
    }
}
