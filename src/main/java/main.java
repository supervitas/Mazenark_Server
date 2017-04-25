import static spark.Spark.*;

public class main {
    public static void main(String[] args) {
        port(9001);
        get("/hello", (req, res) -> "Hello World");
    }
}