package org.example.service;

import lombok.Getter;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.util.SecurityConstants;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class AuthService {

    @Getter
    private static final AuthService INSTANCE = new AuthService();
    private static final int TOKEN_LENGTH = 32;
    private static final AtomicInteger idCounter = new AtomicInteger();
    private static final Map<Integer, User> userBase = new HashMap<>();
    private static final Map<String, String> tokenBase = new HashMap<>();

    static {
        userBase.put(10, User.builder()
                .email("mail@gmail.com")
                .password("123")
                .role(Role.ADMIN)
                .build());
    }

    public int registerUser(String email, String password, Role role) {
        int id = idCounter.incrementAndGet();
        User user = new User(email, password, role);
        userBase.put(id, user);
        tokenBase.put(email, generateToken(id));
        return id;
    }

    public boolean isAuthenticateUser(String email, String password) {
        for (Map.Entry<Integer, User> entry : userBase.entrySet()) {
            User user = entry.getValue();
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                tokenBase.put(user.getEmail(), generateToken(entry.getKey()));
                return true;
            }
        }
        return false;
    }

    public String generateToken(Integer id) {
        byte[] tokenBytes = new byte[TOKEN_LENGTH];
        new SecureRandom().nextBytes(tokenBytes);
        String token = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
        return isAdmin(id) ? token + SecurityConstants.SECRET_WORD : token;
    }

    public boolean isValidateToken(String token) {
        return tokenBase.containsValue(token);
    }

    public String getTokenFromEmail(String email) {
        return tokenBase.get(email);
    }

    private static boolean isAdmin(Integer id) {
        return userBase.get(id).getRole() == Role.ADMIN;
    }

    private AuthService() {

    }
}
