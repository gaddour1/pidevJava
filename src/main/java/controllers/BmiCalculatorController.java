package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BmiCalculatorController {

    @FXML
    private Label adviceLabel;

    @FXML
    private TextField heightField;

    @FXML
    private Label resultLabel;

    @FXML
    private TextField weightField;

    @FXML
    void calculateBMI(ActionEvent event) {
        try {
            double weight = Double.parseDouble(weightField.getText());
            double height = Double.parseDouble(heightField.getText());

            if (height <= 0 || weight <= 0) {
                resultLabel.setText("Entrez des valeurs valides !");
                adviceLabel.setText("");
                return;
            }

            double bmi = weight / (height * height);
            resultLabel.setText(String.format("Votre IMC est : %.2f", bmi));

            if (bmi < 18.5) {
                adviceLabel.setText("Vous êtes en sous-poids.");
            } else if (bmi >= 18.5 && bmi <= 24.9) {
                adviceLabel.setText("Vous avez un poids normal.");
            } else if (bmi >= 25 && bmi <= 29.9) {
                adviceLabel.setText("Vous êtes en surpoids.");
            } else {
                adviceLabel.setText("Obésité constatée.");
                double idealWeight = 24.9 * (height * height);
                double weightToLose = weight - idealWeight;
                adviceLabel.setText(adviceLabel.getText() + " Vous devriez perdre environ " + String.format("%.2f", weightToLose) + " kg pour atteindre un IMC de 24.9 (poids normal).");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Erreur de saisie. Veuillez entrer des nombres valides.");
            adviceLabel.setText("");
        }
    }

    }

