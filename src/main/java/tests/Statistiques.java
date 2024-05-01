package tests;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent; // Import correct pour la classe Parent

import java.net.URL;

public class Statistiques extends Application {


    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/projetjava/Statistiques.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root);
            URL cssURL = getClass().getResource("/resources/style.css");
            if (cssURL != null) {
                scene.getStylesheets().add(cssURL.toExternalForm());
            } else {
                System.out.println("Cannot find 'style.css'. Ensure it's in the correct directory.");
            }
            stage.setTitle("Statistiques des Traitements");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
