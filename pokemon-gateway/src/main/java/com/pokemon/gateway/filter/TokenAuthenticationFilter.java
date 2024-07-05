package com.pokemon.gateway.filter;

import com.pokemon.gateway.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
public class TokenAuthenticationFilter implements WebFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return redirectToLogin(exchange);
        }

        String authorizationHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return redirectToLogin(exchange);
        }

        String token = authorizationHeader.substring(7);
        if (!jwtUtils.validateJwtToken(token)) {
            return redirectToLogin(exchange);
        }

        return chain.filter(exchange);
    }

    private Mono<Void> redirectToLogin(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
        exchange.getResponse().getHeaders().setLocation(URI.create("/signin"));
        return exchange.getResponse().setComplete();
    }
}