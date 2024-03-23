package com.innowise.innowise_java_hackathon.model.entity;

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
public class Currency implements BaseEntity<String> {

    @Id
    private String id;

    private String symbol;

    private BigDecimal price;
}
