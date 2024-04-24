package services;

import java.sql.*;
import java.time.LocalDate;

public class serviceFavoris {

    private Connection connect() {
        // Replace with your database connection details
        String url = "jdbc:mysql://localhost:3306/amine";
        String user = "root";
        String password = "";

        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public void saveToFavoris(int userId, int eventId, LocalDate date) throws SQLException {
        String sql = "INSERT INTO favoris(id_user, id_event, date) VALUES(?,?,?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, eventId); // Assuming you also have an event ID to insert
            pstmt.setString(3, date.toString()); // Convert LocalDate to String
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e; // Rethrow the exception for handling in the calling code
        }
    }
    public boolean isFavorite(int userId, int eventId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM favoris WHERE id_user = ? AND id_event = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, eventId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
        return false;
    }

    public void removeFromFavorites(int userId, int eventId) throws SQLException {
        String sql = "DELETE FROM favoris WHERE id_user = ? AND id_event = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, eventId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }
}
