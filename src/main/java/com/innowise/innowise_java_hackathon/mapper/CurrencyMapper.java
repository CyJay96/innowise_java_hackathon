package com.innowise.innowise_java_hackathon.mapper;

import com.innowise.innowise_java_hackathon.model.dto.CurrencyDto;
import com.innowise.innowise_java_hackathon.model.entity.Currency;
import org.mapstruct.Mapper;

@Mapper
public interface CurrencyMapper {

    Currency toCurrency(CurrencyDto currencyDto);
}
