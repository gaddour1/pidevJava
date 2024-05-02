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
import utils.PDFGenerator;
import utils.SchedulerService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    @FXML
    private TextField email;


    public Ajoutervisite() {
        servicetraitement = new Servicetraitement();
        servicevisite = new Servicevisite();
    }
    @FXML
    void ajouterv(ActionEvent event) throws SQLException {
        boolean nouvelleVisiteAjoutée = false;

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
            nouvelleVisiteAjoutée = true;

            showInformationAlert("SUCCÈS", "Visite ajoutée avec succès");
        } catch (SQLException e) {
            showAlert("Erreur", "Échec de l'ajout de la visite : " + e.getMessage());

        }
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(heurev.getText(), timeFormatter);
        LocalDateTime dateTime = LocalDateTime.of(datefx.getValue(), time);
        long delay = Duration.between(LocalDateTime.now(), dateTime.minusHours(2)).toMillis();

        if (delay > 0) {
            new SchedulerService().scheduleEmailReminder(
                    email.getText(),
                    "Rappel de visite",
                    "Vous avez un rendez-vous prévu à " + heurev.getText() + " le " + datefx.getValue().toString(),
                    delay
            );}
        else {
            showAlert("Erreur", "L'heure de rappel est déjà passée.");
        }

        if (nouvelleVisiteAjoutée) {
            try {
                PDFGenerator.generateVisitReport(newVisite, "C:\\Users\\USER\\Downloads\\visitespdf/visit_report.pdf");
                showAlert("Succès", "Le rapport PDF a été créé avec succès.");
            } catch (Exception e) {
                showAlert("Erreur", "Erreur lors de la génération du rapport PDF : " + e.getMessage());
            }
            }
        }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
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

