package com.example.invoice_app.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    //jelszó titkosítás
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                // H2 Console-hoz kell
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                // login felület elérhető mndenki számára, lecseréli a security alap belépési formját
                .formLogin(httpForm -> {
                    httpForm.loginPage("/login")
                            .failureHandler(customAuthenticationFailureHandler)
                            .successHandler(customAuthenticationSuccessHandler)
                            .permitAll();
                })

                // Engedélyezett / védett endpointok
                // engedélyezük a hozzáférést a regisztrációhoz és a h2 konzolhoz
                // engedélyezük a css és js használatát, hogy a html-ek formázva legyenek
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/invoices/**").hasAnyRole("USER", "ACCOUNTANT", "ADMIN")
                        .requestMatchers("/invoice/create").hasAnyRole("ACCOUNTANT", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/registration", "/h2-console/**", "/css/**", "/js/**", "/api/**").permitAll()
                        .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login")
                        .permitAll()
                )
                .build();
    }
}
