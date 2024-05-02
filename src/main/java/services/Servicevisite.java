package services;

import entities.traitement;
import entities.visite;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servicevisite implements IService<visite>{
    Connection connection;
    public Servicevisite(){

        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(visite visite) throws SQLException {

        String req = "INSERT INTO visite(date, lieu, heure, traitement_id) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(req, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, visite.getDate());
        statement.setString(2, visite.getLieu());
        statement.setString(3, visite.getHeure());
        statement.setInt(4, visite.getTraitement().getId());



        int affectedRows = statement.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Insertion failed, no rows affected.");
        }
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                visite.setId(generatedKeys.getInt(1));
            } else {
                throw new SQLException("Insertion failed, no ID obtained.");
            }
        }

    }


    @Override
    public void modifier(visite visite) throws SQLException {

        String req = "update visite set date=?, lieu=?, heure=?,traitement_id=? where id=?";
        PreparedStatement pre = connection.prepareStatement(req);

        pre.setString(1, visite.getDate());;
        pre.setString(2,visite.getLieu());
        pre.setString(3,visite.getHeure());
        pre.setInt(4,visite.getTraitement().getId());
         pre.setInt(5,visite.getId());


        int affectedRows = pre.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Visite modifiée avec succès");
        } else {
            System.out.println("Aucune visite n'a été modifiée. Vérifiez que l'ID existe.");
        }


    }

    @Override
    public void supprimer(visite visite) throws SQLException {
        String req = "DELETE FROM visite WHERE id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, visite.getId());
        int affectedRows = pre.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("Visite supprimée avec succès");
        } else {
            System.out.println("Aucune visite n'a été supprimée. Vérifiez que l'ID existe.");
        }
    }

    @Override
    public List<visite> afficher() throws SQLException {
        List<visite> visites = new ArrayList<>();
        String req = "SELECT v.*, t.nom, t.duree, t.posologie, t.notes, t.cout FROM visite v JOIN traitement t ON v.traitement_id = t.id";
        try (Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(req)) {

            while (rs.next()) {
                int traitementId = rs.getInt("traitement_id");
                String traitementNom = rs.getString("nom");
                int traitementDuree = rs.getInt("duree");
                String traitementPosologie = rs.getString("posologie");
                String traitementNotes = rs.getString("notes");
                int traitementCout = rs.getInt("cout");

                traitement trait = new traitement(traitementId, traitementNom, traitementDuree, traitementPosologie, traitementNotes, traitementCout);

                visite visite = new visite();
                visite.setId(rs.getInt("id"));
                visite.setDate(rs.getString("date"));
                visite.setLieu(rs.getString("lieu"));
                visite.setHeure(rs.getString("heure"));
                visite.setTraitement(trait);

                visites.add(visite);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des visites : " + e.getMessage());
            throw e; // Rethrow the exception to handle it at a higher level
        }
        return visites;




/*
        List<visite> visites = new ArrayList<>();
        // Simplified SQL to include only necessary fields from the `visite` table and the treatment ID from the `traitement` table
        String sql = "SELECT v.id, v.date, v.lieu, v.heure, v.traitement_id FROM visite v";
        try (Connection conn = this.connection;  // Assuming 'this.connection' is a valid open connection
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Create a new traitement object and set only the ID
                traitement trait = new traitement();
                trait.setId(rs.getInt("traitement_id"));  // Setting the treatment ID

                // Create a new visite object and set its properties including the traitement object
                visite visite = new visite(
                        rs.getInt("id"),
                        rs.getString("date"),
                        rs.getString("lieu"),
                        rs.getString("heure"),
                        trait  // Passing the traitement object that contains only the ID
                );
                visites.add(visite);
            }
        }
        return visites;
    }*/
    }

    public List<PieChart.Data> getMostUsedTreatments() {
        String sql = "SELECT t.nom, COUNT(v.traitement_id) AS count FROM visite v JOIN traitement t ON v.traitement_id = t.id GROUP BY v.traitement_id ORDER BY count DESC LIMIT 10";
        List<PieChart.Data> data = new ArrayList<>();
        try (Statement stmt = this.connection.createStatement();


             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String treatmentName = rs.getString("nom");
                int count = rs.getInt("count");
                data.add(new PieChart.Data(treatmentName, count));
            }
        } catch (SQLException e) {
            System.out.println("Error accessing the database: " + e.getMessage());
        }
        return data;
    }}