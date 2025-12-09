package com.example.pedido_service.client;

import static org.junit.jupiter.api.Assertions.*;

import com.example.pedido_service.model.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PedidoClientTest {

    private WebClient mockClienteClient;
    private WebClient mockEstoqueClient;
    private PedidoClient pedidoClient;

    @BeforeEach
    void setup() {
        pedidoClient = new PedidoClient("localhost", "8082", "localhost", "8083");

        mockClienteClient = Mockito.mock(WebClient.class, RETURNS_DEEP_STUBS);
        mockEstoqueClient = Mockito.mock(WebClient.class, RETURNS_DEEP_STUBS);

        TestUtils.setField(pedidoClient, "clienteClient", mockClienteClient);
        TestUtils.setField(pedidoClient, "estoqueClient", mockEstoqueClient);
    }

    @Test
    void clienteExiste_deveRetornarTrue() {
        when(mockClienteClient.get().uri(anyString(), anyString()).retrieve().bodyToMono(Object.class))
                .thenReturn(Mono.just(new Object()));

        StepVerifier.create(pedidoClient.clienteExiste("c1"))
                .expectNext(true)
                .verifyComplete();
    }
    @Test
    void reservarEstoque_deveRetornarTrue() {
        when(mockEstoqueClient.patch().uri(any(Function.class)).retrieve().bodyToMono(Object.class))
                .thenReturn(Mono.just(new Object()));

        StepVerifier.create(pedidoClient.reservarEstoque("p1", 2))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void clienteExiste_quandoErro_deveRetornarFalse() {
        when(mockClienteClient.get().uri(anyString(), anyString()).retrieve().bodyToMono(Object.class))
                .thenReturn(Mono.error(new RuntimeException()));

        StepVerifier.create(pedidoClient.clienteExiste("c1"))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void consultarEstoque_deveRetornarQuantidade() {
        PedidoClient.ProdutoResponse response = new PedidoClient.ProdutoResponse("p1", "produto", 5);
        when(mockEstoqueClient.get().uri(anyString(), anyString()).retrieve().bodyToMono(PedidoClient.ProdutoResponse.class))
                .thenReturn(Mono.just(response));

        StepVerifier.create(pedidoClient.consultarEstoque("p1"))
                .expectNext(5)
                .verifyComplete();
    }
    @Test
    void deveCriarPedidoResumoCorretamente() {
        PedidoClient.PedidoResumo resumo = new PedidoClient.PedidoResumo("p1", "produto", 5);

        assertEquals("p1", resumo.pedidoId);
        assertEquals("produto", resumo.produto);
        assertEquals(5, resumo.quantidade);
    }

    @Test
    void deveAlterarCampos() {
        PedidoClient.PedidoResumo resumo = new PedidoClient.PedidoResumo("p1", "produto", 5);

        resumo.pedidoId = "p2";
        resumo.produto = "novoProduto";
        resumo.quantidade = 10;

        assertEquals("p2", resumo.pedidoId);
        assertEquals("novoProduto", resumo.produto);
        assertEquals(10, resumo.quantidade);
    }

}
