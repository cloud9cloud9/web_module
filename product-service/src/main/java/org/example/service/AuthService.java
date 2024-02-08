package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.example.entity.Product;
import org.example.util.ExternalService;
import org.example.util.SecurityConstants;
import org.example.validator.ValidationResult;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class AuthService {
    private static final ObjectMapper mapper = new ObjectMapper();
    private static final HttpClient httpClient = HttpClient.newBuilder().build();
    @SneakyThrows
    public boolean isTokenValid(String token) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.noBody())
                .uri(URI.create(ExternalService.AUTH.getUrl()))
                .header("Authorization", token)
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        String jsonString = response.body();
        ValidationResult result = mapper.readValue(jsonString, ValidationResult.class);
        return result.isValid();
    }
    public boolean isAdminUser(String token){
        return token.contains(SecurityConstants.SECRET_WORD);
    }
}
