package com.example.pedido_service.service;

import com.example.pedido_service.client.PedidoClient;
import com.example.pedido_service.model.Pedido;
import com.example.pedido_service.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final PedidoClient client;

    public PedidoService(PedidoRepository repository, PedidoClient client) {
        this.repository = repository;
        this.client = client;
    }

    public Mono<Pedido> criarPedido(Pedido pedido) {
        return client.verificarProduto(pedido.getIdProduto())
                .flatMap(qtdDisponivel -> {
                    if (qtdDisponivel < pedido.getQuantidade()) {
                        return Mono.error(new RuntimeException("Quantidade indisponível no estoque"));
                    }
                    return client.verificarCliente(pedido.getIdCliente())
                            .flatMap(clienteOk -> {
                                if (!clienteOk) {
                                    return Mono.error(new RuntimeException("Cliente inválido"));
                                }
                                // Adaptando chamada síncrona do repository para Mono
                                return Mono.fromCallable(() -> repository.save(pedido));
                            });
                });
    }

    public Flux<Pedido> listarPedidos() {
        // Adaptando chamada síncrona para Flux
        return Flux.fromIterable(repository.findAll());
    }

    public Mono<Pedido> buscarPedido(Long id) {
        return Mono.justOrEmpty(repository.findById(id));
    }

    public Mono<Void> deletarPedido(Long id) {
        return Mono.fromRunnable(() -> repository.deleteById(id));
    }
}
