package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class IMC extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Charger le fichier FXML et créer la scène avec les dimensions souhaitées
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/projetjava/IMC.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 600);

        stage.setTitle("Hello!"); // Titre de la fenêtre
        stage.setScene(scene); // Appliquer la scène à la fenêtre
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - 800) / 2);
        stage.setY((screenBounds.getHeight() - 600) / 2);
        stage.show(); // Afficher la fenêtre
    }

    public static void main(String[] args) {
        launch(args); // Lancer l'application
    }
}


