package com.example.pedido_service.service;

import static org.junit.jupiter.api.Assertions.*;


import com.example.pedido_service.client.PedidoClient;
import com.example.pedido_service.model.Pedido;
import com.example.pedido_service.model.PedidoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;

class PedidoServiceTest {

    private PedidoClient client;
    private PedidoService service;

    @BeforeEach
    void setup() {
        client = Mockito.mock(PedidoClient.class);
        service = new PedidoService(client);
    }

    @Test
    void criarPedido_sucesso() {
        PedidoRequest req = new PedidoRequest("c1", "racao-gato-10kg", 2);

        when(client.clienteExiste("c1")).thenReturn(Mono.just(true));
        when(client.consultarEstoque("racao-gato-10kg")).thenReturn(Mono.just(10));
        when(client.reservarEstoque("racao-gato-10kg", 2)).thenReturn(Mono.just(true));

        when(client.enviarPedidoParaCliente(any(), any())).thenReturn(Mono.empty());

        StepVerifier.create(service.criarPedido(req))
                .expectNextMatches(p ->
                        p.getClienteId().equals("c1") &&
                                p.getProduto().equals("racao-gato-10kg") &&
                                p.getQuantidade() == 2 &&
                                p.getStatus().equals("CRIADO")
                )
                .verifyComplete();

        verify(client, times(1)).clienteExiste("c1");
        verify(client, times(1)).consultarEstoque("racao-gato-10kg");
        verify(client, times(1)).reservarEstoque("racao-gato-10kg", 2);
        verify(client, times(1)).enviarPedidoParaCliente(any(), any());
    }

    @Test
    void criarPedido_clienteNaoExiste_deveFalhar() {
        PedidoRequest req = new PedidoRequest("c1", "racao-gato-10kg", 2);
        when(client.clienteExiste("c1")).thenReturn(Mono.just(false));

        StepVerifier.create(service.criarPedido(req))
                .expectErrorMessage("Cliente não existe")
                .verify();

        verify(client, times(1)).clienteExiste("c1");
        verify(client, never()).consultarEstoque(any());
        verify(client, never()).reservarEstoque(any(), anyInt());
        verify(client, never()).enviarPedidoParaCliente(any(), any());
    }

    @Test
    void criarPedido_estoqueInsuficiente_deveFalhar() {
        PedidoRequest req = new PedidoRequest("c1", "racao-gato-10kg", 100);
        when(client.clienteExiste("c1")).thenReturn(Mono.just(true));
        when(client.consultarEstoque("racao-gato-10kg")).thenReturn(Mono.just(50));

        StepVerifier.create(service.criarPedido(req))
                .expectErrorMessage("Estoque insuficiente")
                .verify();

        verify(client, times(1)).clienteExiste("c1");
        verify(client, times(1)).consultarEstoque("racao-gato-10kg");
        verify(client, never()).reservarEstoque(any(), anyInt());
        verify(client, never()).enviarPedidoParaCliente(any(), any());
    }

    @Test
    void criarPedido_falhaAoReservarEstoque() {
        PedidoRequest req = new PedidoRequest("c1", "racao-gato-10kg", 5);
        when(client.clienteExiste("c1")).thenReturn(Mono.just(true));
        when(client.consultarEstoque("racao-gato-10kg")).thenReturn(Mono.just(10));
        when(client.reservarEstoque("racao-gato-10kg", 5)).thenReturn(Mono.just(false));

        StepVerifier.create(service.criarPedido(req))
                .expectErrorMessage("Falha ao reservar estoque")
                .verify();

        verify(client, times(1)).clienteExiste("c1");
        verify(client, times(1)).consultarEstoque("racao-gato-10kg");
        verify(client, times(1)).reservarEstoque("racao-gato-10kg", 5);
        verify(client, never()).enviarPedidoParaCliente(any(), any());
    }
}
