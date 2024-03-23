package com.innowise.innowise_java_hackathon.service;

import com.innowise.innowise_java_hackathon.config.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    private static final String START = "/start";
    private static final String HELP = "/help";

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String text = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        String firstName = update.getMessage().getChat().getFirstName();

        switch (text) {
            case START -> startCommand(chatId, firstName);
            case HELP -> helpCommand(chatId);
            default -> unknownCommand(chatId);
        }
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    private void startCommand(Long chatId, String firstName) {
        String responseText = """
                Hi, %s! I am CryptoCurrency Bot!
                            
                Available commands:
                /test - coming soon...
                /help
                """;
        sendMessage(chatId, String.format(responseText, firstName));
    }

    private void helpCommand(Long chatId) {
        String responseText = """
                Help command, coming soon...
                """;
        sendMessage(chatId, responseText);
    }

    private void unknownCommand(Long chatId) {
        String responseText = """
                Sorry, command was not defined
                """;
        sendMessage(chatId, responseText);
    }

    private void sendMessage(Long chatId, String responseText) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(responseText);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
