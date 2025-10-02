    package com.example.invoice_app.Sevice;

    import org.springframework.stereotype.Service;
    import org.springframework.web.client.RestTemplate;
    import org.springframework.web.util.UriComponentsBuilder;

    import java.util.Map;

    @Service
    public class RecaptchaService {

        private final String SECRET_KEY = "6Lchr9krAAAAABuDRViWdzUmM_61xeMbEKmo8MOg";

        private final RestTemplate restTemplate = new RestTemplate();

        public boolean verifyRecaptcha(String captchaResponse) {
            if(captchaResponse == null || captchaResponse.isEmpty()) return false;

            String url = UriComponentsBuilder
                    .fromHttpUrl("https://www.google.com/recaptcha/api/siteverify")
                    .queryParam("secret", SECRET_KEY)
                    .queryParam("response", captchaResponse)
                    .toUriString();

            Map<String, Object> response = restTemplate.postForObject(url, null, Map.class);

            boolean success = response != null && (Boolean) response.get("success");
            System.out.println("Recaptcha verification final result: " + success); // Adj hozzá egyértelmű logot
            System.out.println("captcha service response:" + response.toString());
            return success;
        }
    }
