package Users;

import com.sun.istack.internal.Nullable;

import java.util.HashSet;

public class UserManager {
    private HashSet<User> databaseConnector = new HashSet<>();
    private HashSet<User> loggedInUsers = new HashSet<>();

    @Nullable
    public User GetUser(String username, String password) {
        for (User user : databaseConnector) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }

    @Nullable
    public User GetUser(String username) {
        for (User user : databaseConnector) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    @Nullable
    public User Register(String username, String password) {
        if (GetUser(username) != null)
            return null;

        User user = new User(username, password, false);
        databaseConnector.add(user);

        return user;
    }

    @Nullable
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
        loggedInUsers.add(user);
        user.setLoggedIn(true);
    }

    public void LogOut(User user) {
        user.setLoggedIn(false);
        loggedInUsers.remove(user);
    }

    public boolean IsLoggedIn(User user) {
        return loggedInUsers.contains(user);
    }
}
