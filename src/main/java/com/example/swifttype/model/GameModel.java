package com.example.swifttype.model;

/**
 * Class that handles the game logic, including the timer, levels, and opportunities.
 * This class is responsible for generating random words, managing the game state,
 * and adjusting the difficulty as the player progresses.
 *
 * @author Daniel Fernando Vallejo Cabrera - 2343154
 * @version 1.0
 */
public class GameModel {
    private WordGenerator wordGenerator;
    private String currentWord;
    private int timeRemaining; // Remaining time in seconds
    private int level; // Current level
    private int opportunities; // Remaining opportunities

    /**
     * Constructor for the GameModel class.
     * Initializes the word generator, time, level, and opportunities.
     */
    public GameModel() {
        wordGenerator = new WordGenerator();
        timeRemaining = 20; // Initial time of 20 seconds
        level = 1; // Starting level
        opportunities = 4; // Initial 4 opportunities
        generateNewWord();
    }

    /**
     * Generates a new random word.
     */
    public void generateNewWord() {
        currentWord = wordGenerator.getRandomWord();
    }

    /**
     * Gets the current word that the user must type.
     *
     * @return The current word.
     */
    public String getCurrentWord() {
        return currentWord;
    }

    /**
     * Checks if the word typed by the user is correct.
     *
     * @param userInput The word typed by the user.
     * @return true if the word is correct, false otherwise.
     */
    public boolean checkWord(String userInput) {
        return userInput.equals(currentWord);
    }

    /**
     * Gets the remaining time in seconds.
     *
     * @return The remaining time.
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Decrements the remaining time by 1 second.
     */
    public void decrementTime() {
        if (timeRemaining > 0) {
            timeRemaining--;
        }
    }

    /**
     * Gets the current level.
     *
     * @return The current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Increments the level by 1 and adjusts the game time if necessary.
     */
    public void incrementLevel() {
        level++;
        adjustTimeForLevel(); // Adjust time every 5 levels
    }

    /**
     * Adjusts the game time every 5 levels.
     */
    private void adjustTimeForLevel() {
        if (level % 5 == 0 && timeRemaining > 2) {
            timeRemaining = Math.max(timeRemaining - 2, 2); // Reduce time by 2 seconds every 5 levels, but not less than 2 seconds
        }
    }

    /**
     * Resets the remaining time to the corresponding value.
     */
    public void resetTime() {
        int baseTime = 20; // Base time
        int reduction = (level / 5) * 2; // Reduction of 2 seconds for every 5 levels
        timeRemaining = Math.max(baseTime - reduction, 2); // Not less than 2 seconds
    }

    /**
     * Gets the remaining opportunities.
     *
     * @return The remaining opportunities.
     */
    public int getOpportunities() {
        return opportunities;
    }

    /**
     * Decreases the remaining opportunities by 1.
     */
    public void decrementOpportunities() {
        if (opportunities > 0) {
            opportunities--;
        }
    }
}