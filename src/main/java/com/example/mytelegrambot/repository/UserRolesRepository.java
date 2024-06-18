package com.example.mytelegrambot.repository;

import com.example.mytelegrambot.model.UserRole;
import org.springframework.data.repository.CrudRepository;

public interface UserRolesRepository extends CrudRepository<UserRole, Long> {
}
