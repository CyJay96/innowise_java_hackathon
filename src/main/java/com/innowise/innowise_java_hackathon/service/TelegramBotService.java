package com.innowise.innowise_java_hackathon.service;

import com.innowise.innowise_java_hackathon.model.entity.Currency;
import java.util.List;

public interface TelegramBotService {

    List<Currency> getCurrencies();

    Currency getCurrencyBySymbol(String symbol);
}
