package com.example.invoice_app.Sevice;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPT = 3;
    private final ConcurrentHashMap<String, Integer> attemptsCache = new ConcurrentHashMap<>();

    public void loginSucceeded(String key) {
        attemptsCache.remove(key);
    }

    public void loginFailed(String key) {
        int attempts = attemptsCache.getOrDefault(key, 0);
        attempts++;
        System.out.println(attempts);
        attemptsCache.put(key, attempts);
    }

    public boolean isCaptchaRequired(String key) {
        return attemptsCache.getOrDefault(key, 0) >= MAX_ATTEMPT;
    }
}
