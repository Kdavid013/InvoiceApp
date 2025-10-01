package com.example.invoice_app.security;

import com.example.invoice_app.Sevice.LoginAttemptService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    // LoginAttemptService injektálása
    private final LoginAttemptService loginAttemptService;

    public CustomAuthenticationFailureHandler(LoginAttemptService loginAttemptService) {
        this.loginAttemptService = loginAttemptService;
    }

    // A rossz bejelentkezések számolása
    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        String username = request.getParameter("username"); // A bemeneti mező neve a HTML-ben

        loginAttemptService.loginFailed(username);
        if (loginAttemptService.isCaptchaRequired(username)) {
            // CAPTCHA-t kell kérni → sessionbe tesszük
            request.getSession().setAttribute("captchaRequired", true);
        }

        request.getSession().setAttribute("errorMessage", "Invalid username or password");
        response.sendRedirect(request.getContextPath() + "/login?error");

    }

}
