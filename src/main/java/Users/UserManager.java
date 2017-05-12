package Users;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class UserManager {
    private HashSet<User> databaseConnector = new HashSet<>();
    private HashMap<String, User> loggedInUsers = new HashMap<>();

    public User GetUser(String username, String password) {
        for (User user : databaseConnector) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    public User GetUser(String username) {
        for (User user : databaseConnector) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public User GetUser(int id) {
        for (User user : databaseConnector) {
            if (user.getId() == id)
                return user;
        }
        return null;
    }

    public User GetLoggedInUser(String token) {
        return loggedInUsers.get(token);
    }

    public User Register(String username, String password) {
        if (GetUser(username) != null)
            return null;

        User user = new User(username, password, false);
        databaseConnector.add(user);

        return user;
    }

    public User NewGuest() {
        User user = new User("tmp~I hope no one will register this username", "NONE", true);

        // Just in case someone registers with "Guest #999" username.
        for (int i = user.getId(); i < user.getId() + 10e+3; i++) {
            User existingUser = GetUser("Guest #" + i);
            if (existingUser == null) {
                user.setUsername("Guest #" + i);
                break;
            }
        }

        User existingUser = GetUser(user.getUsername());
        if (existingUser != null)
            return null;

        databaseConnector.add(user);

        return user;
    }

    public void LogIn(User user) {
        GenerateSessionTokenForUser(user);
        loggedInUsers.put(user.getToken(), user);
        user.setLoggedIn(true);
    }

    public void LogOut(User user) {
        user.setLoggedIn(false);
        loggedInUsers.remove(user.getToken());
        user.setToken(null);
    }

    public boolean IsLoggedIn(User user) {
        return loggedInUsers.containsKey(user.getToken());
    }

    private String GenerateSessionTokenForUser(User user) {
        String token = new BigInteger(128, new Random()).toString(32);
        user.setToken(token);
        return token;
    }
}
