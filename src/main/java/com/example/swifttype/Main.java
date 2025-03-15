package com.example.swifttype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class that launches the SwiftType application.
 * This class loads the graphical interface and configures the main window.
 *
 * @author Daniel Fernando Vallejo Cabrera - 2343154
 * @version 1.0
 */
public class Main extends Application {

    /**
     * Main method that launches the application.
     *
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }

    /**
     * Method executed when the application starts.
     * Loads the graphical interface from the FXML file and configures the main window.
     *
     * @param primaryStage The primary stage of the application.
     * @throws Exception If an error occurs while loading the graphical interface.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the graphical interface from the FXML file
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/swifttype/view/main.fxml"));

        // Configure the main window
        primaryStage.setTitle("SwiftType (V1.0) Eclipsed Sun");
        primaryStage.setScene(new Scene(root, 400, 400)); // Adjust the window size
        primaryStage.show();
    }
}