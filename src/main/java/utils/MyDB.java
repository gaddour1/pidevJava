package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyDB {
    final String URL="jdbc:mysql://localhost:3306/pidev";
    final String USERNAME="root";
    final String PASSWORD="";
    Connection connection;
    static MyDB  instance ;
    private MyDB(){
        try {
        connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        System.out.println("Connexion etablie");
    } catch (SQLException e) {
            System.out.println(e.getMessage());}
    }
public static MyDB getInstance(){
        if(instance==null){
            instance=new MyDB();
        }
        return instance;
}

    public Connection getConnection() {
        return connection;
    }
}
