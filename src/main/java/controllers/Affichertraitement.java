package controllers;

import entities.traitement;
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
import services.Servicetraitement;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Affichertraitement {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<traitement, Void > actioncolumn;

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
    private ObservableList<traitement> traitementsobservableList = FXCollections.observableArrayList();

    private final Servicetraitement servicestraitement= new Servicetraitement();

    private ObservableList<traitement> observableList;
    @FXML
         void initialize() {
        /*
        Servicetraitement servicetraitement = new Servicetraitement();
        try {
            List<traitement> traitements = servicetraitement.afficher();
            ObservableList<traitement> observableList = FXCollections.observableList(traitements);
            tableview.setItems(observableList);
            nomtv.setCellValueFactory(new PropertyValueFactory<>("nom"));
            dureetv.setCellValueFactory(new PropertyValueFactory<>("duree"));
            posologietv.setCellValueFactory(new PropertyValueFactory<>("posologie"));
            notestv.setCellValueFactory(new PropertyValueFactory<>("notes"));
            posologietv.setCellValueFactory(new PropertyValueFactory<>("posologie"));
            couttv.setCellValueFactory(new PropertyValueFactory<>("cout"));
            posologietv.setCellValueFactory(new PropertyValueFactory<>("posologie"));


        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
*/
        initializetraitementtab();
        setupActionColumn();
         }

    private void initializetraitementtab() {
        try {
            // Read categories from the database
            List<traitement> traitementList = servicestraitement.afficher();
             observableList = FXCollections.observableArrayList(traitementList);

            // Set items to the TableView
            tableview.setItems(observableList);

            // Set cell value factories for each column
            nomtv.setCellValueFactory(new PropertyValueFactory<>("nom"));
            dureetv.setCellValueFactory(new PropertyValueFactory<>("duree"));
            posologietv.setCellValueFactory(new PropertyValueFactory<>("posologie"));
            notestv.setCellValueFactory(new PropertyValueFactory<>("notes"));
            couttv.setCellValueFactory(new PropertyValueFactory<>("cout"));

        } catch (SQLException e) {
            // Show an error alert if loading categories fails
            showAlert("Error", "Error Loading traitements", e.getMessage());
        }
        // Add listener to search field
      // searchfieldpro.textProperty().addListener((observable, oldValue, newValue) -> {
            // Call method to filter TableView based on search text
      //    filterTableView(newValue);
      // });

    }

    void setupActionColumn() {
                 actioncolumn.setCellFactory(param -> new TableCell<traitement, Void>()
                 {
                     private final Button deleteButton = new Button("Supprimer");
                      private final Button updateButton = new Button("Modifier");

                      {
                           // DÃ©finir l'action pour le bouton Supprimer
                          deleteButton.setOnAction(event -> {
                    traitement t = getTableView().getItems().get(getIndex());
                    // Effectuer l'action de suppression
                    deleteEvent(t);
                          });

                       // DÃ©finir l'action pour le bouton Modifier
                         updateButton.setOnAction(event -> {
                    traitement t = getTableView().getItems().get(getIndex());
                    // Effectuer l'action de mise Ã  jour
                    openUpdateWindow(t);
                });

            }
                     @Override
                     protected void updateItem(Void item, boolean empty) {
                         super.updateItem(item, empty);
                         if (empty) {
                             setGraphic(null);
                         } else {
                             // Ajouter les deux boutons Ã  un HBox pour la mise en page
                             HBox buttonsBox = new HBox(deleteButton, updateButton);
                             buttonsBox.setSpacing(5); // Ajuster l'espacement entre les boutons si nÃ©cessaire
                             setGraphic(buttonsBox);
                         }
                     }
                 });
             }
         void deleteEvent(traitement traitement) {
        boolean confirmed = ConfirmationDialog.showConfirmationDialog("etes-vous sur de vouloir supprimer ce traitement ?");
        if (confirmed) {
            // Create an instance of ServicesSponsor
            Servicetraitement servicestraitement = new Servicetraitement();

            // Call the supprimer method on the instance of ServicesSponsor
            try {
                servicestraitement.supprimer(traitement);
                traitementsobservableList.remove(traitement);
                showInformationAlert("Succés", "traitement supprimé avec succés");
            } catch (SQLException e) {
                showAlert("Erreur", "Erreur lors de la suppression du traitement", e.getMessage());
            }
        } }
        void openUpdateWindow (traitement traitement) {
            try {
                // Préparation du chemin correct du fichier FXML
                FXMLLoader loader = new FXMLLoader();
                URL fxmlUrl = getClass().getResource("/projetjava/Ajoutertraitement.fxml");
                if (fxmlUrl == null) {
                    throw new IllegalStateException("Fichier FXML non trouvé : Vérifiez le chemin.");
                }
                loader.setLocation(fxmlUrl);
                Parent root = loader.load();

                // Configuration du contrôleur pour la mise à jour
                Ajoutertraitement controller = loader.getController();
                controller.initDataForUpdate(traitement);

                // Affichage de la nouvelle scène
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Mettre à jour le traitement");
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
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
        }
         class ConfirmationDialog {
             public static boolean showConfirmationDialog(String message) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText(message);
            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }
    }

