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
 * Main controller for the game. Manages the interaction between the view and the model.
 * This class is responsible for handling the game logic, including word validation,
 * time management, UI updates, and player opportunities.
 *
 * @author Daniel Fernando Vallejo Cabrera - 2343154
 * @version 1.0
 */
public class GameController implements Initializable {
    @FXML
    private BorderPane root; // Reference to the main BorderPane
    @FXML
    private Label wordLabel; // Displays the random word
    @FXML
    private TextField inputTextField; // Field for user input
    @FXML
    private Label messageLabel; // Displays success or error messages
    @FXML
    private Label timeLabel; // Displays the remaining time
    @FXML
    private Label levelLabel; // Displays the current level
    @FXML
    private Circle sunCircle; // Represents the full sun
    @FXML
    private Circle sunEclipseClip; // Represents the eclipse of the sun
    @FXML
    private Button validateButton; // Button to validate the word

    private GameModel gameModel; // Game model
    private Timeline timeline; // Timer
    private boolean isGameActive; // Indicates if the game is active

    /**
     * Initialization method for the controller.
     * This method is automatically executed after the view is loaded.
     * It sets up the gradient background, initializes the game model,
     * displays the first word, and starts the timer.
     *
     * @param location  The location used to resolve relative paths for the root object.
     * @param resources The resources used to localize the root object.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up the gradient background
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, null,
                new Stop(0, javafx.scene.paint.Color.web("#87CEEB")),
                new Stop(1, javafx.scene.paint.Color.web("#1E90FF")));
        root.setBackground(new Background(new BackgroundFill(gradient, null, null)));

        gameModel = new GameModel();
        showNewWord();
        updateSunProgress(); // Update the sun at the start
        startTimer();
    }

    /**
     * Displays a new random word in the UI.
     * Updates the word, remaining time, and level labels.
     */
    private void showNewWord() {
        wordLabel.setText(gameModel.getCurrentWord());
        timeLabel.setText("Time: " + gameModel.getTimeRemaining() + "s");
        levelLabel.setText("Level: " + gameModel.getLevel());
    }

    /**
     * Starts the game timer.
     * The timer decrements the remaining time every second and checks if the time has run out.
     */
    private void startTimer() {
        isGameActive = true; // The game starts active
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            gameModel.decrementTime();
            timeLabel.setText("Time: " + gameModel.getTimeRemaining() + "s");

            if (gameModel.getTimeRemaining() == 0) {
                // If time runs out, keep the level, reset the time, and show a new word
                gameModel.decrementOpportunities(); // Reduce an opportunity
                updateSunProgress(); // Update the sun after an error

                if (gameModel.getOpportunities() == 0) {
                    timeline.stop();
                    isGameActive = false; // The game stops
                    messageLabel.setText("Game Over! Levels completed: " + (gameModel.getLevel() - 1));
                } else {
                    // If there are still opportunities, keep the level, reset the time, and show a new word
                    gameModel.generateNewWord(); // Generate a new word
                    gameModel.resetTime(); // Reset the time
                    showNewWord(); // Show the new word
                    inputTextField.clear(); // Clear the input field
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Validates the word entered by the user.
     * Compares the user's input with the current word.
     * If the word is correct, increments the level and resets the time.
     * If the word is incorrect, reduces the opportunities and updates the sun eclipse.
     */
    @FXML
    private void validateWord() {
        if (!isGameActive) {
            return; // Do not validate if the game is not active
        }

        String correctWord = wordLabel.getText();
        String userInput = inputTextField.getText().trim();

        if (userInput.equals(correctWord)) {
            messageLabel.setText("Correct!");
            gameModel.incrementLevel(); // Increment the level and adjust the time
            gameModel.resetTime(); // Reset the time to the corresponding value
            gameModel.generateNewWord();
            showNewWord();
            inputTextField.clear();
        } else {
            messageLabel.setText("Error: Try again.");
            gameModel.decrementOpportunities(); // Reduce the opportunities
            updateSunProgress(); // Update the sun after an error

            if (gameModel.getOpportunities() == 0) {
                timeline.stop();
                isGameActive = false;
                messageLabel.setText("Game Over! Levels completed: " + (gameModel.getLevel() - 1));
            } else {
                // If there are still opportunities, show a new word, keep the level, and reset the time
                gameModel.generateNewWord(); // Generate a new word
                gameModel.resetTime(); // Reset the time
                showNewWord(); // Show the new word
                inputTextField.clear(); // Clear the input field
            }
        }
    }

    /**
     * Updates the progress of the sun eclipse.
     * The sun is progressively eclipsed as the player loses opportunities.
     */
    private void updateSunProgress() {
        double progress = 1 - (gameModel.getOpportunities() / 4.0); // Remaining opportunities (0 to 1)
        sunEclipseClip.setRadius(50 * progress); // Adjust the eclipse radius
    }

    /**
     * Handles the key press event in the input field.
     * If the "Enter" key is pressed, the word is validated.
     *
     * @param event The key event.
     */
    @FXML
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            validateWord(); // Validate on "Enter" key press
        }
    }
}