package com.example.invoice_app.Sevice;


import org.springframework.stereotype.Service;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class LoginAttemptService {

    // a rossz login threshold meghatározása ha átlépi kell captcha
    private final int MAX_ATTEMPTS = 3;
    // számláló
    private final Map<String, Integer> attemptsCache = new ConcurrentHashMap<>();

    // Sikeres loginnál nullázok a számlálót
    public void loginSucceeded(String key) {
        attemptsCache.remove(key);
    }

    // Sikertelen login-nál a számláló nő 1-el
    public void loginFailed(String key) {
        int attempts = attemptsCache.getOrDefault(key, 0);
        attempts++;
        attemptsCache.put(key, attempts);
    }

    // Ha a számláló nagyobb mint a megadott threshold akkor kérjük a captcha-t
    public boolean isCaptchaRequired(String key) {
        return attemptsCache.getOrDefault(key, 0) >= MAX_ATTEMPTS;
    }

}
