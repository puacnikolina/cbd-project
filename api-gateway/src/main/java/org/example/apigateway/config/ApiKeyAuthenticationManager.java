package org.example.apigateway.config;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import reactor.core.publisher.Mono;

public class ApiKeyAuthenticationManager implements ReactiveAuthenticationManager {

    private static final String ADMIN_KEY = "adminKey123";
    private static final String USER_KEY = "userKey123";

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String apiKey = (String) authentication.getCredentials();

        if (ADMIN_KEY.equals(apiKey)) {
            return Mono.just(new ApiKeyAuthenticationToken(apiKey, "ROLE_ADMIN"));
        } else if (USER_KEY.equals(apiKey)) {
            return Mono.just(new ApiKeyAuthenticationToken(apiKey, "ROLE_USER"));
        }

        return Mono.empty(); // authentication failed
    }
}