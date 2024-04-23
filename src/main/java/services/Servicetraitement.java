package services;

import entities.traitement;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Servicetraitement implements IService<traitement> {
    Connection connection;

    public Servicetraitement() {

        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(traitement traitement) throws SQLException {
        String req = "insert into traitement(nom,duree,posologie,notes,cout)" + "values('" + traitement.getNom() + "'," + traitement.getDuree() + ",'" + traitement.getPosologie() + "','" + traitement.getNotes() + "'," + traitement.getCout() + ")";

        Statement statement = connection.createStatement();
        statement.executeUpdate(req);
        System.out.println("  traitement ajoutée avec succés ");

    }

    @Override
    public void modifier(traitement traitement) throws SQLException {
        String req = "update traitement set nom=?, duree=?, posologie=?,notes=?,cout=? where id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, traitement.getNom());
        pre.setInt(2, traitement.getDuree());
        pre.setString(3, traitement.getPosologie());
        pre.setString(4, traitement.getNotes());
        pre.setInt(5, traitement.getCout());
        pre.setInt(6, traitement.getId());

        pre.executeUpdate();
        System.out.println(" traitement modifié avec succés");


    }

    @Override
    public void supprimer(int id) throws SQLException {
        String req = "delete from traitement where id=" + id;
        Statement statement = connection.createStatement();
        statement.executeUpdate(req);


    }

    @Override
    public List<traitement> afficher() throws SQLException {
        List<traitement> traits = new ArrayList<>();
        String req = "select * from traitement";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {
            traitement trait = new traitement("amna", 20, "aaaaaa", "amnaaa", 12);

            trait.setNom(rs.getString("nom"));
            trait.setDuree(rs.getInt("duree"));
            trait.setPosologie(rs.getString("posologie"));
            trait.setNotes(rs.getString("notes"));
            trait.setCout(rs.getInt("cout"));
            traits.add(trait);

        }

        return traits;
    }
    public boolean exists(traitement newTraitement) throws SQLException {
        String query = "SELECT COUNT(*) FROM traitement WHERE nom = ? AND posologie = ? AND notes = ? AND duree = ? AND cout = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newTraitement.getNom());
            stmt.setString(2, newTraitement.getPosologie());
            stmt.setString(3, newTraitement.getNotes());
            stmt.setInt(4, newTraitement.getDuree());
            stmt.setInt(5, newTraitement.getCout());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // returns true if there is at least one matching record
            }
        }
        return false;

}}
