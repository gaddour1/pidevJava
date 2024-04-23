package controllers;

import entities.traitement;
import entities.visite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import services.Servicetraitement;
import services.Servicevisite;

import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Ajoutervisite {
    @FXML
    private ComboBox<traitement> combotrait;

    @FXML
    private DatePicker datefx;

    @FXML
    private TextField heurev;

    @FXML
    private TextField lieuv;
    private Servicetraitement servicetraitement;
    private traitement selectedTraitement;
    private Servicevisite servicevisite;

    @FXML
    void ajouterv(ActionEvent event) throws SQLException {
        /*
        traitement selectedTraitement = combotrait.getValue();
        if (selectedTraitement == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un traitement.");
            alert.showAndWait();
            return; // Arrête la fonction si aucun traitement n'est sélectionné
        }

        Servicevisite servicevisite = new Servicevisite();
        // Formatteur pour la date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // Conversion de LocalDate en String selon le format désiré
        String dateString = datefx.getValue().format(formatter);
        visite visite = new visite(
                dateString,
                lieuv.getText(),
                heurev.getText(),
                combotrait.getValue()
        );
        try {
            servicevisite.ajouter(visite);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("SUCCES");
            alert.setContentText("visite ajouté");
            alert.showAndWait();
            // Afficher un message de succès ou effectuer d'autres actions après l'ajout de l'article
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
            // Gérer l'erreur lors de l'ajout de l'article
        }*/
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = datefx.getValue().format(formatter);
        String heure = heurev.getText();
        String lieu = lieuv.getText();





        if (selectedTraitement == null) {
            System.out.println("Veuillez sélectionner une catégorie.");
            return;
        }


        visite visite = new visite( dateString,heure,lieu,selectedTraitement);
        servicevisite.ajouter(visite);
        System.out.println("Article ajouté avec succès !");

    }

























    private void loadTraitementData() throws SQLException {
       /* Servicetraitement servicetraitement = new Servicetraitement();
        ObservableList<traitement> traitements = FXCollections.observableArrayList(servicetraitement.afficher());

        combotrait.setItems(traitements);
*/    Servicetraitement servicetraitement = new Servicetraitement();
        ObservableList<traitement> traitements = FXCollections.observableArrayList();
        try {
            traitements.addAll(servicetraitement.afficher());
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setContentText("Impossible de charger les traitements : " + e.getMessage());
            alert.showAndWait();
            return;
        }

        combotrait.setItems(traitements);
        combotrait.setConverter(new StringConverter<traitement>() {
            @Override
            public String toString(traitement object) {
                return object != null ? object.getNom() : "";
            }

            @Override
            public traitement fromString(String string) {
                return traitements.stream().filter(item -> item.getNom().equals(string)).findFirst().orElse(null);
            }
        });
    }

    }


