package com.example.mytelegrambot.model;

import org.springframework.beans.factory.support.ManagedArray;
import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    USER,
    MODERATOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
