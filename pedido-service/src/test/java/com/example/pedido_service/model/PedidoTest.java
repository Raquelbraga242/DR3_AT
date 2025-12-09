package com.example.pedido_service.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class PedidoTest {

    @Test
    void gettersESetters() {
        Instant now = Instant.now();
        Pedido pedido = new Pedido();

        pedido.setId("p1");
        pedido.setClienteId("c1");
        pedido.setProduto("racao-gato");
        pedido.setQuantidade(3);
        pedido.setStatus("CRIADO");
        pedido.setTimestamp(now);

        assertEquals("p1", pedido.getId());
        assertEquals("c1", pedido.getClienteId());
        assertEquals("racao-gato", pedido.getProduto());
        assertEquals(3, pedido.getQuantidade());
        assertEquals("CRIADO", pedido.getStatus());
        assertEquals(now, pedido.getTimestamp());
    }

    @Test
    void construtorComParametros() {
        Instant now = Instant.now();
        Pedido pedido = new Pedido("p2", "c2", "racao-cao", 5, "CRIADO", now);

        assertEquals("p2", pedido.getId());
        assertEquals("c2", pedido.getClienteId());
        assertEquals("racao-cao", pedido.getProduto());
        assertEquals(5, pedido.getQuantidade());
        assertEquals("CRIADO", pedido.getStatus());
        assertEquals(now, pedido.getTimestamp());
    }
}
