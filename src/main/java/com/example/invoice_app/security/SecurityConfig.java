package com.example.invoice_app.security;

import com.example.invoice_app.Sevice.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    //jelszó titkosítás
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService  uds, PasswordEncoder encoder){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(uds);
        provider.setPasswordEncoder(encoder);
        return provider;
    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                // H2 Console-hoz kell
                .csrf(csrf -> csrf.disable())
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))

                // login felület elérhető mndenki számára, lecseréli a security alap belépési formját
                .formLogin(httpForm -> {
                    httpForm.loginPage("/login").permitAll();
                })

                // Engedélyezett / védett endpointok
                // engedélyezük a hozzáférést a regisztrációhoz és a h2 konzolhoz
                // engedélyezük a css és js használatát, hogy a html-ek formázva legyenek
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/registration", "/h2-console/**", "/css/**", "/js/**", "/invoices/**","/invoicecreation").permitAll()
                        .anyRequest().authenticated()
                )
                // basic belépés login form nélkül
                .httpBasic(Customizer.withDefaults())
                .build();
    }
}
