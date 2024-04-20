package org.example.amine.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.example.amine.entities.event;
import org.example.amine.services.SessionService;
import org.example.amine.services.serviceEvenement;
import org.example.amine.services.serviceFavoris;
import org.example.amine.services.serviceParticipation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class cardsController {
    @FXML
    private Button refreshButton;

    @FXML
    private VBox eventsContainer;

    private final serviceEvenement eventService = new serviceEvenement();

    @FXML
    private void initialize() throws SQLException {
        populateEventCards();
    }

    @FXML
    void setupRefreshButton(ActionEvent event) {
        refreshButton.setOnAction(actionEvt -> { // Use a different variable name such as 'actionEvt'
            try {
                populateEventCards(); // Call the method to refresh event cards
            } catch (SQLException ex) { // Use a different name such as 'ex' for the catch block
                ex.printStackTrace();
            }
        });
    }



    private void populateEventCards() throws SQLException {
        List<event> events = eventService.getAll();
        eventsContainer.getChildren().clear(); // Clear existing cards

        FlowPane flowPane = new FlowPane(Orientation.HORIZONTAL, 10, 10); // Create a FlowPane with horizontal orientation and spacing of 10
        flowPane.setAlignment(Pos.CENTER); // Set alignment to center
        flowPane.setPrefWrapLength(300); // Set preferred wrap length

        for (event ev : events) {
            flowPane.getChildren().add(createEventCard(ev)); // Add cards to FlowPane
        }

        eventsContainer.getChildren().add(flowPane); // Add FlowPane to the VBox container
    }


    private AnchorPane createEventCard(event ev) throws SQLException {
        AnchorPane eventCard = new AnchorPane();
        eventCard.getStyleClass().add("event-card");


        ImageView imageView = new ImageView();
        String imageUrl = ev.getImage(); // Assuming getImage() returns the URL of the image
        if (imageUrl != null && !imageUrl.isEmpty()) {
            imageView.setImage(new Image(imageUrl));
        } else {
            imageView.setImage(new Image("images/logo.png")); // Placeholder image
        }
        imageView.setFitHeight(100); // Set the preferred height
        imageView.setFitWidth(100); // Set the preferred width
        imageView.setLayoutX(14); // Set the X position
        imageView.setLayoutY(14); // Set the Y position

        Label titleLabel = new Label("Event Title: " + ev.getTitre());
        titleLabel.setLayoutX(50);
        titleLabel.setLayoutY(imageView.getLayoutY() + imageView.getFitHeight() + 10); // Position below the image
        titleLabel.getStyleClass().add("event-title");

        Label dateLabel = new Label("Event Date: " + ev.getDate());
        dateLabel.setLayoutX(14);
        dateLabel.setLayoutY(titleLabel.getLayoutY() + 20); // Position below the title
        dateLabel.getStyleClass().add("event-details");

        Label locationLabel = new Label("Location: " + ev.getLieu());
        locationLabel.setLayoutX(14);
        locationLabel.setLayoutY(dateLabel.getLayoutY() + 20); // Position below the date
        locationLabel.getStyleClass().add("event-details");

        Label descriptionLabel = new Label("Description: " + ev.getDescription());
        descriptionLabel.setLayoutX(14); // Align with other labels
        descriptionLabel.setLayoutY(locationLabel.getLayoutY() + 20); // Position below the location
        descriptionLabel.getStyleClass().add("event-description");

        Button detailsButton = new Button("Details");
        detailsButton.setLayoutX(14);
        detailsButton.setLayoutY(descriptionLabel.getLayoutY() + 30); // Position below the description
        detailsButton.getStyleClass().add("event-button");
        detailsButton.setOnAction(actionEvent -> showEventDetails(ev));

        Button participateButton = new Button("Participate");
        participateButton.setLayoutX(detailsButton.getLayoutX() + detailsButton.getWidth() +70);
        participateButton.setLayoutY(215);
        participateButton.getStyleClass().add("event-button");

        Button favorisButton = new Button("Add to Favorites");
        favorisButton.setLayoutX(participateButton.getLayoutX() + participateButton.getWidth() +90); // Position next to the participate buttont X position based on the width of the detailsButton
        favorisButton.setLayoutY(215); // Same Y position as the detailsButton
        participateButton.setOnAction(actionEvent -> handleParticipation(actionEvent, ev.getId()));
        favorisButton.getStyleClass().add("event-button");


        serviceFavoris favorisService = new serviceFavoris();
        boolean isFavorite = favorisService.isFavorite(SessionService.getInstance().getSignedInUserId(), ev.getId());
        updateButtonStyle(favorisButton, isFavorite);

        favorisButton.setOnAction(actionEvent -> toggleFavorite(actionEvent, ev.getId(), favorisButton));
        // Add the ImageView to the event card
        eventCard.getChildren().add(imageView);


        eventCard.getChildren().addAll(titleLabel, dateLabel, locationLabel, detailsButton, participateButton, favorisButton);

        return eventCard;
    }
    private void showEventDetails(event ev) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Event Details");
        alert.setHeaderText(ev.getTitre()); // Event title as header
        // Set content text with all event details
        alert.setContentText("Title: " + ev.getTitre() +
                "\nDate: " + ev.getDate() +
                "\nLocation: " + ev.getLieu() +
                "\nDescription: " + ev.getDescription());
        alert.showAndWait();
    }

    private void toggleFavorite(ActionEvent actionEvent, int eventId, Button favorisButton) {
        int userId = SessionService.getInstance().getSignedInUserId();
        if (userId == -1) {
            System.out.println("No signed-in user found.");
            return;
        }


        serviceFavoris favorisService = new serviceFavoris();
        try {
            boolean isFavorite = favorisService.isFavorite(userId, eventId);
            if (isFavorite) {
                favorisService.removeFromFavorites(userId, eventId);
            } else {
                favorisService.saveToFavoris(userId, eventId, LocalDate.now());
            }
            updateButtonStyle(favorisButton, !isFavorite);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateButtonStyle(Button button, boolean isFavorite) {
        if (isFavorite) {
            button.getStyleClass().add("favoris-active");
            button.setText("Remove from Favorites");
        } else {
            button.getStyleClass().removeAll("favoris-active");
            button.setText("Add to Favorites");
        }
    }

    @FXML
    void handleParticipation(ActionEvent actionEvent, int eventId) {
        int userId = SessionService.getInstance().getSignedInUserId();

        if (userId == -1) {
            System.out.println("No signed-in user found.");
            return;
        }

        serviceParticipation participationService = new serviceParticipation();
        try {
            participationService.saveParticipation(userId, eventId);
            System.out.println("Participation saved successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addfavoris(ActionEvent actionEvent, int eventId) {
        Button source = (Button) actionEvent.getSource();
        LocalDate currentDate = LocalDate.now();
        int userId = SessionService.getInstance().getSignedInUserId();

        if (userId == -1) {
            System.out.println("No signed-in user found.");
            return;
        }

        serviceFavoris favorisService = new serviceFavoris();
        try {
            favorisService.saveToFavoris(userId, eventId, currentDate);
            source.setDisable(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
