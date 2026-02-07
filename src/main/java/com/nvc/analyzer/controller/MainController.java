package com.nvc.analyzer.controller;

import com.nvc.analyzer.model.NvcProcess;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class MainController {

    @FXML
    private TextField observationField;

    @FXML
    private TextField feelingField;

    @FXML
    private TextField needField;

    @FXML
    private TextField requestField;

    @FXML
    private TextArea resultArea;

    @FXML
    public void initialize() {
        System.out.println("Controller initialized!");
    }

    @FXML
    private void handleAnalyze() {
        String obs = observationField.getText();
        String feel = feelingField.getText();
        String need = needField.getText();
        String req = requestField.getText();

        NvcProcess process = new NvcProcess();
        process.setObservation(obs);
        process.setFeeling(feel);
        process.setNeed(need);
        process.setRequest(req);

        // Standard NVC Format: "When I see [Observation], I feel [Feeling] because I need [Need]. Would you be willing to [Request]?"
        
        StringBuilder result = new StringBuilder();
        
        // Checking if fields are empty
        if (obs.isBlank() || feel.isBlank() || need.isBlank() || req.isBlank()) {
            result.append("Please fill in all 4 steps to complete the NVC process.");
        } else {
            result.append("Here is your NVC Statement:\n\n");
            result.append(String.format(
                "\"When I see/hear %s,\n I feel %s,\n because I need %s.\n Would you be willing to %s?\"", 
                process.getObservation(), 
                process.getFeeling(), 
                process.getNeed(), 
                process.getRequest()
            ));
        }

        resultArea.setText(result.toString());
    }
}