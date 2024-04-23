package controllers;

import entities.traitement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.Servicetraitement;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class Affichertraitement {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<traitement, Integer> couttv;

    @FXML
    private TableColumn<traitement, Integer> dureetv;

    @FXML
    private TableColumn<traitement, String> nomtv;

    @FXML
    private TableColumn<traitement, String> notestv;

    @FXML
    private TableColumn<traitement, String> posologietv;

    @FXML
    private TableView<traitement> tableview;

    @FXML
    void initialize() {
        Servicetraitement servicetraitement=new Servicetraitement();
        try{
            List<traitement>traitements = servicetraitement.afficher();
            ObservableList<traitement> observableList= FXCollections.observableList(traitements);
            tableview.setItems(observableList);
                nomtv.setCellValueFactory(new PropertyValueFactory<>("nom"));
            dureetv.setCellValueFactory(new PropertyValueFactory<>("duree"));
            posologietv.setCellValueFactory(new PropertyValueFactory<>("posologie"));
            notestv.setCellValueFactory(new PropertyValueFactory<>("notes"));
            posologietv.setCellValueFactory(new PropertyValueFactory<>("posologie"));
            couttv.setCellValueFactory(new PropertyValueFactory<>("cout"));
            posologietv.setCellValueFactory(new PropertyValueFactory<>("posologie"));




        } catch(SQLException e){
System.err.println(e.getMessage());
        }

    }
    @FXML
    void modifierbtn(ActionEvent event) {


    }

    @FXML
    void supprimerbtn(ActionEvent event) {
    }
}
