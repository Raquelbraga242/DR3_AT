package com.example.cliente_service.controller;

import static org.junit.jupiter.api.Assertions.*;


import com.example.cliente_service.model.Cliente;
import com.example.cliente_service.model.PedidoResumo;
import com.example.cliente_service.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClienteControllerTest {

    private ClienteService service;
    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        service = Mockito.mock(ClienteService.class);
        ClienteController controller = new ClienteController(service);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void listar_deveRetornarClientes() {
        Cliente c = new Cliente("1", "Ana", "ana@email.com", new ArrayList<>());
        when(service.listar()).thenReturn(Flux.just(c));

        webTestClient.get().uri("/clientes")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].id").isEqualTo("1")
                .jsonPath("$[0].nome").isEqualTo("Ana")
                .jsonPath("$[0].email").isEqualTo("ana@email.com")
                .jsonPath("$[0].historicoPedidos").isArray()
                .jsonPath("$[0].historicoPedidos.length()").isEqualTo(0);
    }

    @Test
    void buscar_deveRetornarClientePorId() {
        Cliente c = new Cliente("1", "Ana", "ana@email.com", new ArrayList<>());
        when(service.buscar("1")).thenReturn(Mono.just(c));

        webTestClient.get().uri("/clientes/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo("1")
                .jsonPath("$.nome").isEqualTo("Ana")
                .jsonPath("$.email").isEqualTo("ana@email.com")
                .jsonPath("$.historicoPedidos").isArray()
                .jsonPath("$.historicoPedidos.length()").isEqualTo(0);
    }

    @Test
    void criar_deveSalvarCliente() {
        Cliente c = new Cliente(null, "Ana", "ana@email.com", new ArrayList<>());
        when(service.salvar(any())).thenReturn(Mono.just(c));

        webTestClient.post().uri("/clientes")
                .bodyValue(c)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.id").isEqualTo(null)
                .jsonPath("$.nome").isEqualTo("Ana")
                .jsonPath("$.email").isEqualTo("ana@email.com")
                .jsonPath("$.historicoPedidos").isArray()
                .jsonPath("$.historicoPedidos.length()").isEqualTo(0);
    }

    @Test
    void adicionarPedido_deveAdicionarPedido() {
        when(service.adicionarPedidoAoCliente(any(), any())).thenReturn(Mono.empty());

        PedidoResumo resumo = new PedidoResumo("p1", "Ração", "prod1", 2);
        webTestClient.post().uri("/clientes/1/adicionar-pedido")
                .bodyValue(resumo)
                .exchange()
                .expectStatus().isOk();
    }
}
