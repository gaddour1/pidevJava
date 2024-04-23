module projet1 {

    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens tests to javafx.fxml;
    opens controllers to javafx.fxml; // Open the Controlleur package
    exports tests; // Export your main package
    exports controllers;


    opens entities to javafx.base;


}
