package org.example.amine.services;

import org.example.amine.entities.event;

import java.sql.SQLException;
import java.util.List;

public interface IService<T>{
    public void ajouter(T p) throws SQLException;
    public void modifier(T p) throws SQLException;
    public void supprimer(int id) throws SQLException;
    public T getOneById(int id) throws SQLException;
    public List<event> getAll() throws SQLException;

}
