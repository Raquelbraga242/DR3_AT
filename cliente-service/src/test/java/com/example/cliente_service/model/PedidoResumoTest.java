package com.example.cliente_service.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PedidoResumoTest {

    @Test
    void gettersSetters_eConstrutores() {
        PedidoResumo p = new PedidoResumo("p1", "Ração", "prod1", 3);

        assertEquals("p1", p.getPedidoId());
        assertEquals("Ração", p.getProduto());
        assertEquals("prod1", p.getProdutoId());
        assertEquals(3, p.getQuantidade());

        p.setPedidoId("p2");
        p.setProduto("Brinquedo");
        p.setProdutoId("prod2");
        p.setQuantidade(5);

        assertEquals("p2", p.getPedidoId());
        assertEquals("Brinquedo", p.getProduto());
        assertEquals("prod2", p.getProdutoId());
        assertEquals(5, p.getQuantidade());
    }
}
