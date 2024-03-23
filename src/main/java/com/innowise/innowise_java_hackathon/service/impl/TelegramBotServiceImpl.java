package com.innowise.innowise_java_hackathon.service.impl;

import com.innowise.innowise_java_hackathon.client.CurrencyClient;
import com.innowise.innowise_java_hackathon.mapper.CurrencyMapper;
import com.innowise.innowise_java_hackathon.model.entity.Currency;
import com.innowise.innowise_java_hackathon.service.TelegramBotService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TelegramBotServiceImpl implements TelegramBotService {

    private final CurrencyClient currencyClient;
    private final CurrencyMapper currencyMapper;

    @Override
    public List<Currency> getCurrencies() {
        return currencyClient.getCurrencies().stream()
                .map(currencyMapper::toCurrency)
                .toList();
    }

    @Override
    public Currency getCurrencyBySymbol(String symbol) {
        return currencyMapper.toCurrency(currencyClient.getCurrencyBySymbol(symbol));
    }
}
