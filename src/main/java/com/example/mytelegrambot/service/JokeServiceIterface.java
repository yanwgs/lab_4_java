package com.example.mytelegrambot.service;

import com.example.mytelegrambot.model.DBJokes;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface JokeServiceIterface {
    void addjoke(DBJokes Jokes);
    List<DBJokes> getAllJokes();
    Optional<DBJokes> getJokeById(Long id);
    ResponseEntity<String> deleteJokeById(Long id);
    Optional<DBJokes> editJokeById(Long id, String text);
}
