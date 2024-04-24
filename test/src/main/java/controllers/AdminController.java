package controllers;

import entities.user;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import services.serviceAdmin;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminController {

    @FXML
    private TextField M_adresse;

    @FXML
    private TextField M_cin;

    @FXML
    private TextField M_email;

    @FXML
    private TextField M_motDePasse;

    @FXML
    private TextField M_nom;

    @FXML
    private TextField M_numero;

    @FXML
    private TextField M_prenom;

    @FXML
    private ChoiceBox<String> M_role;

    @FXML
    private ListView<user> userListView;

    private final serviceAdmin userService = new serviceAdmin();

     private ObservableList<String> rolesList = FXCollections.observableArrayList("Patient", "Médecin", "Infirmier");

    @FXML
    public void ajouter(ActionEvent event) {
        try {
            // Create a new user object
            user e = new user();
            e.setNom(M_nom.getText());
            e.setAdresse(M_adresse.getText());
            e.setNumero(Integer.parseInt(M_numero.getText()));
            e.setCin(Integer.parseInt(M_cin.getText()));
            e.setEmail(M_email.getText());
            e.setPassword(M_motDePasse.getText());
            e.setUsername(M_prenom.getText());
            e.setRole(M_role.getValue());

            // Add the user to the ListView
            userListView.getItems().add(e);

            // Clear the text fields after adding the user
            M_nom.clear();
            M_adresse.clear();
            M_numero.clear();
            M_cin.clear();
            M_email.clear();
            M_motDePasse.clear();
            M_prenom.clear();

            // Call the service layer to add the user to the database
            userService.ajouter(e);

            // Provide feedback to the user
            // showAlert("User added successfully!");
        } catch (NumberFormatException ex) {
            // showAlert("Please enter valid numbers for CIN and Numero.");
        } catch (SQLException ex) {
            // showAlert("A database error occurred. Please try again.");
        }
    }

    @FXML
    public void initialize() {
        afficher();
        M_role.setItems(rolesList);
    }


    @FXML
    public void modifier(ActionEvent user) throws SQLException {
        user selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Pre-fill the text fields with details of the selected user
            M_nom.setText(selectedUser.getNom());
            M_adresse.setText(selectedUser.getAdresse());
            M_numero.setText(String.valueOf(selectedUser.getNumero()));
            M_cin.setText(String.valueOf(selectedUser.getCin()));
            M_email.setText(selectedUser.getEmail());
            M_role.setValue(selectedUser.getRole());
            M_motDePasse.setText(selectedUser.getPassword());
            M_prenom.setText(selectedUser.getUsername());

            // Create a confirmation dialog
            ButtonType modifierButtonType = new ButtonType("Modifier", ButtonBar.ButtonData.OK_DONE);
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Modifier Utilisateur");
            dialog.setHeaderText("Modifier les détails de l'utilisateur sélectionné:");
            dialog.getDialogPane().getButtonTypes().addAll(modifierButtonType, ButtonType.CANCEL);

            // Ajouter les champs de texte au dialogue pour l'entité 'user'
            GridPane grid = new GridPane();
            grid.setHgap(10); // Horizontal gap between columns
            grid.setVgap(10); // Vertical gap between rows
            grid.setPadding(new Insets(20, 150, 10, 10)); // Padding around the grid

// Assuming M_nom, M_adresse, etc. are already defined as TextField members of the class
            grid.add(new Label("Nom:"), 0, 0);
            grid.add(M_nom, 1, 0);
            grid.add(new Label("Adresse:"), 0, 1);
            grid.add(M_adresse, 1, 1);
            grid.add(new Label("Numéro:"), 0, 2);
            grid.add(M_numero, 1, 2);
            grid.add(new Label("CIN:"), 0, 3);
            grid.add(M_cin, 1, 3);
            grid.add(new Label("Email:"), 0, 4);
            grid.add(M_email, 1, 4);
            grid.add(new Label("Mot de Passe:"), 0, 5);
            grid.add(M_role, 1, 5);
            grid.add(new Label("Mot de Passe:"), 0, 6);
            grid.add(M_motDePasse, 1, 6);
            grid.add(new Label("Prénom:"), 0, 7);
            grid.add(M_prenom, 1, 8);

            dialog.getDialogPane().setContent(grid);

            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == modifierButtonType) {
                // Update the selected user with the new values
                selectedUser.setNom(M_nom.getText());
                selectedUser.setAdresse(M_adresse.getText());
                selectedUser.setNumero(Integer.parseInt(M_numero.getText()));
                selectedUser.setCin(Integer.parseInt(M_cin.getText()));
                selectedUser.setEmail(M_email.getText());
                selectedUser.setPassword(M_motDePasse.getText());
                selectedUser.setUsername(M_prenom.getText());

                // Call the service method to update the user
                userService.modifier(selectedUser);

                // Refresh the ListView to reflect the changes
                afficher();
            }
        }
    }

    @FXML
    public void supprimer(ActionEvent event) {
        user selectedUser = userListView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Remove the selected user from the ListView
            userListView.getItems().remove(selectedUser);

            // Optionally, call the service layer to delete the user from the database
            // userService.supprimer(selectedUser);
        } else {
            showAlert("Please select a user to delete.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


 /*   @FXML
    public void afficher() {
        try {
            // Fetch the list of users from the service
            List<user> userList = userService.getAll(); // Assuming there is a method called getAllUsers in userService

            // Clear the current items in the ListView
            userListView.getItems().clear();

            // Add all fetched users to the ListView
            userListView.getItems().addAll(userList);

        } catch (SQLException ex) {
            // Handle any SQL exceptions here
            // showAlert("Error fetching users from the database.");
        }
    }*/

    @FXML
    public void afficher() {
        try {
            List<user> events = userService.getAll();
            userListView.getItems().setAll(events);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
