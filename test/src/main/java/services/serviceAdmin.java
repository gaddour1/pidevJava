package services;

import entities.user;
import jdk.jfr.Event;
import entities.event;
import utils.MydataBase;
import services.IService;
import utils.MydataBase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.user;

public class serviceAdmin implements IService<user> {

    private static Connection cnx = MydataBase.getInstance().getCnx();

    @Override
    public void ajouter(user p) throws SQLException {
        String query = "INSERT INTO user (nom, username, numero, cin, adresse, email, password, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getUsername());
            ps.setInt(3, p.getNumero());
            ps.setInt(4, p.getCin());
            ps.setString(5, p.getAdresse());
            ps.setString(6, p.getEmail());
            ps.setString(7, p.getPassword());
            ps.setString(8, p.getRole());
            ps.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace(); // Affichez l'erreur pour le débogage
            throw ex; // Lancez à nouveau l'exception pour la gérer au niveau supérieur si nécessaire
        }
    }

    @Override
    public void modifier(user p) throws SQLException {
        String query = "UPDATE user SET nom = ?, username = ?, numero = ?, cin = ?, adresse = ?, email = ?, password = ?, role = ? WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setString(1, p.getNom());
            ps.setString(2, p.getUsername());
            ps.setInt(3, p.getNumero());
            ps.setInt(4, p.getCin());
            ps.setString(5, p.getAdresse());
            ps.setString(6, p.getEmail());
            ps.setString(7, p.getPassword());
            ps.setString(8, p.getRole()); // Ajouter le rôle de l'utilisateur
            ps.setInt(9, p.getId());
            ps.executeUpdate();
        }
    }


    @Override
    public void supprimer(int id) {
        String req = "DELETE FROM user WHERE id=?";
        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("user deleted successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    @Override
    public user getOneById(int id) throws SQLException {
        String query = "SELECT * FROM user WHERE id = ?";
        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String nom = rs.getString("nom");
                    String username = rs.getString("username");
                    int numero = rs.getInt("numero");
                    int cin = rs.getInt("cin");
                    String adresse = rs.getString("adresse");
                    String email = rs.getString("email");
                    String password = rs.getString("password");
                    String role = rs.getString("role");


                    return new user(id, nom, username, numero, cin, adresse, email, password, role);
                }
            }
        }
        return null; // Return null if no user with the specified id is found
    }

    @Override
    public List<user> getAll() throws SQLException {
        List<user> users = new ArrayList<>();
        String query = "SELECT * FROM user"; // Assurez-vous que "utilisateur" est le nom correct de votre table
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String username = rs.getString("username");
                int numero = rs.getInt("numero");
                int cin = rs.getInt("cin");
                String adresse = rs.getString("adresse");
                String email = rs.getString("email");
                String password = rs.getString("password");
                String role = rs.getString("role");

                // Créer un nouvel objet utilisateur avec les données récupérées
                user u = new user(id, nom, username, numero, cin, adresse, email, password, role);
                users.add(u);
            }
        } catch (SQLException e) {
            // Gérer l'exception SQL ici
            throw e;
        }
        return users;
    }


}
