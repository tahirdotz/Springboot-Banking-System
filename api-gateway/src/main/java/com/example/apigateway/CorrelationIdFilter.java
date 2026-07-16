package com.example.apigateway;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class CorrelationIdFilter implements GlobalFilter, Ordered {
    private static final String CorrelationId="X-Correlation-ID";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String currentCorrelationId=exchange.getRequest().getHeaders().getFirst(CorrelationId);
        if(currentCorrelationId==null)
        {
            currentCorrelationId= UUID.randomUUID().toString();
        }
        final String correlationId = currentCorrelationId;
        exchange.getResponse().getHeaders().add(CorrelationId, correlationId);
        ServerWebExchange mutatedExchange = exchange.mutate()
                .request(r -> r.header(CorrelationId, correlationId))
                .build();
        return chain.filter(mutatedExchange);
    }

    @Override
    public int getOrder() {
        return -2;
    }
}
