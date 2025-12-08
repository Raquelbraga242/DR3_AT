package com.example.cliente_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
@Component
public class EstoqueClient {

    private final WebClient webClient;

    public EstoqueClient(@Value("${estoque.url}") String baseUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public Mono<Integer> consultarDisponibilidade(Long idProduto) {
        return webClient.get()
                .uri("/produtos/{id}/disponibilidade", idProduto)
                .retrieve()
                .bodyToMono(Integer.class)
                .onErrorReturn(0); // para não quebrar caso o estoque não esteja disponível
    }
}
