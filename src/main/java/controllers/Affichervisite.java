package controllers;

import entities.traitement;
import entities.visite;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.Servicevisite;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class Affichervisite {

    @FXML
    private TableColumn<visite, Void> actions;

    @FXML
    private TableColumn<visite, String> datev;

    @FXML
    private TableColumn<visite, String> heurev;

    @FXML
    private TableColumn<visite, Integer> idv;

    @FXML
    private TableColumn<visite, String> lieuv;

    @FXML
    private TableView<visite> tablev;

    @FXML
    private TableColumn<visite, String> traitementv;


    private ObservableList<visite> visitesObservableList = FXCollections.observableArrayList();
    private Servicevisite serviceVisite = new Servicevisite();

    @FXML
    void initialize() {
        initializeVisiteTab();
        setupActionColumn();
    }

    private void initializeVisiteTab() {
        try {
            List<visite> visiteList = serviceVisite.afficher();
            visitesObservableList.setAll(visiteList);
            tablev.setItems(visitesObservableList);

            datev.setCellValueFactory(new PropertyValueFactory<>("date"));
            lieuv.setCellValueFactory(new PropertyValueFactory<>("lieu"));
            heurev.setCellValueFactory(new PropertyValueFactory<>("heure"));
            traitementv.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTraitement().getNom()));
        } catch (SQLException e) {
            showAlert("Error", "Error Loading visites", e.getMessage());
        }
    }

    @FXML
    void refresh(ActionEvent event) {
        try {

            List<visite> updatedEventList = serviceVisite.afficher();

            visitesObservableList.setAll(updatedEventList);
            tablev.setItems(visitesObservableList);
            tablev.refresh();

            showInformationAlert("Success", "Table View refreshed successfully with updated data");
        } catch (SQLException e) {

            showAlert("Error", "Error refreshing Table View", e.getMessage());
        }

    }

    void setupActionColumn() {
        actions.setCellFactory(param -> new TableCell<visite, Void>() {
            private final Button deleteButton = new Button("Supprimer");
            private final Button updateButton = new Button("Modifier");

            {
                deleteButton.setOnAction(event -> {
                    visite visite = getTableView().getItems().get(getIndex());
                    deleteEvent(visite);
                });

                updateButton.setOnAction(event -> {
                    visite visite = getTableView().getItems().get(getIndex());
                    openUpdateWindow(visite);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox buttonsBox = new HBox(deleteButton, updateButton);
                    buttonsBox.setSpacing(5);
                    setGraphic(buttonsBox);
                }
            }
        });
    }

    private void deleteEvent(visite visite) {
        if (ConfirmationDialog.showConfirmationDialog("Êtes-vous sûr de vouloir supprimer cette visite ?")) {
            try {
                serviceVisite.supprimer(visite);
                visitesObservableList.remove(visite);
                showInformationAlert("Succès", "Visite supprimée avec succès");
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression de la visite", e.getMessage());
            }
        }
    }

    private void openUpdateWindow(visite visite) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/projetjava/Ajoutervisite.fxml"));
            Parent root = loader.load();
            Ajoutervisite controller = loader.getController();
            controller.initDataForUpdate(visite);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Mettre à jour la visite");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            showAlert("Erreur de Chargement", "Erreur lors du chargement du fichier FXML", e.getMessage());
        }
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    static class ConfirmationDialog {
        public static boolean showConfirmationDialog(String message) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(message);
            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }
    }
}
