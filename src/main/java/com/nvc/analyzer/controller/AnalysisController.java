package com.nvc.analyzer.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.nvc.analyzer.App;
import com.nvc.analyzer.model.NvcProcess;
import com.nvc.analyzer.model.NvcValidator;
import com.nvc.analyzer.service.DataService;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;

/**
 * Controller for the NVC Analysis view.
 * Handles user input for the 4-step NVC process, validates the text 
 * against predefined rules, and displays feedback or formatted statements.
 */
public class AnalysisController {

    @FXML private TextField observationField;
    @FXML private TextField feelingField;
    @FXML private TextField needField;
    @FXML private TextField requestField;
    @FXML private Button analyseBtn;
    @FXML private TextArea resultArea;

    private final DataService dataService = new DataService();
    private NvcValidator observationValidator;
    private NvcValidator feelingValidator;
    private NvcValidator needValidator;
    private NvcValidator requestValidator;

    private NvcProcess currentProcess;

    /**
     * Puts an existing NVC process into the controller and populates the text fields with its data. 
     * @param process The NvcProcess object containing the data to edit.
     */
    public void setProcess(NvcProcess process) {
        this.currentProcess = process;
        
        observationField.setText(process.getObservation());
        feelingField.setText(process.getFeeling());
        needField.setText(process.getNeed());
        requestField.setText(process.getRequest());
    }

    /**
     * Loads the observation, feeling, need and request validator
     * The function loads them concurrently to avoid freezing the screen.
    */
    @FXML
    public void initialize() {
        CompletableFuture<NvcValidator> obsFuture = loadValidatorAsync("observation", "com/nvc/analyzer/rule/observation_rules.json");
        CompletableFuture<NvcValidator> feelFuture = loadValidatorAsync("feeling", "com/nvc/analyzer/rule/feeling_rules.json");
        CompletableFuture<NvcValidator> needFuture = loadValidatorAsync("need", "com/nvc/analyzer/rule/need_rules.json");
        CompletableFuture<NvcValidator> reqFuture = loadValidatorAsync("request", "com/nvc/analyzer/rule/request_rules.json");

        // I have asked the AI for suggestions which classes to use, therefore I am using CompletableFuture and Platform,
        // althought it is my first time handling them
        CompletableFuture.allOf(obsFuture, feelFuture, needFuture, reqFuture)
            .thenAccept(v -> {
                Platform.runLater(() -> {
                    try {
                        this.observationValidator = obsFuture.join();
                        this.feelingValidator = feelFuture.join();
                        this.needValidator = needFuture.join();
                        this.requestValidator = reqFuture.join();

                        analyseBtn.setDisable(false);
                    
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            })
            .exceptionally(ex -> {
                Platform.runLater(() -> {
                    System.err.println("An error occurred while loading the validators: " + ex.getMessage());
                    ex.printStackTrace();
                });
                return null;
            });
    }

    private CompletableFuture<NvcValidator> loadValidatorAsync(String type, String resourcePath) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return createValidator(type, resourcePath);
            } catch (IOException e) {
                throw new RuntimeException("An error when loading the rules for: " + type, e);
            }
        });
    }

    private NvcValidator createValidator(String type, String resourcePath) throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath);
        if (is == null) {
            throw new IOException("Resource not found: " + resourcePath);
        }
        return new NvcValidator(type, is);
    }

    /**
     * Triggered by the Analyze button click, this method loads the text from all the fields and shows respective analysis in resultArea
     * It analyses the text from every field using the appropriate validator and shows 
     * the result in resultArea. If any of the fields is empty, it shows a warning instead.
     */
    @FXML
    private void handleAnalyze() {
        String obs = observationField.getText();
        String feel = feelingField.getText();
        String need = needField.getText();
        String req = requestField.getText();

        List<String> obsWarnings = observationValidator.analyze(obs);
        List<String> feelWarnings = feelingValidator.analyze(feel);
        List<String> needWarnings = needValidator.analyze(need);
        List<String> reqWarnings = requestValidator.analyze(req);

        NvcProcess process = new NvcProcess();
        process.setObservation(obs);
        process.setFeeling(feel);
        process.setNeed(need);
        process.setRequest(req);

        // Standard NVC Format: "When I see [Observation], I feel [Feeling] because I need [Need]. Would you be willing to [Request]?"
        
        StringBuilder result = new StringBuilder();

        if (obs.isBlank() || feel.isBlank() || need.isBlank() || req.isBlank()) {
            result.append("Please fill in all 4 steps to complete the NVC process!");
        } else if (!obsWarnings.isEmpty() || !feelWarnings.isEmpty() || !needWarnings.isEmpty() || !reqWarnings.isEmpty()) {
            result.append("ANALYSIS & TIPS\n");
            result.append("\nObservation: \n");
            for (String w : obsWarnings) result.append(w).append("\n");
            result.append("\nFeeling: \n");
            for (String w : feelWarnings) result.append(w).append("\n");
            result.append("\nNeed: \n");
            for (String w : needWarnings) result.append(w).append("\n");
            result.append("\nRequest: \n");
            for (String w : reqWarnings) result.append(w).append("\n");
            result.append("\n"); 
        } else {
            result.append("There were no problems detected in your statement!\n");
        }

        resultArea.setText(result.toString());
    }

    /**
     * Triggered by the Write Out button click, this method loads the text from all the fields and shows the full statement in resultArea
     * If any of the fields is empty, shows a warning instead.
     */
    @FXML
    private void handleWrite() {
        String obs = observationField.getText();
        String feel = feelingField.getText();
        String need = needField.getText();
        String req = requestField.getText();

        NvcProcess process = new NvcProcess();
        process.setObservation(obs);
        process.setFeeling(feel);
        process.setNeed(need);
        process.setRequest(req);
        
        StringBuilder result = new StringBuilder();
        
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

    /**
     * Triggered by the Save button click, this method loads text from all the fields, saves it to nvc_data.json
     * If any of the fields is empty, shows an alert instead. An alert is shown on a succesful save as well.
     */
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

    /**
     * Triggered by the Return to Menu button click, this method changes the current root to menu view.
     */
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