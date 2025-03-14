package com.example.swifttype.controller;

import com.example.swifttype.model.GameModel;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlador principal del juego. Maneja la interacción entre la vista y el modelo.
 * Esta clase es responsable de gestionar la lógica del juego, incluyendo la validación de palabras,
 * el manejo del tiempo, la actualización de la interfaz y la gestión de las oportunidades del jugador.
 */
public class GameController implements Initializable {
    @FXML
    private BorderPane root; // Referencia al BorderPane principal
    @FXML
    private Label wordLabel; // Muestra la palabra aleatoria
    @FXML
    private TextField inputTextField; // Campo para escribir la palabra
    @FXML
    private Label messageLabel; // Muestra mensajes de éxito o error
    @FXML
    private Label timeLabel; // Muestra el tiempo restante
    @FXML
    private Label levelLabel; // Muestra el nivel actual
    @FXML
    private Circle sunCircle; // Representa el sol completo
    @FXML
    private Circle sunEclipseClip; // Representa el eclipse del sol
    @FXML
    private Button validateButton; // Botón para validar la palabra

    private GameModel gameModel; // Modelo del juego
    private Timeline timeline; // Temporizador
    private boolean isGameActive; // Indica si el juego está activo

    /**
     * Método de inicialización del controlador.
     * Se ejecuta automáticamente después de cargar la vista.
     * Configura el fondo degradado, inicializa el modelo del juego, muestra la primera palabra
     * y comienza el temporizador.
     *
     * @param location  La ubicación utilizada para resolver rutas relativas para el objeto raíz.
     * @param resources Los recursos utilizados para localizar el objeto raíz.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar el fondo degradado
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, javafx.scene.paint.Color.web("#87CEEB")),
                new Stop(1, javafx.scene.paint.Color.web("#1E90FF")));
        root.setBackground(new Background(new BackgroundFill(gradient, null, null)));

        gameModel = new GameModel();
        showNewWord();
        updateSunProgress(); // Actualizar el sol al inicio
        startTimer();
    }

    /**
     * Muestra una nueva palabra aleatoria en la interfaz.
     * Actualiza las etiquetas de la palabra, el tiempo restante y el nivel.
     */
    private void showNewWord() {
        wordLabel.setText(gameModel.getCurrentWord());
        timeLabel.setText("Tiempo: " + gameModel.getTimeRemaining() + "s");
        levelLabel.setText("Nivel: " + gameModel.getLevel());
    }

    /**
     * Inicia el temporizador.
     * El temporizador decrementa el tiempo restante cada segundo y verifica si el tiempo se ha agotado.
     */
    private void startTimer() {
        isGameActive = true; // El juego comienza activo
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            gameModel.decrementTime();
            timeLabel.setText("Tiempo: " + gameModel.getTimeRemaining() + "s");

            if (gameModel.getTimeRemaining() == 0) {
                // Si el tiempo se acaba, mantener el nivel, reiniciar el tiempo y mostrar una nueva palabra
                gameModel.decrementOpportunities(); // Reducir una oportunidad
                updateSunProgress(); // Actualizar el sol después de un error

                if (gameModel.getOpportunities() == 0) {
                    timeline.stop();
                    isGameActive = false; // El juego se detiene
                    messageLabel.setText("¡Juego terminado! Niveles completados: " + (gameModel.getLevel() - 1));
                } else {
                    // Si aún hay oportunidades, mantener el nivel, reiniciar el tiempo y mostrar una nueva palabra
                    gameModel.generateNewWord(); // Generar una nueva palabra
                    gameModel.resetTime(); // Reiniciar el tiempo
                    showNewWord(); // Mostrar la nueva palabra
                    inputTextField.clear(); // Limpiar el campo de texto
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Valida la palabra escrita por el usuario.
     * Compara la palabra ingresada por el usuario con la palabra actual.
     * Si la palabra es correcta, incrementa el nivel y reinicia el tiempo.
     * Si la palabra es incorrecta, reduce las oportunidades y actualiza el sol eclipsado.
     */
    @FXML
    private void validateWord() {
        if (!isGameActive) {
            return; // No validar si el juego no está activo
        }

        String correctWord = wordLabel.getText();
        String userInput = inputTextField.getText().trim();

        if (userInput.equals(correctWord)) {
            messageLabel.setText("¡Correcto!");
            gameModel.incrementLevel(); // Incrementa el nivel y ajusta el tiempo
            gameModel.resetTime(); // Reinicia el tiempo al valor correspondiente
            gameModel.generateNewWord();
            showNewWord();
            inputTextField.clear();
        } else {
            messageLabel.setText("Error: Inténtalo de nuevo.");
            gameModel.decrementOpportunities(); // Reduce las oportunidades
            updateSunProgress(); // Actualizar el sol después de un error

            if (gameModel.getOpportunities() == 0) {
                timeline.stop();
                isGameActive = false;
                messageLabel.setText("¡Juego terminado! Niveles completados: " + (gameModel.getLevel() - 1));
            } else {
                // Si aún hay oportunidades, mostrar una nueva palabra, mantener el nivel y reiniciar el tiempo
                gameModel.generateNewWord(); // Generar una nueva palabra
                gameModel.resetTime(); // Reiniciar el tiempo
                showNewWord(); // Mostrar la nueva palabra
                inputTextField.clear(); // Limpiar el campo de texto
            }
        }
    }

    /**
     * Actualiza la barra de progreso del sol eclipsado.
     * El sol se eclipsa progresivamente a medida que el jugador pierde oportunidades.
     */
    private void updateSunProgress() {
        double progress = 1 - (gameModel.getOpportunities() / 4.0); // Oportunidades restantes (0 a 1)
        sunEclipseClip.setRadius(50 * progress); // Ajustar el radio del eclipse
    }

    /**
     * Maneja el evento de presionar una tecla en el campo de texto.
     * Si se presiona la tecla "Enter", se valida la palabra.
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