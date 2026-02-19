package com.nvc.analyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * A javafx application to analyze conflicts using the NVC principles.
 * @version 1.0
 * @since 2026-02-19
 * @author Ondřej Kočár
 * @see <a href="https://en.wikipedia.org/wiki/Nonviolent_Communication">NVC on Wikipedia</a>
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        scene = new Scene(loadFXML("menu_view"), 1024, 768);
        stage.setScene(scene);
        stage.setTitle("NVC Analyzer");
        stage.show();
    }

    /**
     * Changes the root of the applications to different views.
     * @param fxml  Name of the fxml view that is to be set as the root
     * @throws IOException
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/com/nvc/analyzer/view/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}
