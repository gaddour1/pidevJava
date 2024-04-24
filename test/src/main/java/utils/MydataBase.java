package utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MydataBase {
    private String url = "jdbc:mysql://localhost:3306/amine";
    private String user = "root";
    private String passwd = "";

    private Connection cnx;

    private static MydataBase instance;

    private MydataBase(){
        try {
            cnx = DriverManager.getConnection(url,user,passwd);
            System.out.println("Connected to DB !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static MydataBase getInstance(){
        if(instance == null){
            instance = new MydataBase();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}