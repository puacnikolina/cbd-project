package org.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()

                .route("user-service", r -> r.path("/users/**")
                        .uri("lb://user-service"))
                .route("exhibition-service", r -> r.path("/exhibitions/**")
                        .uri("lb://exhibition-service"))
                .build();
    }
}
