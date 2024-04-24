
package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import utils.MydataBase;



import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginController {

    @FXML
    private AnchorPane signin_form;

    @FXML
    private AnchorPane signup_form;

    @FXML
    private TextField username_text;

    @FXML
    private TextField password_text;

    @FXML
    private CheckBox showPassword;

    @FXML
    private Button signInButton;
    @FXML
    private Button signUp_Button;

    @FXML
    private Hyperlink haveAnAccountLink;

    @FXML
    private TextField email_reg;

    @FXML
    private TextField password_reg;


    @FXML
    private TextField username_reg;

    @FXML
    private TextField nom_reg;

    @FXML
    private TextField numero_reg;

    @FXML
    private TextField cin_reg;

    @FXML
    private TextField adresse_reg;

    @FXML
    private ChoiceBox<String> choiceBox; // Reference the ChoiceBox with fx:id "choiceBox"



    @FXML
    private void initialize() {
        // Initialize other components
        signInButton.setOnAction(this::signIn);
        signUp_Button.setOnAction(this::signUp);

        // Populate the ChoiceBox with roles
        choiceBox.getItems().addAll("Infirmier", "Patient", "Médecin");
       // choiceBox.setValue("Infirmier"); // Set a default value if needed
    }

    @FXML
    private void signIn(ActionEvent event) {
        String username = username_text.getText();
        String password = password_text.getText();

        try {
            Connection connection = MydataBase.getInstance().getCnx();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Sign in successful, navigate to cards.fxml page
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cards.fxml"));
                Parent root = loader.load();
                Object controller = loader.getController(); // Assuming the controller class is CardsController
                Stage stage = (Stage) signInButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                // Sign in failed, show error message or perform other actions
                System.out.println("Invalid username or password");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    private void signUp(ActionEvent event) {
        String username = username_reg.getText();
        String nom = nom_reg.getText();
        Integer numero = Integer.parseInt(numero_reg.getText().trim());
        Integer cin = Integer.parseInt(cin_reg.getText().trim());
        String adresse = adresse_reg.getText();
        String email = email_reg.getText();
        String password = password_reg.getText();
        String role = choiceBox.getValue(); // Get the selected role from the ChoiceBox

        // Mapper le rôle sélectionné par l'utilisateur à un rôle spécifique dans la base de données


        try {
            Connection connection = MydataBase.getInstance().getCnx();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (username, email, password, role, nom, numero, cin, adresse) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);
            preparedStatement.setString(4, role); // Utiliser le rôle adapté pour la base de données
            preparedStatement.setString(5, nom);
            preparedStatement.setInt(6, numero);
            preparedStatement.setInt(7, cin);
            preparedStatement.setString(8, adresse);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Utilisateur enregistré avec succès avec le rôle : " + role);
                // Après une inscription réussie, masquer le formulaire d'inscription et afficher le formulaire de connexion
                signup_form.setVisible(false);
                signin_form.setVisible(true);
            } else {
                System.out.println("Échec de l'enregistrement de l'utilisateur.");
                // Vous pouvez afficher un message d'erreur ou effectuer d'autres actions si l'inscription échoue.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les erreurs de connexion à la base de données ou de requête SQL ici
        } catch (NumberFormatException e) {
            System.out.println("Veuillez entrer des valeurs valides pour les champs numériques.");
            // Gérer les erreurs de conversion de chaîne en nombre ici
        }
    }



    private void showRegistrationSection() {
        // Hide the sign-in form
        signin_form.setVisible(false);

        // Show the sign-up form
        signup_form.setVisible(true);
    }
}

