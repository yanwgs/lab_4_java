package com.example.mytelegrambot.service;

import com.example.mytelegrambot.model.DBJokes;
import com.example.mytelegrambot.model.DbJokesHistory;
import com.example.mytelegrambot.repository.JokesRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JokeService implements JokeServiceIterface {

    private final JokesRepositoryInterface jokes;

    @Override
    public void addjoke(DBJokes joke){
        joke.setCreated(new Date());

        if (joke.getJokesHistories() == null) {
            joke.setJokesHistories(new ArrayList<>());
        }

        joke.getJokesHistories().add(new DbJokesHistory(null, joke, joke.getText(), new Date(), null));
        jokes.save(joke);
    }
    @Override
    public List<DBJokes> getAllJokes(){
        return jokes.findAll();
    }
    @Override
    public Optional<DBJokes> getJokeById(Long id){
        return jokes.findById(id);
    }
    @Override
    public ResponseEntity<String> deleteJokeById(Long id){
        if (jokes.findById(id).isPresent()){
            jokes.deleteById(id);
            return ResponseEntity.ok("Шутка успешно удалена с id: "+id);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
    @Override
    public Optional<DBJokes> editJokeById(Long id, String text) {
        Optional<DBJokes> optionalJoke = jokes.findById(id);
        if (optionalJoke.isPresent()){
            DBJokes joke = optionalJoke.get();
            String oldText = joke.getText();

            // Обновление текста шутки
            joke.setText(text);
            joke.setUpdated(new Date());
            jokes.save(joke);

            // Создание новой записи истории изменений
            DbJokesHistory newHistory = new DbJokesHistory();
            newHistory.setJokes(joke);
            newHistory.setText(text);
            newHistory.setCreated(new Date());
            newHistory.setUpdated(joke.getUpdated());

            // Добавление новой записи истории в список историй
            if (joke.getJokesHistories() == null) {
                joke.setJokesHistories(new ArrayList<>());
            }
            joke.getJokesHistories().add(newHistory);

            // Сохранение изменений
            jokes.save(joke);
        }
        return optionalJoke;
    }

    public List<DBJokes> getTop5JokesByLikes() {
        return jokes.findTop5ByOrderByLikesDesc();
    }

    public ResponseEntity<String> likeJoke(Long id) {
        Optional<DBJokes> optionalJoke = jokes.findById(id);
        if (optionalJoke.isPresent()) {
            DBJokes joke = optionalJoke.get();
            joke.setLikes(joke.getLikes() + 1);
            jokes.save(joke);
            return ResponseEntity.ok("Нам очень приятно что вы выбрали эту шутку.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    public DBJokes getRandomJoke() {
        return jokes.findRandomJoke();
    }

    public Page<DBJokes> pagejoke(Pageable pageable) {
        return jokes.findAll(pageable);
    }

}
