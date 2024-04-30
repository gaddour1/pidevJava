package controllers;

import entities.traitement;
import entities.visite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import services.Servicetraitement;
import services.Servicevisite;
import tests.HelloApplication;
import controllers.* ;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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
    private visite visiteToUpdate;


    public Ajoutervisite() {
        servicetraitement = new Servicetraitement();
        servicevisite = new Servicevisite();
    }
    @FXML
    void ajouterv(ActionEvent event) throws SQLException {
        traitement selectedTraitement = combotrait.getValue();
        if (selectedTraitement == null) {
            showAlert("Erreur", "Veuillez sélectionner un traitement.");
            return;
        }

        if (datefx.getValue() == null) {
            showAlert("Erreur", "Veuillez entrer une date.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = datefx.getValue().format(formatter);
        LocalDate dateChoisie = LocalDate.parse(dateString, formatter);
        if (dateChoisie.isBefore(LocalDate.now())) {
            showAlert("Erreur", "La date doit être supérieure à la date d'aujourd'hui.");
            return;
        }

        String lieu = lieuv.getText().trim();
        if (!lieu.toLowerCase().contains("tunis")) {
            showAlert("Erreur", "Le lieu doit être en Tunisie.");
            return;
        }

        String heure = heurev.getText().trim();
        if (!heure.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
            showAlert("Erreur", "L'heure doit être au format HH:mm.");
            return;
        }

        visite newVisite = new visite(dateString, lieu, heure, selectedTraitement);
        try {
            servicevisite.ajouter(newVisite);
            showAlert("SUCCÈS", "Visite ajoutée avec succès");
        } catch (SQLException e) {
            showAlert("Erreur", "Échec de l'ajout de la visite : " + e.getMessage());
        }
    }
    @FXML
    void afficherv(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/projetjava/Affichervisite.fxml"));
        try {
            datefx.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de Chargement", "Impossible de charger la vue.");
        }

    }

    private void showAlert(Alert.AlertType alertType, String erreurDeChargement, String s) {

    }

    private void showAlert( String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();

    }

        @FXML
    private void initialize() {
        loadTraitementData();
    }


    private void loadTraitementData() {
        servicetraitement = new Servicetraitement();  // S'assurer que ceci est bien initialisé
        ObservableList<traitement> traitements = FXCollections.observableArrayList();

        try {
            traitements.addAll(servicetraitement.afficher());
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
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setContentText("Impossible de charger les traitements : " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void initDataForUpdate(visite visite) {
         visiteToUpdate = visite;

        // Populate form fields with event data
        if (visite.getDate() != null && !visite.getDate().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(visite.getDate(), formatter);
            datefx.setValue(localDate);
        } else {
            datefx.setValue(null); // Set to null or to a default value if necessary
        }
        lieuv.setText(String.valueOf(visite.getLieu()));
        heurev.setText(String.valueOf(visite.getHeure()));
        combotrait.getSelectionModel().select(visite.getTraitement());

    }
}

