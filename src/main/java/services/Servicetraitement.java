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
    public void supprimer(traitement traitement) throws SQLException {
        String req = "DELETE FROM visite WHERE id=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, traitement.getId());
        int affectedRows = pre.executeUpdate();
        if (affectedRows > 0) {
            System.out.println("traitement supprimée avec succès");
        } else {
            System.out.println("Aucune traitement n'a été supprimée. Vérifiez que l'ID existe.");
        }

    }

    @Override
    public List<traitement> afficher() throws SQLException {
        List<traitement> traits = new ArrayList<>();
        String req = "select * from traitement";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);
        while (rs.next()) {
            traitement trait = new traitement(10,"amna", 20, "aaaaaa", "amnaaa", 12);
            trait.setId(rs.getInt("id"));
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

}
    public List<String> getAllLibelles1() throws SQLException {
        List<String> noms = new ArrayList<>();
        String req = "SELECT nom FROM traitement";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(req);

        while (rs.next()) {
            noms.add(rs.getString("nom"));
        }

        rs.close();
        statement.close();

        return noms;
    }
    public int getIdByLibelle1(String nom) throws SQLException {
        String req = "SELECT id FROM traitement WHERE nom = ?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, nom);

        ResultSet rs = pre.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("id");
            rs.close();
            pre.close();
            return id;
        } else {
            rs.close();
            pre.close();
            throw new RuntimeException("Le traitement avec le nom '" + nom + "' n'existe pas.");
            // Ou vous pouvez retourner -1 pour indiquer que le traitement n'existe pas
            // return -1;
        }
    }
    public traitement getByLibelle1(String libelle) {
        String query = "SELECT * FROM traitement WHERE nom = ?"; // Utiliser des requêtes paramétrées pour éviter l'injection SQL
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            Connection connexion = null;
            ps = connexion.prepareStatement(query);
            ps.setString(1, libelle); // Définir le paramètre de la requête
            rs = ps.executeQuery();
            if (rs.next()) {
                traitement tr = new traitement();
                tr.setId(rs.getInt("id"));
                tr.setNom(rs.getString("nom"));
                tr.setDuree(rs.getInt("duree")); // Hypothèse: il existe un champ `duree`
                tr.setPosologie(rs.getString("posologie")); // Hypothèse: il existe un champ `posologie`
                tr.setNotes(rs.getString("notes")); // Hypothèse: il existe un champ `notes`
                tr.setCout(rs.getInt("cout")); // Hypothèse: il existe un champ `cout`
                return tr;
            } else {
                throw new RuntimeException("Le traitement avec le nom '" + libelle + "' n'existe pas.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération du traitement par nom : " + e.getMessage());
        } finally {
            // Fermer les ressources JDBC
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (SQLException e) {
                throw new RuntimeException("Erreur lors de la fermeture des ressources JDBC : " + e.getMessage());
            }
        }
    }
    public List<traitement> findTreatmentsByKeywords(String keywords) throws SQLException {
        List<traitement> treatments = new ArrayList<>();
        String sql = "SELECT * FROM traitement WHERE notes LIKE ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, "%" + keywords + "%");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                treatments.add(new traitement(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("duree"),
                        rs.getString("posologie"),
                        rs.getString("notes"),
                        rs.getInt("cout")
                ));
            }
        }
        return treatments;
    }
}
