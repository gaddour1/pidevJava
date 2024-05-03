package controllers;

import entities.traitement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import services.Servicetraitement;

import java.sql.SQLException;
import java.util.List;

public class RechercheTraitement {

    @FXML
    private TextArea resultsArea;

    @FXML
    private TextField symptomsField;
    private Servicetraitement serviceTraitement;

    public RechercheTraitement() {
        serviceTraitement = new Servicetraitement(); // Assurez-vous que ce service est bien connecté à votre base de données
    }

    @FXML
    void handleSearch(ActionEvent event) {
        String keywords = symptomsField.getText();
        if (keywords.isEmpty()) {
            resultsArea.setText("Veuillez entrer des mots-clés pour les symptômes.");
            return;
        }

        try {
            List<traitement> treatments = serviceTraitement.findTreatmentsByKeywords(keywords);
            if (treatments.isEmpty()) {
                resultsArea.setText("Aucun traitement trouvé pour les mots-clés fournis.");
            } else {
                StringBuilder builder = new StringBuilder("Les traitements qui conviennent avec vos symptômes sont :\n");
                for (traitement t : treatments) {
                    builder.append("Nom du traitement: ").append(t.getNom())
                            .append("\nNotes: ").append(t.getNotes())
                            .append("\n\n"); // Ajoute deux nouvelles lignes pour séparer les entrées
                }
                resultsArea.setText(builder.toString());
            }
        } catch (SQLException e) {
            resultsArea.setText("Erreur lors de la recherche des traitements: " + e.getMessage());
        }
    }
    }
