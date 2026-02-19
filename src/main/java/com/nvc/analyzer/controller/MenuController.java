package com.nvc.analyzer.controller;

import com.nvc.analyzer.App;
import javafx.fxml.FXML;
import java.io.IOException;

/**Controller for the menu view. */
public class MenuController {

    /**Triggered by the Create New Analysis button click, this method changes the current root to analysis view. */
    @FXML
    private void switchToCreate() throws IOException {
        App.setRoot("analysis_view");
    }

    /**Triggered by the Browse History button click, this method changes the current root to history view. */
    @FXML
    private void switchToBrowse() throws IOException {
        App.setRoot("history_view");
    }
}
