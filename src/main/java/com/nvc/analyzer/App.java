package com.nvc.analyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/nvc/analyzer/view/analysis_view.fxml"));
        Parent root = loader.load();

        scene = new Scene(root, 600, 700); // Width: 600, Height: 700
        
        stage.setScene(scene);
        stage.setTitle("NVC Analyzer");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
