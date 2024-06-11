package com.example.WordGuessingGame.controller;

import com.example.WordGuessingGame.model.Rating;
import com.example.WordGuessingGame.model.Word;
import com.example.WordGuessingGame.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GameController {

    @Autowired
    private WordService wordService;

    private static final int MAX_LIVES = 5;
    private int lives = MAX_LIVES;

    @GetMapping("/")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("/start")
    public String startGame(@RequestParam(required = false) String difficulty, Model model) {
        if (difficulty != null) {
            Rating rating = Rating.valueOf(difficulty.toUpperCase());
            Word word = wordService.getRandomWordByRating(rating);
            model.addAttribute("word", word);
            model.addAttribute("gameState", new GameState(word.getWord()));
            model.addAttribute("hint", word.getHint());
            model.addAttribute("difficulty", difficulty);
            model.addAttribute("lives", lives); // Add lives to the model
        }
        return "game";
    }

    @PostMapping("/guess")
    public String makeGuess(@RequestParam String guess, @RequestParam Long wordId, Model model) {
        Word word = wordService.getWordById(wordId); // Get the word by ID
        GameState gameState = new GameState(word.getWord());
        gameState.makeGuess(guess);
        model.addAttribute("gameState", gameState);
        model.addAttribute("word", word);
        model.addAttribute("hint", word.getHint());
        model.addAttribute("difficulty", word.getRating().toString().toLowerCase());

        if (!gameState.isWin() && !gameState.isLose()) {
            if (!guess.equalsIgnoreCase(word.getWord())) {
                lives--;
            }
        }

        if (gameState.isWin()) {
            model.addAttribute("message", "Congratulations! You've guessed the word correctly.");
            resetGame();
        } else if (gameState.isLose() || lives <= 0) {
            model.addAttribute("message", "You lost! The correct word was: " + word.getWord());
            resetGame();
        }

        model.addAttribute("lives", lives); // Add lives to the model
        return "game";
    }

    private void resetGame() {
        lives = MAX_LIVES;
        // Other reset logic
    }
}
