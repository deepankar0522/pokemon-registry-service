package com.pokemon.gateway.config;

import com.pokemon.gateway.filter.TokenAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, TokenAuthenticationFilter tokenAuthenticationFilter) {
        http.csrf().disable()
                .authorizeExchange()
                .pathMatchers("/login", "/register").permitAll()
                .anyExchange().authenticated()
                .and()
                .addFilterAt(tokenAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }
}


