package com.example.swifttype;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Clase principal que inicia la aplicación de escritura rápida.
 * Esta clase carga la interfaz gráfica y configura la ventana principal.
 */
public class Main extends Application {

    /**
     * Método principal que inicia la aplicación.
     *
     */
    public static void main(String[] args) {
        launch(args); // Inicia la aplicación JavaFX
    }

    /**
     * Método que se ejecuta al iniciar la aplicación.
     * Carga la interfaz gráfica desde el archivo FXML y configura la ventana principal.
     *
     * @param primaryStage El escenario principal de la aplicación.
     * @throws Exception Si ocurre un error al cargar la interfaz gráfica.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar la interfaz gráfica desde el archivo FXML
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/swifttype/view/main.fxml"));

        // Configurar la ventana principal
        primaryStage.setTitle("SwiftType - Escritura Rápida");
        primaryStage.setScene(new Scene(root, 400, 300));
        primaryStage.show();
    }
}