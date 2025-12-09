package com.example.pedido_service.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoRequestTest {

    @Test
    void gettersESetters() {
        PedidoRequest req = new PedidoRequest();

        req.setClienteId("c1");
        req.setProduto("racao-gato");
        req.setQuantidade(2);

        assertEquals("c1", req.getClienteId());
        assertEquals("racao-gato", req.getProduto());
        assertEquals(2, req.getQuantidade());
    }

    @Test
    void construtorComParametros() {
        PedidoRequest req = new PedidoRequest("c2", "racao-cao", 5);

        assertEquals("c2", req.getClienteId());
        assertEquals("racao-cao", req.getProduto());
        assertEquals(5, req.getQuantidade());
    }
}
