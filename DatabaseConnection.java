import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3307/projet_java";
    private static final String USERNAME = "root";// both username and password are removed for security mesures!
    private static final String PASSWORD = "hiba1009*";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
            throw e;
        }
    }
}