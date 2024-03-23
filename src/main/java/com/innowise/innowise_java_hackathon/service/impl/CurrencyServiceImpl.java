package com.innowise.innowise_java_hackathon.service.impl;

import com.innowise.innowise_java_hackathon.client.CurrencyClient;
import com.innowise.innowise_java_hackathon.exception.EntityNotFoundException;
import com.innowise.innowise_java_hackathon.mapper.CurrencyMapper;
import com.innowise.innowise_java_hackathon.model.entity.Currency;
import com.innowise.innowise_java_hackathon.repository.CurrencyRepository;
import com.innowise.innowise_java_hackathon.service.CurrencyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrencyServiceImpl implements CurrencyService {

    private final CurrencyClient currencyClient;
    private final CurrencyMapper currencyMapper;
    private final CurrencyRepository currencyRepository;

    @Override
    public void saveCurrency(Currency currency) {
        currencyRepository.save(currency);
    }

    @Override
    public void saveCurrencies(List<Currency> currencies) {
        currencyRepository.saveAll(currencies);
    }

    @Override
    public List<Currency> getCurrencies() {
        return currencyClient.getCurrencies().stream()
                .map(currencyMapper::toCurrency)
                .toList();
    }

    @Override
    public List<Currency> getCurrenciesFromDb() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency getCurrencyBySymbol(String symbol) {
        return currencyMapper.toCurrency(currencyClient.getCurrencyBySymbol(symbol));
    }

    @Override
    public Currency getCurrencyBySymbolFromDb(String symbol) {
        return currencyRepository.findBySymbol(symbol)
                .orElseThrow(() -> new EntityNotFoundException(Currency.class, symbol));
    }
}
