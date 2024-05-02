package controllers;

import entities.visite;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.layout.BorderPane;
import services.Servicevisite;

import java.sql.SQLException;
import java.util.List;

public class Statistiques {


    @FXML
    private PieChart pieChart;
    @FXML
    void refresh(ActionEvent event) {
        new Thread(() -> {
            ObservableList<PieChart.Data> newData = FXCollections.observableArrayList(serviceVisite.getMostUsedTreatments());
            Platform.runLater(() -> {
                pieChart.getData().clear();
                pieChart.getData().addAll(newData);
            });
        }).start();

    }
    private Servicevisite serviceVisite = new Servicevisite();

    @FXML
    public void initialize() {
        loadPieChartData();

    }

    private void loadPieChartData() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(serviceVisite.getMostUsedTreatments());
        pieChartData.forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), ": ", data.pieValueProperty()
                        )
                )
        );
        pieChart.getData().clear();
        pieChart.getData().addAll(pieChartData);
    }

    }


