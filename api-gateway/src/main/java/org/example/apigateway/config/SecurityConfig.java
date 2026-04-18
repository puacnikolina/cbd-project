package org.example.apigateway.config;

import io.netty.handler.codec.http.HttpMethod;
import org.example.apigateway.filter.ApiKeyAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) {
        ApiKeyAuthenticationManager authManager = new ApiKeyAuthenticationManager();
        ApiKeyAuthenticationFilter authFilter = new ApiKeyAuthenticationFilter(authManager);

        return http
                .csrf(csrf -> csrf.disable())
                .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(exchanges -> exchanges
                        //Svi
                        .pathMatchers("/users/register", "/users/login").permitAll()
                        //ADMIN
                        .pathMatchers("/users/admin/**", "/exhibitions/admin/**","/artists/admin/**").hasRole("ADMIN")
                        //PUBLIC routes
                        .pathMatchers("/exhibitions/**","/artists/**").permitAll()
                        .pathMatchers("/bookings/**").hasAnyRole("USER", "ADMIN")
                        .anyExchange().denyAll()
                )
                .build();
    }
}