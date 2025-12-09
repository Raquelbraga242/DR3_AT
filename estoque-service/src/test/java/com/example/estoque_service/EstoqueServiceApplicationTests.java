package com.example.estoque_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.estoque_service.model.Produto;
import com.example.estoque_service.service.EstoqueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class EstoqueServiceApplicationTests {

    private EstoqueService service;

    @BeforeEach
    void setup() {
        service = new EstoqueService();
        service.init(); // inicializa com produtos de exemplo
    }

    @Test
    void listarTodos_deveRetornarProdutos() {
        StepVerifier.create(service.listarTodos())
                .expectNextCount(2)
                .verifyComplete();
    }

    @Test
    void buscar_deveRetornarProduto() {
        StepVerifier.create(service.buscar("racao-gato-10kg"))
                .expectNextMatches(p -> p.nome().equals("Ração 10kg"))
                .verifyComplete();
    }

    @Test
    void adicionar_deveAdicionarProduto() {
        Produto novo = new Produto("prod-1", "Produto 1", 10);
        StepVerifier.create(service.adicionar(novo))
                .expectNext(novo)
                .verifyComplete();

        StepVerifier.create(service.buscar("prod-1"))
                .expectNext(novo)
                .verifyComplete();
    }

    @Test
    void reservar_deveDiminuirQuantidade() {
        StepVerifier.create(service.reservar("racao-gato-10kg", 5))
                .expectNextMatches(p -> p.quantidade() == 45)
                .verifyComplete();
    }

    @Test
    void reservar_quandoQuantidadeInsuficiente_deveRetornarVazio() {
        StepVerifier.create(service.reservar("racao-gato-10kg", 1000))
                .verifyComplete();
    }
}
