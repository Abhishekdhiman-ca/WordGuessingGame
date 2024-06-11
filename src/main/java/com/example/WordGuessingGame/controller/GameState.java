package com.example.WordGuessingGame.controller;

import java.util.ArrayList;
import java.util.List;

public class GameState {
    private String word;
    private List<LetterState> letters = new ArrayList<>();
    private int attempts = 0;
    private boolean win = false;
    private boolean lose = false;

    public GameState(String word) {
        this.word = word;
        for (int i = 0; i < word.length(); i++) {
            letters.add(new LetterState('_', "badge-secondary"));
        }
    }

    public void makeGuess(String guess) {
        attempts++;
        boolean allCorrect = true;
        for (int i = 0; i < guess.length(); i++) {
            char guessedLetter = guess.charAt(i);
            if (guessedLetter == word.charAt(i)) {
                letters.set(i, new LetterState(guessedLetter, "badge-success"));
            } else if (word.indexOf(guessedLetter) >= 0) {
                letters.set(i, new LetterState(guessedLetter, "badge-warning"));
                allCorrect = false;
            } else {
                letters.set(i, new LetterState(guessedLetter, "badge-dark"));
                allCorrect = false;
            }
        }
        if (allCorrect) {
            win = true;
        } else if (attempts >= 5) {
            lose = true;
        }
    }

    public List<LetterState> getLetters() {
        return letters;
    }

    public boolean isWin() {
        return win;
    }

    public boolean isLose() {
        return lose;
    }

    public class LetterState {
        private char character;
        private String color;

        public LetterState(char character, String color) {
            this.character = character;
            this.color = color;
        }

        public char getCharacter() {
            return character;
        }

        public String getColor() {
            return color;
        }
    }
}
