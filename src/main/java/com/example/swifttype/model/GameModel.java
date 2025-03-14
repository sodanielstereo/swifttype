package com.example.swifttype.model;

/**
 * Clase que maneja la lógica del juego, incluyendo el temporizador, los niveles y las oportunidades.
 */
public class GameModel {
    private WordGenerator wordGenerator;
    private String currentWord;
    private int timeRemaining; // Tiempo restante en segundos
    private int level; // Nivel actual
    private int opportunities; // Oportunidades restantes

    /**
     * Constructor de la clase GameModel.
     * Inicializa el generador de palabras, el tiempo, el nivel y las oportunidades.
     */
    public GameModel() {
        wordGenerator = new WordGenerator();
        timeRemaining = 20; // Tiempo inicial de 20 segundos
        level = 1; // Nivel inicial
        opportunities = 4; // 4 oportunidades iniciales
        generateNewWord();
    }

    /**
     * Genera una nueva palabra aleatoria.
     */
    public void generateNewWord() {
        currentWord = wordGenerator.getRandomWord();
    }

    /**
     * Obtiene la palabra actual que el usuario debe escribir.
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

    /**
     * Obtiene el tiempo restante en segundos.
     *
     * @return El tiempo restante.
     */
    public int getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Reduce el tiempo restante en 1 segundo.
     */
    public void decrementTime() {
        if (timeRemaining > 0) {
            timeRemaining--;
        }
    }

    /**
     * Obtiene el nivel actual.
     *
     * @return El nivel actual.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Incrementa el nivel en 1 y ajusta el tiempo de juego si es necesario.
     */
    public void incrementLevel() {
        level++;
        adjustTimeForLevel(); // Ajustar el tiempo cada 5 niveles
    }

    /**
     * Ajusta el tiempo de juego cada 5 niveles.
     */
    private void adjustTimeForLevel() {
        if (level % 5 == 0 && timeRemaining > 2) {
            timeRemaining = Math.max(timeRemaining - 2, 2); // Reduce el tiempo en 2 segundos cada 5 niveles, pero no menos de 2 segundos
        }
    }

    /**
     * Reinicia el tiempo restante al valor correspondiente.
     */
    public void resetTime() {
        int baseTime = 20; // Tiempo base
        int reduction = (level / 5) * 2; // Reducción de 2 segundos por cada 5 niveles
        timeRemaining = Math.max(baseTime - reduction, 2); // No menos de 2 segundos
    }

    /**
     * Obtiene las oportunidades restantes.
     *
     * @return Las oportunidades restantes.
     */
    public int getOpportunities() {
        return opportunities;
    }

    /**
     * Reduce las oportunidades restantes en 1.
     */
    public void decrementOpportunities() {
        if (opportunities > 0) {
            opportunities--;
        }
    }
}