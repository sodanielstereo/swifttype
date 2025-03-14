package com.example.swifttype.model;

/**
 * Clase que maneja la lógica del juego.
 */
public class GameModel {
    private WordGenerator wordGenerator;
    private String currentWord;

    /**
     * Constructor de la clase GameModel.
     * Inicializa el generador de palabras y genera la primera palabra.
     */
    public GameModel() {
        wordGenerator = new WordGenerator();
        generateNewWord();
    }

    /**
     * Genera una nueva palabra aleatoria.
     */
    public void generateNewWord() {
        currentWord = wordGenerator.getRandomWord();
    }

    /**
     * Obtiene la palabra actual.
     *
     * @return La palabra actual.
     */
    public String getCurrentWord() {
        return currentWord;
    }

    /**
     * Verifica si la palabra escrita por el usuario es correcta.
     *
     * @param userInput La palabra escrita por el usuario.
     * @return true si la palabra es correcta, false en caso contrario.
     */
    public boolean checkWord(String userInput) {
        return userInput.equals(currentWord);
    }
}