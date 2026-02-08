package com.nvc.analyzer.controller;

import java.io.IOException;
import java.util.List;

import com.nvc.analyzer.App;
import com.nvc.analyzer.model.NvcProcess;
import com.nvc.analyzer.model.NvcValidator;
import com.nvc.analyzer.service.DataService;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AnalysisController {

    @FXML private TextField observationField;
    @FXML private TextField feelingField;
    @FXML private TextField needField;
    @FXML private TextField requestField;
    @FXML private TextArea resultArea;

    private final DataService dataService = new DataService();
    private final NvcValidator validator = new NvcValidator();

    private NvcProcess currentProcess;

    public void setProcess(NvcProcess process) {
        this.currentProcess = process;
        
        observationField.setText(process.getObservation());
        feelingField.setText(process.getFeeling());
        needField.setText(process.getNeed());
        requestField.setText(process.getRequest());
    }

    @FXML
    private void handleAnalyze() {
        String obs = observationField.getText();
        String feel = feelingField.getText();
        String need = needField.getText();
        String req = requestField.getText();

        List<String> obsWarnings = validator.analyzeObservation(obs);
        List<String> feelWarnings = validator.analyzeFeeling(feel);

        NvcProcess process = new NvcProcess();
        process.setObservation(obs);
        process.setFeeling(feel);
        process.setNeed(need);
        process.setRequest(req);

        // Standard NVC Format: "When I see [Observation], I feel [Feeling] because I need [Need]. Would you be willing to [Request]?"
        
        StringBuilder result = new StringBuilder();

        if (!obsWarnings.isEmpty() || !feelWarnings.isEmpty()) {
            result.append("ANALYSIS & TIPS\n");
            for (String w : obsWarnings) result.append(w).append("\n");
            for (String w : feelWarnings) result.append(w).append("\n");
            result.append("\n"); 
        }
        
        if (obs.isBlank() || feel.isBlank() || need.isBlank() || req.isBlank()) {
            result.append("Please fill in all 4 steps to complete the NVC process!");
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

    @FXML
    private void handleSave() {
        String obs = observationField.getText();
        String feel = feelingField.getText();
        String need = needField.getText();
        String req = requestField.getText();

        if (obs.isBlank() || feel.isBlank() || need.isBlank() || req.isBlank()) {
            showAlert("Missing Info", "Please fill in all fields before saving.");
            return;
        }

        List<NvcProcess> allProcesses = dataService.loadProcesses();

        if (currentProcess == null) {
            NvcProcess newProcess = new NvcProcess();
            newProcess.setObservation(obs);
            newProcess.setFeeling(feel);
            newProcess.setNeed(need);
            newProcess.setRequest(req);
            
            allProcesses.add(newProcess);
            showAlert("Success", "Your NVC analysis has been saved succesfully!");
            
        } else {
            for (int i = 0; i < allProcesses.size(); i++) {
                if (allProcesses.get(i).getId().equals(currentProcess.getId())) {
                    allProcesses.get(i).setObservation(obs);
                    allProcesses.get(i).setFeeling(feel);
                    allProcesses.get(i).setNeed(need);
                    allProcesses.get(i).setRequest(req);
                    break; 
                }
            }
            showAlert("Success", "The analysis has been updated successfully!");
        }
        
        dataService.saveProcesses(allProcesses);
            
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("menu_view");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}