package org.example.amine.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.amine.services.SessionService;
import org.example.amine.utils.dataSource;


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
    private void initialize() {
        // Set action for the "Sign In" button
        signInButton.setOnAction(this::signIn);
        signUp_Button.setOnAction(this::signUp);
    }

    @FXML
    private void signIn(ActionEvent event) {
        String username = username_text.getText();
        String password = password_text.getText();

        try {
            Connection connection = dataSource.getInstance().getCnx();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE username=? AND password=?");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                // Connexion réussie, définissez l'ID de l'utilisateur dans la session
                int userId = resultSet.getInt("id"); // Assurez-vous que la colonne s'appelle 'id' dans votre base de données
                SessionService.getInstance().setSignedInUserId(userId);
                System.out.println(userId);


                // Naviguez vers la page cards.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/cards.fxml"));
                Parent root = loader.load();
                cardsController controller = loader.getController(); // Supposons que la classe du contrôleur est cardsController
                Stage stage = (Stage) signInButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();
            } else {
                // Échec de la connexion, affichez un message d'erreur ou effectuez d'autres actions
                System.out.println("Nom d'utilisateur ou mot de passe invalide");
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void signUp(ActionEvent event) {
        String username = username_reg.getText();
        String email = email_reg.getText();
        String password = password_reg.getText();

        try {
            Connection connection = dataSource.getInstance().getCnx();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (username, email, password) VALUES (?, ?, ?)");
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User registered successfully!");
                // After successful registration, hide the sign-up form and show the sign-in form
                signup_form.setVisible(false);
                signin_form.setVisible(true);
            } else {
                System.out.println("Failed to register user.");
                // You can display an error message or perform other actions if the registration fails.
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database connection or query errors here
        }
    }


    private void showRegistrationSection() {
        // Hide the sign-in form
        signin_form.setVisible(false);

        // Show the sign-up form
        signup_form.setVisible(true);
    }
}
