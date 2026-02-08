package com.nvc.analyzer.controller;

import com.nvc.analyzer.App;
import com.nvc.analyzer.model.NvcProcess;
import com.nvc.analyzer.service.DataService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;

public class HistoryController {

    @FXML
    private ListView<NvcProcess> historyList;

    private final DataService dataService = new DataService();

    @FXML
    public void initialize() {
        ObservableList<NvcProcess> data = FXCollections.observableArrayList(dataService.loadProcesses());
        historyList.setItems(data);

        historyList.setCellFactory(param -> new ListCell<NvcProcess>() {
            @Override
            protected void updateItem(NvcProcess item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    // Display a summary line in the list
                    setText("Observation: " + item.getObservation() + " | Feeling: " + item.getFeeling());
                }
            }
        });
    }

    @FXML
    private void handleEdit() throws IOException {
        NvcProcess selected = historyList.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Nothing selected!");
            alert.setContentText("Please select an item from the list to edit.");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(App.class.getResource("/com/nvc/analyzer/view/analysis_view.fxml"));
        Parent root = loader.load();

        AnalysisController controller = loader.getController();
        controller.setProcess(selected);

        Stage stage = (Stage) historyList.getScene().getWindow();
        stage.getScene().setRoot(root);
    }

    @FXML
    private void goBack() throws IOException {
        App.setRoot("menu_view");
    }
}
