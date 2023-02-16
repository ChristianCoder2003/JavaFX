package com.example.pocketsound;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carga el FXML
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        // Crea una escena y le añade atributos
        Scene scene = new Scene(fxmlLoader.load(), 750, 500);
        primaryStage.setTitle("Hello!");
        primaryStage.setScene(scene);
        // Le pasa al controlador la escena
        ((HelloController) fxmlLoader.getController()).setStage(primaryStage);
        // Muestra la escena
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}