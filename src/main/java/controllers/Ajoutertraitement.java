package controllers;

import entities.traitement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.Servicetraitement;
import tests.HelloApplication;

import java.io.IOException;
import java.sql.SQLException;

public class Ajoutertraitement {
    private traitement traitementToUpdate;
    @FXML
    private Button ajouttfx;

    @FXML
    private TextField coutfx;

    @FXML
    private TextField dureefx;

    @FXML
    private TextField nomfx;

    @FXML
    private TextArea notefx;

    @FXML
    private TextField posologiefx;

    @FXML
    void ajouterbtn(ActionEvent event) {
        traitement newTraitement = new traitement();
        newTraitement.setNom(nomfx.getText());
        newTraitement.setDuree(Integer.parseInt(dureefx.getText()));
        newTraitement.setPosologie(posologiefx.getText());
        newTraitement.setNotes(notefx.getText());
        newTraitement.setCout(Integer.parseInt(coutfx.getText()));

        Servicetraitement servicetraitement = new Servicetraitement();
        try {
            if (!servicetraitement.exists(newTraitement)) {
                servicetraitement.ajouter(newTraitement);
                showAlert(Alert.AlertType.CONFIRMATION, "Success", "Traitement ajouté avec succès.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Duplicate Entry", "Ce traitement existe déjà.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Erreur lors de l'ajout du traitement: " + e.getMessage());
        }
    }

    private boolean validateUniqueness(traitement newTraitement) {
        Servicetraitement servicetraitement = new Servicetraitement();
        try {
            if (servicetraitement.exists(newTraitement)) {
                showAlert(Alert.AlertType.ERROR, "Erreur de Duplication", "Un traitement avec les mêmes informations existe déjà existe .");
                return false;
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de Base de Données", "Erreur lors de la vérification de l'unicité: " + e.getMessage());
            return false;
        }
        return true;
    }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    void afficherbtn(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/projetjava/Affichertraitement.fxml"));
        try {
            nomfx.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de Chargement", "Impossible de charger la vue.");
        }
    }

    // Helper method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Input validation method
    private boolean validateInput() {
        String errorMessage = "";

        if (nomfx.getText() == null || nomfx.getText().isEmpty()) {
            errorMessage += "Nom ne peut pas être vide!\n";
        }
        if (dureefx.getText() == null || dureefx.getText().isEmpty()) {
            errorMessage += "Durée doit être remplie!\n";
        } else {
            try {
                Integer.parseInt(dureefx.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Durée doit être un nombre entier!\n";
            }
        }
        if (coutfx.getText() == null || coutfx.getText().isEmpty()) {
            errorMessage += "Coût doit être rempli!\n";
        } else {
            try {
                Integer.parseInt(coutfx.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Coût doit être un nombre entier!\n";
            }
        }

        if (!errorMessage.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Erreur de Validation", errorMessage);
            return false;
        }
        return true;
    }

    public void initDataForUpdate(traitement traitement) {
        traitementToUpdate = traitement;

        // Populate form fields with event data
        nomfx.setText(traitement.getNom());
        dureefx.setText(String.valueOf(traitement.getDuree()));
        posologiefx.setText(String.valueOf(traitement.getPosologie()));
        notefx.setText(traitement.getNotes());
        coutfx.setText(String.valueOf(traitement.getCout()));



        //  startdatepro.setText(entities.traitement.getPromo_start_date().toString());
    }
}
