package com.example.cliente_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import com.example.cliente_service.model.Cliente;
import com.example.cliente_service.model.PedidoResumo;
import com.example.cliente_service.repository.ClienteRepository;
import com.example.cliente_service.service.ClienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ClienteServiceApplicationTests {

    private ClienteRepository repo;
    private ClienteService service;

    @BeforeEach
    void setup() {
        repo = Mockito.mock(ClienteRepository.class);
        service = new ClienteService(repo);
    }

    @Test
    void listar_deveRetornarTodosClientes() {
        Cliente c = new Cliente("1", "Ana", "ana@email.com", Collections.emptyList());
        when(repo.findAll()).thenReturn(Flux.just(c));

        StepVerifier.create(service.listar())
                .expectNext(c)
                .verifyComplete();
    }

    @Test
    void buscar_deveRetornarClientePorId() {
        Cliente c = new Cliente("1", "Ana", "ana@email.com", Collections.emptyList());
        when(repo.findById("1")).thenReturn(Mono.just(c));

        StepVerifier.create(service.buscar("1"))
                .expectNext(c)
                .verifyComplete();
    }

    @Test
    void salvar_devePersistirCliente() {
        Cliente c = new Cliente(null, "Ana", "ana@email.com", Collections.emptyList());
        when(repo.save(c)).thenReturn(Mono.just(c));

        StepVerifier.create(service.salvar(c))
                .expectNext(c)
                .verifyComplete();
    }

    @Test
    void adicionarPedidoAoCliente_deveAdicionarPedido() {
        Cliente c = new Cliente("1", "Ana", "ana@email.com", new ArrayList<>());
        PedidoResumo p = new PedidoResumo("p1", "Ração", "prod1", 2);

        when(repo.findById("1")).thenReturn(Mono.just(c));
        when(repo.save(any())).thenReturn(Mono.just(c));

        StepVerifier.create(service.adicionarPedidoAoCliente("1", p))
                .verifyComplete();

        verify(repo).save(c);
        assert c.getHistoricoPedidos().contains(p);
    }

}
