package Users;

import com.sun.istack.internal.Nullable;

import java.util.concurrent.atomic.AtomicInteger;

public class User {
    private static AtomicInteger idCounter = new AtomicInteger();

    private int id;
    private String username;
    private String password;
    private boolean isGuest;
    private boolean isLoggedIn;
    @Nullable
    private String token = null;

    public User(String username, String password, boolean isGuest) {
        this.id = idCounter.getAndIncrement();
        this.username = username;
        this.password = password;
        this.isGuest = isGuest;
        this.isLoggedIn = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isGuest() {
        return isGuest;
    }

    public void setGuest(boolean guest) {
        isGuest = guest;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // Auto-generated by IntelliJ
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (isGuest != user.isGuest) return false;
        if (!username.equals(user.username)) return false;
        return password.equals(user.password);

    }

    // Auto-generated by IntelliJ
    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + username.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + (isGuest ? 1 : 0);
        return result;
    }
}
