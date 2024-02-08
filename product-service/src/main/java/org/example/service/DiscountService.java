package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.entity.Discount;
import org.example.util.ExternalService;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class DiscountService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newBuilder().build();

    @SneakyThrows
    public double getPriceWithDiscount() {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(ExternalService.DISCOUNT.getUrl()))
                .timeout(Duration.of(1, ChronoUnit.SECONDS))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();
        Discount discount = mapper.readValue(jsonString, Discount.class);
        return discount.getPrice();
    }
}
