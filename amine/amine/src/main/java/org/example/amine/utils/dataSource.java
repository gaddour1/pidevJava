package org.example.amine.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dataSource {
    private String url = "jdbc:mysql://localhost:3306/amine";
    private String user = "root";
    private String passwd = "";

    private Connection cnx;

    private static dataSource instance;

    private dataSource(){
        try {
            cnx = DriverManager.getConnection(url,user,passwd);
            System.out.println("Connected to DB !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static dataSource getInstance(){
        if(instance == null){
            instance = new dataSource();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
