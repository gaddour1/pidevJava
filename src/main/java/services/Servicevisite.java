package services;

import entities.traitement;
import entities.visite;
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
/*
        String req = "INSERT INTO visite(date, lieu, heure, traitement_id)"+" VALUES('" + visite.getDate()+ "', '" + visite.getLieu() + "', '" + visite.getHeure() + "', " + visite.getTraitement().getId() + ")";
        Statement statement= connection.createStatement();
        statement.executeUpdate(req);
            System.out.println("Visite ajoutée avec succès");

*/
        String req = "INSERT INTO visite(date, lieu, heure, traitement_id) VALUES (?, ?, ?, ?)";

        // Using PreparedStatement to prevent SQL injection and ensure proper data types
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

        System.out.println("Visite ajoutée avec succès");
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
    public void supprimer(int id) throws SQLException {
        String req = "delete from visite where id="+id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);


    }

    @Override
    public List<visite> afficher() throws SQLException {
      /*  List<visite> visites = new ArrayList<>();
        String req = "select * from visite";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {
            traitement trait = new traitement("amna", 20, "aaaaaa", "amnaaa", 12);

            visite visite = new visite(145, "2015-5-15", "aaaaaa", "15:17",trait  );

            visite.setDate(rs.getString("date"));
            visite.setLieu(rs.getString("lieu"));
            visite.setHeure(rs.getString("heure"));
            visite.setTraitement(rs.getString("traitement_id"));
            visites.add(visite);
        }

        return visites;

       */



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
    }}