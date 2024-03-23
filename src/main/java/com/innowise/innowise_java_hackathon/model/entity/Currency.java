package com.innowise.innowise_java_hackathon.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "currencies")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Currency implements Serializable {

    @Id
    private String symbol;

    private String price;
}
