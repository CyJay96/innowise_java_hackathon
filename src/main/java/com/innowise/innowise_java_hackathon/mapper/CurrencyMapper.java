package com.innowise.innowise_java_hackathon.mapper;

import com.innowise.innowise_java_hackathon.model.dto.CurrencyDto;
import com.innowise.innowise_java_hackathon.model.entity.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CurrencyMapper {

    @Mapping(target = "id", ignore = true)
    Currency toCurrency(CurrencyDto currencyDto);
}
