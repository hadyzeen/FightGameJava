import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        // ✅ Start the actual fighting game GUI
        new GameFrame();

        // ✅ Optional: test database connection
        String url = "jdbc:mysql://localhost:3306/advanced";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Ensure JDBC driver is loaded
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println(" Connected to MySQL successfully!");
            conn.close();
        } catch (ClassNotFoundException e) {
            System.out.println(" MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println(" Database connection failed.");
            e.printStackTrace();
        }
    }
}
