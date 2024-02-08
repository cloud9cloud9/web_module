package org.example.util;

import lombok.Getter;

@Getter
public enum ExternalService {

    AUTH("http://localhost:8085/validate"),
    DISCOUNT("http://localhost:8099/discount");

    private final String url;
    ExternalService(String url) {
        this.url = url;
    }
}
