package com.example.pedido_service.client;

import com.example.pedido_service.model.Pedido;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class PedidoClient {

    private final WebClient clienteClient;
    private final WebClient estoqueClient;

    public PedidoClient(
            @Value("${CLIENTE_SERVICE_HOST:localhost}") String clienteHost,
            @Value("${CLIENTE_SERVICE_PORT:8082}") String clientePort,
            @Value("${ESTOQUE_SERVICE_HOST:localhost}") String estoqueHost,
            @Value("${ESTOQUE_SERVICE_PORT:8083}") String estoquePort
    ) {
        String clienteUrl = "http://" + clienteHost + ":" + clientePort;
        String estoqueUrl = "http://" + estoqueHost + ":" + estoquePort;

        this.clienteClient = WebClient.create(clienteUrl);
        this.estoqueClient = WebClient.create(estoqueUrl);
    }


    public Mono<Boolean> clienteExiste(String clienteId) {
        return clienteClient.get()
                .uri("/clientes/{id}", clienteId)
                .retrieve()
                .bodyToMono(Object.class)
                .map(o -> true)
                .onErrorReturn(false);
    }

    public Mono<Integer> consultarEstoque(String produtoId) {
        return estoqueClient.get()
                .uri("/estoque/{id}", produtoId)
                .retrieve()
                .bodyToMono(ProdutoResponse.class)
                .map(ProdutoResponse::quantidade)
                .onErrorReturn(0);
    }
    public Mono<Boolean> reservarEstoque(String produtoId, int quantidade) {
        return estoqueClient.patch()
                .uri(uriBuilder -> uriBuilder.path("/estoque/{id}/reservar")
                        .queryParam("quantidade", quantidade)
                        .build(produtoId))
                .retrieve()
                .bodyToMono(Object.class)
                .map(o -> true)
                .onErrorReturn(false);
    }
    public Mono<Void> enviarPedidoParaCliente(String clienteId, Pedido pedido) {
        return clienteClient.post()
                .uri("/clientes/{id}/adicionar-pedido", clienteId)
                .bodyValue(new PedidoResumo(pedido.getId(), pedido.getProduto(), pedido.getQuantidade()))
                .retrieve()
                .bodyToMono(Void.class)
                .onErrorResume(e -> Mono.empty());
    }
    public static record ProdutoResponse(String produtoId, String nome, int quantidade) {
        public int quantidade() { return quantidade; }
    }

    public static class PedidoResumo {
        public String pedidoId;
        public String produto;
        public int quantidade;

        public PedidoResumo(String pedidoId, String produto, int quantidade) {
            this.pedidoId = pedidoId;
            this.produto = produto;
            this.quantidade = quantidade;
        }
    }
}
