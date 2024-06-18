package com.example.mytelegrambot.repository;

import com.example.mytelegrambot.model.DbJokesHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokesHistoryRepositoryInterface extends JpaRepository<DbJokesHistory, Long> {}
