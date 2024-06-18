package com.example.mytelegrambot.repository;

import com.example.mytelegrambot.model.DBJokes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JokesRepositoryInterface extends JpaRepository<DBJokes, Long> {
    List<DBJokes> findTop5ByOrderByLikesDesc();

    @Query(value = "SELECT * FROM jokes ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    DBJokes findRandomJoke();
}
