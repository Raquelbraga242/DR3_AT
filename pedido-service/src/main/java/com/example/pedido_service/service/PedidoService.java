package com.example.pedido_service.service;

import com.example.pedido_service.client.PedidoClient;
import com.example.pedido_service.model.Pedido;
import com.example.pedido_service.model.PedidoRequest;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
@Service
public class PedidoService {
    private final PedidoClient client;
    public PedidoService(PedidoClient client){ this.client = client; }

    public Mono<Pedido> criarPedido(PedidoRequest req) {

        return client.clienteExiste(req.getClienteId())
                .flatMap(clienteOk -> {
                    if (!clienteOk) return Mono.error(new RuntimeException("Cliente não existe"));

                    return client.consultarEstoque(req.getProduto())
                            .flatMap(qtdDisponivel -> {
                                if (qtdDisponivel < req.getQuantidade()) {
                                    return Mono.error(new RuntimeException("Estoque insuficiente"));
                                }

                                return client.reservarEstoque(req.getProduto(), req.getQuantidade())
                                        .flatMap(reservou -> {
                                            if (!reservou) return Mono.error(new RuntimeException("Falha ao reservar estoque"));

                                            Pedido pedido = new Pedido();
                                            pedido.setId(java.util.UUID.randomUUID().toString());
                                            pedido.setClienteId(req.getClienteId());
                                            pedido.setProduto(req.getProduto());
                                            pedido.setQuantidade(req.getQuantidade());
                                            pedido.setStatus("CRIADO");
                                            pedido.setTimestamp(java.time.Instant.now());

                                            return client.enviarPedidoParaCliente(req.getClienteId(), pedido)
                                                    .thenReturn(pedido);
                                        });
                            });
                });
    }
}
