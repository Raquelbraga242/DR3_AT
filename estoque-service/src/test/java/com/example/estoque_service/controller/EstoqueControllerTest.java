package com.example.estoque_service.controller;

import static org.junit.jupiter.api.Assertions.*;


import com.example.estoque_service.model.Produto;
import com.example.estoque_service.service.EstoqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class EstoqueControllerTest {

    private EstoqueService service;
    private WebTestClient webTestClient;

    @BeforeEach
    void setup() {
        service = Mockito.mock(EstoqueService.class);
        EstoqueController controller = new EstoqueController(service);
        webTestClient = WebTestClient.bindToController(controller).build();
    }

    @Test
    void listarTodos_deveRetornarProdutos() {
        Produto p = new Produto("p1", "Ração", 50);
        when(service.listarTodos()).thenReturn(Flux.just(p));

        webTestClient.get().uri("/estoque")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$[0].produtoId").isEqualTo("p1")
                .jsonPath("$[0].nome").isEqualTo("Ração")
                .jsonPath("$[0].quantidade").isEqualTo(50);
    }

    @Test
    void buscar_deveRetornarProdutoPorId() {
        Produto p = new Produto("p1", "Ração", 50);
        when(service.buscar("p1")).thenReturn(Mono.just(p));

        webTestClient.get().uri("/estoque/p1")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.produtoId").isEqualTo("p1")
                .jsonPath("$.nome").isEqualTo("Ração")
                .jsonPath("$.quantidade").isEqualTo(50);
    }

    @Test
    void adicionar_deveSalvarProduto() {
        Produto p = new Produto("p1", "Ração", 50);
        when(service.adicionar(any())).thenReturn(Mono.just(p));

        webTestClient.post().uri("/estoque")
                .bodyValue(p)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.produtoId").isEqualTo("p1")
                .jsonPath("$.nome").isEqualTo("Ração")
                .jsonPath("$.quantidade").isEqualTo(50);
    }

    @Test
    void reservar_deveAtualizarQuantidade() {
        Produto p = new Produto("p1", "Ração", 45);
        when(service.reservar(anyString(), anyInt())).thenReturn(Mono.just(p));

        webTestClient.patch().uri(uriBuilder -> uriBuilder
                        .path("/estoque/p1/reservar")
                        .queryParam("quantidade", 5)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.produtoId").isEqualTo("p1")
                .jsonPath("$.nome").isEqualTo("Ração")
                .jsonPath("$.quantidade").isEqualTo(45);
    }
}
