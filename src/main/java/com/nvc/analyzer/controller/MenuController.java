package com.nvc.analyzer.controller;

import com.nvc.analyzer.App;
import javafx.fxml.FXML;
import java.io.IOException;

public class MenuController {

    @FXML
    private void switchToCreate() throws IOException {
        App.setRoot("analysis_view");
    }

    @FXML
    private void switchToBrowse() throws IOException {
        System.out.println("Browse feature coming soon...");
        // App.setRoot("list_view"); 
    }
}
