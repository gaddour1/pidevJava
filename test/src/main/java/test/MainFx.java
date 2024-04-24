package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader= new FXMLLoader(getClass().getResource("/login.fxml"));
        Parent root=loader.load();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("ajout");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
