package services;

import entities.user; // Assuming this is the correct import for the 'user' class

import java.sql.SQLException;
import java.util.List;

public interface IService<T> {
    void ajouter(T p) throws SQLException;
    void modifier(T p) throws SQLException;
    void supprimer(int id) throws SQLException;
    T getOneById(int id) throws SQLException;
    List<user> getAll() throws SQLException; // Changed from 'T' to 'user'
}
