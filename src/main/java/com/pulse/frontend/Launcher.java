// src/main/java/com/pulse/frontend/Launcher.java
package com.pulse.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
//import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/RegisterStudentResults.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Pulse EduDataIntegrator");
        stage.setScene(scene);
        stage.show();
        
    }
    public static void main(String[] args) {
        launch(args);
    }
}
