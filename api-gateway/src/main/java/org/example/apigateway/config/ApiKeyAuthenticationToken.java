package org.example.apigateway.config;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import java.util.Collections;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {

    private final String apiKey;
    private final String role;

    public ApiKeyAuthenticationToken(String apiKey, String role) {
        super(Collections.singleton(() -> role));
        this.apiKey = apiKey;
        this.role = role;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return apiKey;
    }

    @Override
    public Object getPrincipal() {
        return role;
    }
}