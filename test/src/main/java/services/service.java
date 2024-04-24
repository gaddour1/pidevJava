package services;

import entities.event;
import entities.user;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
public interface service <T>{
    public void ajouter(T t) throws SQLException;
    public void modifier(T t) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public List<user> afficher() throws SQLException;

}
