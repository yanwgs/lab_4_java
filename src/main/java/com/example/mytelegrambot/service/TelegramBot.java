package com.example.mytelegrambot.service;

import com.example.mytelegrambot.Config.BotConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    final BotConfig config;

    @Autowired
    private JokeService jokeService;

    public TelegramBot(BotConfig config) {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken(){
        return config.getToken();
    }

    private void sendTextMessage(String chatId, String text) {
        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            String joke = null;

            switch (messageText){
                case "/jokes":
                    joke = jokeService.getRandomJoke().getText();
                    sendTextMessage(chatId, joke);// Отправляем шутку пользователю
                    break;
                case "/help":
                    String helpMessage = "Вот список команд, которые я понимаю:\n"
                            + "/jokes - Получить случайную шутку\n"
                            + "/help - Получить помощь";
                    sendTextMessage(chatId, helpMessage);
                    break;

                default:
                    String defaultMessage = "Извините, я не понимаю эту команду.";
                    sendTextMessage(chatId, defaultMessage);
                    break;
            }

        }
    }
}
