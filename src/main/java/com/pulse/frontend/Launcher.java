// src/main/java/com/pulse/frontend/Launcher.java
package com.pulse.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Launcher class to start the JavaFX application
 */
public class Launcher extends Application {
    /**
     * Starts the JavaFX application by loading the main FXML layout.
     * @param stage The primary stage for this application
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/RegisterStudentResults.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Pulse EduDataIntegrator");
        stage.setScene(scene);
        stage.show();
        
    }

    /**
     * Main method to launch the JavaFX application.
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
