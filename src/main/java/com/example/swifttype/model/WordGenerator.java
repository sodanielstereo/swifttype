package com.example.swifttype.model;

import java.util.Random;

/**
 * Clase que genera palabras aleatorias para el juego.
 */
public class WordGenerator {
    private String[] words = {"Hola", "Mundo", "Java", "Programación", "Eventos", "IntelliJ", "JavaFX"};
    private Random random;

    /**
     * Constructor de la clase WordGenerator.
     * Inicializa el generador de números aleatorios.
     */
    public WordGenerator() {
        random = new Random();
    }

    /**
     * Obtiene una palabra aleatoria de la lista.
     *
     * @return Una palabra aleatoria.
     */
    public String getRandomWord() {
        return words[random.nextInt(words.length)];
    }
}