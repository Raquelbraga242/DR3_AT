package com.example.pedido_service.controller;

import static org.junit.jupiter.api.Assertions.*;

import com.example.pedido_service.model.Pedido;
import com.example.pedido_service.model.PedidoRequest;
import com.example.pedido_service.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PedidoControllerTest {

    private PedidoService service;
    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        service = Mockito.mock(PedidoService.class);
        PedidoController controller = new PedidoController(service);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void criar_deveCriarPedido() {
        PedidoRequest req = new PedidoRequest("cliente1", "racao-gato", 2);
        Pedido p = new Pedido(
                "pedido1",
                "cliente1",
                "racao-gato",
                2,
                "CRIADO",
                Instant.now()
        );

        when(service.criarPedido(any(PedidoRequest.class))).thenReturn(Mono.just(p));

        webTestClient.post().uri("/pedidos")
                .bodyValue(req)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("pedido1")
                .jsonPath("$.clienteId").isEqualTo("cliente1")
                .jsonPath("$.produto").isEqualTo("racao-gato")
                .jsonPath("$.quantidade").isEqualTo(2)
                .jsonPath("$.status").isEqualTo("CRIADO")
                .jsonPath("$.timestamp").exists();
    }
}
