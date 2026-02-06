package com.nvc.analyzer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application{
    
    @Override
    public void start(Stage stage) {
        var label = new Label("Welcome to NVC Analyzer");

        var sceneRoot = new StackPane(label);

        var scene = new Scene(sceneRoot, 640, 480);

        stage.setScene(scene);
        stage.setTitle("NVC Analyzer");
        stage.show();
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch();
    }
}
