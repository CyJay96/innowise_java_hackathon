package com.innowise.innowise_java_hackathon.service;

import com.innowise.innowise_java_hackathon.config.BotConfig;
import com.innowise.innowise_java_hackathon.model.entity.Currency;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final CurrencyService currencyService;

    private static final String START = "/start";
    private static final String HELP = "/help";

    private static String symbol;
    private static Long chatId;

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        String text = update.getMessage().getText();
        String firstName = update.getMessage().getChat().getFirstName();
        chatId = update.getMessage().getChatId();

        switch (text) {
            case START -> startCommand(chatId, firstName);
            case HELP -> helpCommand(chatId);
            default -> {
                if (currencyService.getCurrencies().stream()
                        .map(Currency::getSymbol)
                        .anyMatch(symbol -> symbol.equals(text.substring(1)))) {
                    currencyCommand(chatId, text.substring(1));
                } else {
                    unknownCommand(chatId);
                }
            }
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
                
                I will send you messages, when the currency of your choice
                rises or falls in price by more than 3 percent.
                
                In the future, it will be possible to set percentages.
                
                Enter the symbol of the currency by the next way:
                /X - where X is the symbol of the currency
                
                Available commands:
                /start - start crypto bot
                /help - get help
                """;
        sendMessage(chatId, String.format(responseText, firstName));
    }

    private void helpCommand(Long chatId) {
        String responseText = """
                Help command, coming soon...
                """;
        sendMessage(chatId, responseText);
    }

    private void currencyCommand(Long chatId, String symbol) {
        Currency currency = currencyService.getCurrencyBySymbol(symbol);
        currencyService.saveCurrency(currency);

        this.symbol = currency.getSymbol();

        String responseText = """
                Currency %s now is %s
                """;
        sendMessage(chatId, String.format(responseText, currency.getSymbol(), currency.getPrice()));
    }

    private void unknownCommand(Long chatId) {
        String responseText = """
                Sorry, command was not defined
                """;
        sendMessage(chatId, responseText);
    }

    @Scheduled(fixedRate = 20000)
    private void checkCurrency() {
        Currency fromDb = currencyService.getCurrencyBySymbolFromDb(symbol);
        Currency fromServer = currencyService.getCurrencyBySymbol(symbol);

        BigDecimal priceFromDb = new BigDecimal(fromDb.getPrice());
        BigDecimal priceFromServer = new BigDecimal(fromServer.getPrice());

        double percent = priceFromServer
                .subtract(priceFromDb)
                .divide(priceFromDb)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();

        log.info("Currency from DB: {}", priceFromDb);
        log.info("Currency from server: {}", priceFromServer);
        log.info("Percent: {}", percent);

        String responseText = """
                Currency %s has %s in price by %s percent.
                Current price is %s
                """;

        if (percent >= 3) {
            sendMessage(chatId, String.format(responseText, symbol, "risen", percent, priceFromServer));
        } else if (percent <= -3) {
            sendMessage(chatId, String.format(responseText, symbol, "fallen", percent, priceFromServer));
        }
    }

    private void sendMessage(Long chatId, String responseText) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(responseText);

        executeMessage(message);
    }

    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}
