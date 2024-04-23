package tests;


import entities.traitement;
import entities.visite;
import services.Servicetraitement;
import services.Servicevisite;
import utils.MyDB;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {


        //  MyDB.getInstance().getConnection();
     //   traitement trait = new traitement("traitttt",15,"aaaa","ooo",10);
       traitement trait1 = new traitement(14,"trait11111",20,"bbb","oiii",15);
        visite visite111 = new visite("2010-10-10", "espoir", "15:00",trait1);
        visite visite222 = new visite(145,"2020-12-12", "espoir", "15:00",trait1);




/*
        Servicetraitement servicetraitement = new Servicetraitement();
        try{
            servicetraitement.ajouter(trait1);


        } catch(SQLException e){
            System.out.println(e.getMessage());
        }
*/

                // visite
        Servicevisite servicevisite = new Servicevisite();
        try{
            servicevisite.ajouter(visite222);
        } catch(SQLException e){
            System.out.println(e.getMessage());
        }

/*
        Servicevisite servicevisite = new Servicevisite();
      try{
          servicevisite.supprimer(145);


      } catch(SQLException e){
          System.out.println(e.getMessage());
      }
      try {
          System.out.println(servicevisite.afficher());
      } catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
*/
}}
