package com.innowise.innowise_java_hackathon.client;

import com.innowise.innowise_java_hackathon.model.dto.CurrencyDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CurrencyClient {

    @Value("${app.currencyUrl}")
    private String currencyUrl;

    private static final String CURRENCY_SYMBOL_FIELD = "symbol";

    private final WebClient.Builder webClientBuilder;

    public List<CurrencyDto> getCurrencies() {
        return webClientBuilder.build().get()
                .uri(currencyUrl)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<CurrencyDto>>() {
                })
                .block();
    }

    public CurrencyDto getCurrencyBySymbol(String symbol) {
        return webClientBuilder.build().get()
                .uri(currencyUrl,
                        uriBuilder -> uriBuilder.queryParam(CURRENCY_SYMBOL_FIELD, symbol)
                                .build())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<CurrencyDto>() {
                })
                .block();
    }
}
