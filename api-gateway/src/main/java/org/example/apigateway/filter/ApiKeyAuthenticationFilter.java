package org.example.apigateway.filter;

import org.example.apigateway.config.ApiKeyAuthenticationManager;
import org.example.apigateway.config.ApiKeyAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ApiKeyAuthenticationFilter extends AuthenticationWebFilter {

    public ApiKeyAuthenticationFilter(ApiKeyAuthenticationManager manager) {
        super(manager);

        setServerAuthenticationConverter(exchange -> {
            ServerHttpRequest request = exchange.getRequest();
            String apiKey = request.getHeaders().getFirst("x-api-key");

            if (apiKey == null || apiKey.trim().isEmpty()) {
                return Mono.empty();
            }

            AbstractAuthenticationToken token = new ApiKeyAuthenticationToken(apiKey, "");
            return Mono.just(token);
        });
    }
}