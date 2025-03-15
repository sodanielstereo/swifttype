package com.example.swifttype.model;

import java.util.Random;

/**
 * Class that generates random words for the game.
 * This class provides a list of words and selects one randomly for each level.
 *
 * @author Daniel Fernando Vallejo Cabrera - 2343154
 * @version 1.0
 */
public class WordGenerator {
    private String[] words = {"Easy", "HelloWorld", "Hospitable", "Programming", "Events", "Specifically", "JavaFX", "Concentration", "Elementary", "Assignment", "Difficulty", "Sternocleidomastoid"};
    private Random random;

    /**
     * Constructor for the WordGenerator class.
     * Initializes the random number generator.
     */
    public WordGenerator() {
        random = new Random();
    }

    /**
     * Gets a random word from the list.
     *
     * @return A random word.
     */
    public String getRandomWord() {
        return words[random.nextInt(words.length)];
    }
}