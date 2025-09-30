package com.example.invoice_app.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CaptchaService {

    private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";

    @Value("${google.recaptcha.secret}")
    private String secret;

    public boolean verifyCaptcha(String response) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> body = Map.of(
                "secret", secret,
                "response", response
        );

        Map<String, Object> googleResponse = restTemplate.postForObject(
                GOOGLE_RECAPTCHA_VERIFY_URL + "?secret={secret}&response={response}",
                null, Map.class, body
        );

        return googleResponse != null && (Boolean) googleResponse.get("success");
    }
}
