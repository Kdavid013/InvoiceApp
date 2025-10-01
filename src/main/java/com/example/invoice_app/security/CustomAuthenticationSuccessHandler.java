package com.example.invoice_app.security;

import com.example.invoice_app.Sevice.LoginAttemptService;
import com.example.invoice_app.Sevice.LoginAuditService;
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

    public CustomAuthenticationSuccessHandler(LoginAttemptService loginAttemptService, LoginAuditService loginAuditService) {
        this.loginAttemptService = loginAttemptService;
        this.loginAuditService = loginAuditService;
    }

    // Ha sikeres a belépés akkor a számlálót reseteljük
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {



        String username = request.getParameter("username");

        loginAttemptService.loginSucceeded(username);

        loginAuditService.updateUserLoginDate(username);

        response.sendRedirect("/home");
    }
}
