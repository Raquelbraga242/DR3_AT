package com.example.pedido_service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.pedido_service.client.PedidoClient;
import com.example.pedido_service.model.PedidoRequest;
import com.example.pedido_service.service.PedidoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class PedidoServiceApplicationTests {
    @Test
    void contextLoads() {

    }
}