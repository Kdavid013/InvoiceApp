package com.example.invoice_app.security;

import com.example.invoice_app.Sevice.LoginAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final LoginAttemptService loginAttemptService;

    public CustomAuthenticationFailureHandler(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username"); // A bemeneti mező neve a HTML-ben

        if (username != null) {
            loginAttemptService.loginFailed(username);
        }

        String targetUrl = "/login?error"; // Alapértelmezett hiba URL

        if (username != null && loginAttemptService.isCaptchaRequired(username)) {
            // A küszöb elérése esetén CAPTCHA-t kérő paraméterrel irányítunk át
            System.out.println("DEBUG: captcha required értéke "+loginAttemptService.isCaptchaRequired(username));

            targetUrl += "&captcha_required=true";
        }

        // Ezt hívja meg az ősosztály, ami beállítja a failureUrl-t
        // Érdemes a securityFilterChain-ben a .failureUrl("/login?error") helyett
        // .failureHandler(customAuthenticationFailureHandler) hívást használni,
        // amit Ön már meg is tett.
        // A SimpleUrlAuthenticationFailureHandler ősosztály használata esetén az
        // setDefaultFailureUrl("/login?error"); hívás segítene.
        // Most kézzel állítjuk be a redirectet, mivel az ősosztály a fix failureUrl-t használná.

        // Mivel Ön a CustomAuthenticationFailureHandler-t használja:
        response.sendRedirect(targetUrl);
    }

}
