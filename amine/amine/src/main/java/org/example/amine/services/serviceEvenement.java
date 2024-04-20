package org.example.amine.services;

import org.example.amine.entities.event;
import org.example.amine.utils.dataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class serviceEvenement implements IService<event> {

    Connection cnx = dataSource.getInstance().getCnx();

    @Override
    public void ajouter(event p) throws SQLException {
        String req = "INSERT INTO `evenement`(`titre`, `description`, `date`, `lieu`) VALUES (?,?,?,?)";
        System.out.println("SQL Query: " + req); // Debugging

        PreparedStatement ps = cnx.prepareStatement(req);
        ps.setString(1, p.getTitre());
        ps.setString(2, p.getDescription());
        ps.setString(3, p.getDate()); // Update: Added date
        ps.setString(4, p.getLieu()); // Update: Added lieu

        ps.executeUpdate();
        System.out.println("Event added successfully!");
    }

    @Override
    public void modifier(event p) {
        // Implement modification logic if needed
    }

    @Override
    public void supprimer(int id) {
        // Implement deletion logic if needed
    }

    @Override
    public event getOneById(int id) {
        // Implement logic to get one event by ID if needed
        return null;
    }

    @Override
    public List<event> getAll() throws SQLException {
        List<event> events = new ArrayList<>();
        String query = "SELECT * FROM evenement";
        try (PreparedStatement ps = cnx.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String titre = rs.getString("titre");
                String description = rs.getString("description");
                String date = rs.getString("date");
                String lieu = rs.getString("lieu");
                String image = rs.getString("image");
                event e = new event(id, titre, description, date, lieu,image);
                events.add(e);
            }
        }
        return events;
    }



}
