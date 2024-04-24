package services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class serviceParticipation {

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

    public void saveParticipation(int userId, int eventId) throws SQLException {
        String sql = "INSERT INTO participation(id_user, id_event) VALUES(?,?)";

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