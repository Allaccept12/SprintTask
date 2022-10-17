package com.example.sprint.domain.book.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;


@Getter
public enum Currency {
    KRW("KRW"),
    USD("USD"),
    EUR("EUR");

    private String value;

    Currency(String value) {
        this.value = value;
    }
}
