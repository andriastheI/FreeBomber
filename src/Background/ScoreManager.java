package Background;

import java.sql.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class ScoreManager {
    // Update this if your user or DB is different
    private static final String URL = "jdbc:mysql://localhost:3306/freebomber";
    private static final String USER = "root";
    private static final String PASSWORD = "Albertocan1090"; // Replace with your real password

    public static void saveScore(String username, int score) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO scores (username, score) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.setInt(2, score);
            stmt.executeUpdate();
            System.out.println("✅ Score saved for " + username + ": " + score);
        } catch (SQLException e) {
            System.err.println("❌ Error saving score: " + e.getMessage());
        }
    }

    public static Map<String, Integer> getTopScores(int limit) {
        Map<String, Integer> scores = new LinkedHashMap<>();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "SELECT username, MAX(score) as score FROM scores GROUP BY username ORDER BY score DESC LIMIT ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, limit);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                scores.put(rs.getString("username"), rs.getInt("score"));
            }

        } catch (SQLException e) {
            System.err.println("❌ Error loading top scores: " + e.getMessage());
        }

        return scores;
    }

}