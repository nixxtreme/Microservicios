package com.micro.gateway.beans;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Set;

@Configuration
public class GatewayBeans {
    @Bean
    @Profile(value="eureka-off")
    public RouteLocator routeLocatorEurekaOff(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route->route
                        .path("/micro-crud/company/*")
                        .uri("http://localhost:8081")
                )
                .route(route->route
                        .path("/report-ms/report/*")
                        .uri("http://localhost:7070")
                )
                .build();
    }

    @Bean
    @Profile(value="eureka-on")
    public RouteLocator routeLocatorEurekaOn(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route->route
                        .path("/micro-crud/company/**")
                        .uri("lb://micro-crud")
                )
                .route(route->route
                        .path("/report-ms/report/**")
                        .uri("lb://report-ms")
                )
                .build();
    }

    @Bean
    @Profile(value="eureka-on-cb")
    public RouteLocator routeLocatorEurekaOnCB(RouteLocatorBuilder builder) {
        return builder
                .routes()
                .route(route->route
                        .path("/micro-crud/company/**")
                        .filters(filter -> {
                            filter.circuitBreaker(config -> config
                                    .setName("gateway-cb")
                                    .setStatusCodes(Set.of("400", "500"))
                                    .setFallbackUri("forward:/micro-crud-fallback/company/*"));
                            return filter;
                        })
                        .uri("lb://micro-crud")
                )
                .route(route->route
                        .path("/report-ms/report/**")
                        .uri("lb://report-ms")
                )
                .route(route->route
                        .path("/micro-crud-fallback/company/**")
                        .uri("lb://micro-crud-fallback")
                )
                .build();
    }
}
