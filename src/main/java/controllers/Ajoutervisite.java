package controllers;

import entities.traitement;
import entities.visite;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import services.Servicetraitement;
import services.Servicevisite;
import tests.HelloApplication;
import controllers.* ;
import utils.PDFGenerator;
import utils.SchedulerService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

public class Ajoutervisite {
    @FXML
    private ComboBox<traitement> combotrait;

    @FXML
    private DatePicker datefx;

    @FXML
    private TextField heurev;
    @FXML
    private TextField cout;


    @FXML
    private TextField lieuv;
    private Servicetraitement servicetraitement;
    private traitement selectedTraitement;
    private Servicevisite servicevisite;
    private visite visiteToUpdate;
    @FXML
    private TextField email;
    private Set<String> regionsTunisie = Set.of(
            "ariana", "beja", "ben arous", "bizerte", "gabes", "gafsa",
            "jendouba", "kairouan", "kasserine", "kebili", "kef", "mahdia",
            "manouba", "medenine", "monastir", "nabeul", "sfax", "sidi bouzid",
            "siliana", "sousse", "tataouine", "tozeur", "tunis", "zaghouan"
    );



    public Ajoutervisite() {
        servicetraitement = new Servicetraitement();
        servicevisite = new Servicevisite();
    }
    @FXML
    void ajouterv(ActionEvent event) throws SQLException {
        boolean nouvelleVisiteAjoutée = false;

        traitement selectedTraitement = combotrait.getValue();
        if (selectedTraitement == null) {
            showAlert("Erreur", "Veuillez sélectionner un traitement.");
            return;
        }

        if (datefx.getValue() == null) {
            showAlert("Erreur", "Veuillez entrer une date.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dateString = datefx.getValue().format(formatter);
        LocalDate dateChoisie = LocalDate.parse(dateString, formatter);
        if (dateChoisie.isBefore(LocalDate.now())) {
            showAlert("Erreur", "La date doit être supérieure à la date d'aujourd'hui.");
            return;
        }

        String lieu = lieuv.getText().trim().toLowerCase();
        if (!regionsTunisie.contains(lieu)) {
            showAlert("Erreur", "Le lieu doit être une région valide en Tunisie.");
            return;
        }

        String heure = heurev.getText().trim();
        if (!heure.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]")) {
            showAlert("Erreur", "L'heure doit être au format HH:mm.");
            return;
        }

        visite newVisite = new visite(dateString, lieu, heure, selectedTraitement);
        try {
            servicevisite.ajouter(newVisite);
            nouvelleVisiteAjoutée = true;

            showInformationAlert("SUCCÈS", "Visite ajoutée avec succès");
        } catch (SQLException e) {
            showAlert("Erreur", "Échec de l'ajout de la visite : " + e.getMessage());

        }
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(heurev.getText(), timeFormatter);
        LocalDateTime dateTime = LocalDateTime.of(datefx.getValue(), time);
        long delay = Duration.between(LocalDateTime.now(), dateTime.minusHours(2)).toMillis();

        if (delay > 0) {
            new SchedulerService().scheduleEmailReminder(
                    email.getText(),
                    "Rappel de visite",
                    "Vous avez un rendez-vous prévu à " + heurev.getText() + " le " + datefx.getValue().toString(),
                    delay
            );}
        else {
            showAlert("Erreur", "L'heure de rappel est déjà passée.");
        }
/*
        if (nouvelleVisiteAjoutée) {
            try {
                visite derniereVisite = servicevisite.getDerniereVisite();
                double cout = 0.0;
                if (derniereVisite != null) {
                    cout = calculerCoutVisite(derniereVisite.getLieu(), derniereVisite.getTraitement().getCout());
                }
                PDFGenerator.generateVisitReport(newVisite, cout, "C:\\Users\\USER\\Downloads\\visitespdf/visit_report.pdf");
                showAlert("Succès", "Le rapport PDF a été créé avec succès.");
            } catch (Exception e) {
                showAlert("Erreur", "Erreur lors de la génération du rapport PDF : " + e.getMessage());
            }
            }*/

        }

    private void showInformationAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    void handleGeneratePdf(ActionEvent event) {
        try {
            visite latestVisit = servicevisite.getDerniereVisite();
            if (latestVisit != null) {
                double calculatedCost = calculerCoutVisite(latestVisit.getLieu(), latestVisit.getTraitement().getCout());
                String pdfPath = "C:\\Users\\USER\\Downloads\\visitespdf/visit_report_" + latestVisit.getId() + ".pdf";
                PDFGenerator.generateVisitReport(latestVisit, calculatedCost, pdfPath);
                showInformationAlert("Succès", "PDF généré avec succès  ");
            } else {
                showAlert("Information", "Aucune visite récente trouvée.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Erreur lors de la génération du PDF : " + e.getMessage());
        }


    }

    @FXML
    void afficherv(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/projetjava/Affichervisite.fxml"));
        try {
            datefx.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur de Chargement", "Impossible de charger la vue.");
        }

    }

    private void showAlert(Alert.AlertType alertType, String erreurDeChargement, String s) {

    }

    private void showAlert( String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();

    }

        @FXML
    private void initialize() {
        loadTraitementData();
            combotrait.valueProperty().addListener((obs, oldVal, newVal) -> calculateAndDisplayCost());
            lieuv.textProperty().addListener((obs, oldVal, newVal) -> calculateAndDisplayCost());

        }
    private void calculateAndDisplayCost() {
        if (combotrait.getValue() != null && lieuv.getText() != null && !lieuv.getText().isEmpty()) {
            try {
                double coutCalculé = calculerCoutVisite(lieuv.getText(), combotrait.getValue().getCout());
                cout.setText(String.format("%.2f TND", coutCalculé));
            } catch (Exception e) {
                cout.setText("Erreur de calcul");
                showAlert("Erreur", "Erreur lors du calcul du coût : " + e.getMessage());
            }
        }}

    private void loadTraitementData() {
        servicetraitement = new Servicetraitement();  // S'assurer que ceci est bien initialisé
        ObservableList<traitement> traitements = FXCollections.observableArrayList();

        try {
            traitements.addAll(servicetraitement.afficher());
            combotrait.setItems(traitements);
            combotrait.setConverter(new StringConverter<traitement>() {
                @Override
                public String toString(traitement object) {
                    return object != null ? object.getNom() : "";
                }

                @Override
                public traitement fromString(String string) {
                    return traitements.stream().filter(item -> item.getNom().equals(string)).findFirst().orElse(null);
                }
            });
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de chargement");
            alert.setContentText("Impossible de charger les traitements : " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void initDataForUpdate(visite visite) {
         visiteToUpdate = visite;

        // Populate form fields with event data
        if (visite.getDate() != null && !visite.getDate().isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(visite.getDate(), formatter);
            datefx.setValue(localDate);
        } else {
            datefx.setValue(null); // Set to null or to a default value if necessary
        }
        lieuv.setText(String.valueOf(visite.getLieu()));
        heurev.setText(String.valueOf(visite.getHeure()));
        combotrait.getSelectionModel().select(visite.getTraitement());

    }
    public void loadLatestVisitDetails() {
        try {
            visite derniereVisite = servicevisite.getDerniereVisite();
            if (derniereVisite != null) {
                double coutcalculé = calculerCoutVisite(derniereVisite.getLieu(), derniereVisite.getTraitement().getCout());
                cout.setText(String.format("%.2f", coutcalculé) + " TND");
            } else {
                cout.setText("Pas de visites précédentes.");
            }
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors de la récupération des données : " + e.getMessage());
        }
    }

    private double calculerCoutVisite(String lieu, double coutTraitement) {
        double basePrice = coutTraitement; // Utiliser le coût du traitement comme prix de base
        double distanceFactor = 1.0; // Multiplicateur par défaut

        // Convertir le nom du lieu en minuscules pour la comparaison


        switch (lieu.toLowerCase()) {
            case "ariana": distanceFactor = 1.0; break;
            case "beja": distanceFactor = 1.4; break;
            case "ben arous": distanceFactor = 1.1; break;
            case "bizerte": distanceFactor = 1.5; break;
            case "gabes": distanceFactor = 5.1; break;
            case "gafsa": distanceFactor = 5; break;
            case "jendouba": distanceFactor = 1.8; break;
            case "kairouan": distanceFactor = 1.7; break;
            case "kasserine": distanceFactor = 2.3; break;
            case "kebili": distanceFactor = 6; break;
            case "kef": distanceFactor = 1.6; break;
            case "mahdia": distanceFactor = 2; break;
            case "manouba": distanceFactor = 1.0; break;
            case "medenine": distanceFactor = 6.5; break;
            case "monastir": distanceFactor = 3; break;
            case "nabeul": distanceFactor = 1.2; break;
            case "sfax": distanceFactor = 3.7; break;
            case "sidi bouzid": distanceFactor = 4.8; break;
            case "siliana": distanceFactor = 1.7; break;
            case "sousse": distanceFactor = 3; break;
            case "tataouine": distanceFactor = 3.2; break;
            case "tozeur": distanceFactor = 5.2; break;
            case "tunis": distanceFactor = 1.0; break;
            case "zaghouan": distanceFactor = 1.3; break;
            default: distanceFactor = 1.5; // Si la région n'est pas reconnue, appliquer un multiplicateur moyen
        }

        return basePrice * distanceFactor;
    }



}

