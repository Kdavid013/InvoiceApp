package com.example.invoice_app.security;

import com.example.invoice_app.Sevice.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class CaptchaAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final CaptchaService captchaService;
    private final LoginAttemptService loginAttemptService;

    public CaptchaAuthenticationFilter(AuthenticationManager authenticationManager,
                                       CaptchaService captchaService,
                                       LoginAttemptService loginAttemptService) {
        super.setAuthenticationManager(authenticationManager);
        this.captchaService = captchaService;
        this.loginAttemptService = loginAttemptService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        // csak akkor kell captcha, ha elérte a limiteket
        if (loginAttemptService.isCaptchaRequired(username)) {
            System.out.println("kell captcha");
            String captchaResponse = request.getParameter("g-recaptcha-response");
            if (captchaResponse == null || !captchaService.verifyCaptcha(captchaResponse)) {
                throw new BadCredentialsException("Captcha validation failed");
            }
        }

        return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            jakarta.servlet.FilterChain chain,
                                            Authentication authResult) throws java.io.IOException, jakarta.servlet.ServletException {
        String username = request.getParameter("username");

        // nagyon fontos, hogy a SecurityContext beálljon, és a session létrejöjjön
        System.out.println("successfulAuthentication lefutott");
        super.successfulAuthentication(request, response, chain, authResult);

        // reseteljük a próbálkozásokat
        loginAttemptService.loginSucceeded(username);
        response.sendRedirect("/home");
    }
}
