// src/main/java/com/pulse/frontend/Launcher.java
package com.pulse.frontend;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Launcher extends Application {
    @Override
    public void start(Stage stage) {
        stage.setTitle("Pulse EduDataIntegrator");
        stage.setScene(new Scene(new Label("Hello, Pulse!"), 420, 200));
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
