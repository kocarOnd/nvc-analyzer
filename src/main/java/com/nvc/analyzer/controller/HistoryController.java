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

/**
 * Controller for the history view.
 * Shows saved items and allows their edits.
 */
public class HistoryController {

    @FXML
    private ListView<NvcProcess> historyList;

    private final DataService dataService = new DataService();

    /**Loads the data and organizes the text of the cells in the list view */
    @FXML
    public void initialize() {
        ObservableList<NvcProcess> data = FXCollections.observableArrayList(dataService.loadProcesses());
        historyList.setItems(data);

        //AI has suggested the following piece of code to me and although I understand it, I do not think I would have been able to come up with it myself
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

    /**
     * Triggered by the Edit button click, this method loads the selected process to analyse view and switches to it.
     * If no cell is selected, shows an alert instead.
     */
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

    /**
     * Triggered by the Return to Menu button click, this method changes the current root to menu view.
     */
    @FXML
    private void goBack() throws IOException {
        App.setRoot("menu_view");
    }
}
