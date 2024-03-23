package com.innowise.innowise_java_hackathon.repository;

import com.innowise.innowise_java_hackathon.model.entity.Currency;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrencyRepository extends MongoRepository<Currency, String> {

    Optional<Currency> findBySymbol(String symbol);
}
