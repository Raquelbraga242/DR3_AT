package com.example.pedido_service.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PedidoClient {

    private final WebClient webClientCliente;
    private final WebClient webClientEstoque;

    public PedidoClient(
            @Value("${services.cliente}") String clienteUrl,
            @Value("${services.estoque}") String estoqueUrl
    ) {
        this.webClientCliente = WebClient.create(clienteUrl);
        this.webClientEstoque = WebClient.create(estoqueUrl);
    }

    public Mono<Boolean> verificarCliente(Long idCliente) {
        return webClientCliente.get()
                .uri("/clientes/{id}", idCliente)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorReturn(false);
    }

    public Mono<Integer> verificarProduto(Long idProduto) {
        return webClientEstoque.get()
                .uri("/produtos/{id}", idProduto)
                .retrieve()
                .bodyToMono(Integer.class)
                .onErrorReturn(0);
    }
}
