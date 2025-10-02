package com.example.invoice_app.security;

import com.example.invoice_app.Sevice.LoginAttemptService;
import com.example.invoice_app.Sevice.LoginAuditService;
import com.example.invoice_app.Sevice.RecaptchaService;
import com.example.invoice_app.Sevice.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    // LoginAttemptService injektálása
    private final LoginAttemptService loginAttemptService;
    private final LoginAuditService loginAuditService;
    private final RecaptchaService recaptchaService;

    public CustomAuthenticationSuccessHandler(LoginAttemptService loginAttemptService,
                                              LoginAuditService loginAuditService,
                                              RecaptchaService recaptchaService) {
        this.loginAttemptService = loginAttemptService;
        this.loginAuditService = loginAuditService;
        this.recaptchaService = recaptchaService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        String username = request.getParameter("username");
        String captchaResponse = request.getParameter("g-recaptcha-response");

        boolean captchaRequired = loginAttemptService.isCaptchaRequired(username);

        if (captchaRequired) {
            boolean captchaValid = recaptchaService.verifyRecaptcha(captchaResponse);

            if (!captchaValid) {
                request.getSession().setAttribute("errorMessage", "Captcha validation failed!");
                response.sendRedirect("/login?error");
                return;
            }
        }

        loginAttemptService.loginSucceeded(username);
        response.sendRedirect("/home");
        loginAuditService.updateUserLoginDate(username);

    }
}
