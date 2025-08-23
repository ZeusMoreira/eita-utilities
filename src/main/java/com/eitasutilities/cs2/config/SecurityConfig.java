package com.eitasutilities.cs2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita CSRF (para facilitar testes via Postman)
                .csrf(csrf -> csrf.disable())

                // Configura as permissões
                .authorizeHttpRequests(auth -> auth
                        // Libera Swagger UI e endpoints do OpenAPI
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        // Libera todos os outros endpoints (apenas para testes)
                        .anyRequest().permitAll()
                )

                // Usa a nova forma de basic auth (substituto do httpBasic())
                .httpBasic(basic -> {})

                // Substitui o formLogin antigo, se necessário
                .formLogin(login -> {});

        return http.build();
    }
}
