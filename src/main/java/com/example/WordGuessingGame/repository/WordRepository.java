package com.example.WordGuessingGame.repository;

import com.example.WordGuessingGame.model.Word;
import com.example.WordGuessingGame.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordRepository extends JpaRepository<Word, Long> {
    List<Word> findByRating(Rating rating);
}
