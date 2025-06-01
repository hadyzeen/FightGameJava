import java.sql.*;

public class DatabaseManager {

    public static void updateStats(String winnerName, String loserName) {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/advanced", "root", "")) {

            // Ensure both players exist in the table
            try (PreparedStatement insertStmt = conn.prepareStatement("INSERT IGNORE INTO player (name) VALUES (?)")) {
                insertStmt.setString(1, winnerName);
                insertStmt.executeUpdate();
                insertStmt.setString(1, loserName);
                insertStmt.executeUpdate();
            }

            // Update winner's stats
            try (PreparedStatement winStmt = conn.prepareStatement(
                    "UPDATE player SET total_wins = total_wins + 1 WHERE name = ?")) {
                winStmt.setString(1, winnerName);
                winStmt.executeUpdate();
            }

            // Update loser's stats
            try (PreparedStatement lossStmt = conn.prepareStatement(
                    "UPDATE player SET total_losses = total_losses + 1 WHERE name = ?")) {
                lossStmt.setString(1, loserName);
                lossStmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

