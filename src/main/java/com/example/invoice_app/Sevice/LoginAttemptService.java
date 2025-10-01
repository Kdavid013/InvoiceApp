package com.example.invoice_app.Sevice;


import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {
    private final int MAX_ATTEMPTS = 3;
    private final Map<String, Integer> attemptsCache = new ConcurrentHashMap<>();

    public void loginSucceeded(String key) {
        attemptsCache.remove(key);
    }

    public void loginFailed(String key) {
        int attempts = attemptsCache.getOrDefault(key, 0);
        attempts++;
        attemptsCache.put(key, attempts);
        System.out.println("DEBUG: loginFailed username:" + key + "kisérletek száma " + attempts);
    }

    public boolean isCaptchaRequired(String key) {

        return attemptsCache.getOrDefault(key, 0) >= MAX_ATTEMPTS;
    }

    public void resetAttempts(String key) {
        attemptsCache.remove(key);
    }
}
