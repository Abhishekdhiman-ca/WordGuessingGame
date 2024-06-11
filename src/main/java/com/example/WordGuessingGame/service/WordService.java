package com.example.WordGuessingGame.service;

import com.example.WordGuessingGame.model.Rating;
import com.example.WordGuessingGame.model.Word;
import com.example.WordGuessingGame.repository.WordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class WordService {

    @Autowired
    private WordRepository wordRepository;

    public Word getRandomWordByRating(Rating rating) {
        List<Word> words = wordRepository.findByRating(rating);
        if (words.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return words.get(random.nextInt(words.size()));
    }

    public Word getWordById(Long id) {
        return wordRepository.findById(id).orElse(null);
    }
}
