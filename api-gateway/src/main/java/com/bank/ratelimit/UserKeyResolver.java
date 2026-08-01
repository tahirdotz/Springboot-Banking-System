package com.bank.ratelimit;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class UserKeyResolver implements KeyResolver {

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        // Extract the user ID from the request (e.g., from a header or query parameter)
        String userId = exchange.getRequest().getHeaders().getFirst("X-User-Id");
        if (userId == null) {
            userId = "anonymous"; // Default to anonymous if no user ID is provided
        }
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst("X-User-Id")).switchIfEmpty(Mono.just(exchange.getRequest().getHostAddress().toString()));
    }
}
