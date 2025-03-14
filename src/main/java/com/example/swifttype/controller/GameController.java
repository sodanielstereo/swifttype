package com.example.swifttype.controller;

import com.example.swifttype.model.GameModel;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Controlador principal del juego. Maneja la interacción entre la vista y el modelo.
 */
public class GameController {
    @FXML
    private Label wordLabel; // Muestra la palabra aleatoria
    @FXML
    private TextField inputTextField; // Campo para escribir la palabra
    @FXML
    private Label messageLabel; // Muestra mensajes de éxito o error

    private GameModel gameModel;

    /**
     * Método de inicialización del controlador.
     * Se ejecuta automáticamente después de cargar la vista.
     */
    public void initialize() {
        gameModel = new GameModel();
        showNewWord(); // Mostrar la primera palabra al iniciar
    }

    /**
     * Muestra una nueva palabra aleatoria en la interfaz.
     */
    private void showNewWord() {
        wordLabel.setText(gameModel.getCurrentWord()); // Mostrar una nueva palabra
    }

    /**
     * Valida la palabra escrita por el usuario.
     */
    @FXML
    private void validateWord() {
        String correctWord = wordLabel.getText(); // Palabra correcta
        String userInput = inputTextField.getText().trim(); // Palabra del usuario (sin espacios)

        if (userInput.equals(correctWord)) {
            messageLabel.setText("¡Correcto!");
            gameModel.generateNewWord(); // Generar una nueva palabra
            showNewWord(); // Mostrar la nueva palabra
            inputTextField.clear(); // Limpiar el campo de texto
        } else {
            messageLabel.setText("Error: Inténtalo de nuevo.");
        }
    }

    /**
     * Maneja el evento de presionar una tecla en el campo de texto.
     *
     * @param event El evento de teclado.
     */
    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            validateWord(); // Validar al presionar "Enter"
        }
    }
}