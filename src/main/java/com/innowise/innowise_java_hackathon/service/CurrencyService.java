package com.innowise.innowise_java_hackathon.service;

import com.innowise.innowise_java_hackathon.model.entity.Currency;
import java.util.List;

public interface CurrencyService {

    void saveCurrency(Currency currency);

    void saveCurrencies(List<Currency> currencies);

    List<Currency> getCurrenciesFromDb();

    List<Currency> getCurrencies();

    Currency getCurrencyBySymbol(String symbol);

    Currency getCurrencyBySymbolFromDb(String symbol);
}
