package com.example.mytelegrambot.Config;

import com.example.mytelegrambot.model.UserAuthority;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableWebSecurity
public class SpringSecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(expressionInterceptUrlRegistry ->
                        expressionInterceptUrlRegistry
                                // Разрешить всем доступ к GET-запросам на указанные эндпоинты
                                .requestMatchers(HttpMethod.GET, "/jokes").permitAll()
                                .requestMatchers(HttpMethod.GET, "/jokes/pages/**").permitAll()
                                .requestMatchers(HttpMethod.GET, "/jokes/top5").permitAll()
                                .requestMatchers(HttpMethod.GET, "/jokes/random").permitAll()
                                // Разрешить всем доступ к POST-запросам на /jokes/like/*
                                .requestMatchers(HttpMethod.POST, "/jokes/like/**").permitAll()
                                // Разрешить только модераторам и администраторам доступ к POST, PUT, DELETE запросам на /jokes
                                .requestMatchers(HttpMethod.POST, "/jokes").hasAnyAuthority(UserAuthority.MODERATOR.getAuthority(), UserAuthority.ADMIN.getAuthority())
                                .requestMatchers(HttpMethod.PUT, "/jokes/**").hasAnyAuthority(UserAuthority.MODERATOR.getAuthority(), UserAuthority.ADMIN.getAuthority())
                                .requestMatchers(HttpMethod.DELETE, "/jokes/**").hasAnyAuthority(UserAuthority.MODERATOR.getAuthority(), UserAuthority.ADMIN.getAuthority())
                                // Разрешить всем доступ к регистрации и логину
                                .requestMatchers("/registration", "/login").permitAll()
                                // Все остальные запросы доступны только администратору
                                .anyRequest().hasAuthority(UserAuthority.ADMIN.getAuthority()))
                .formLogin(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable);

        return http.build();
    }



    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}