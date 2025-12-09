package com.example.cliente_service.model;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClienteTest {

    @Test
    void gettersSetters_eConstrutores() {
        List<PedidoResumo> pedidos = new ArrayList<>();
        Cliente c = new Cliente("1", "Ana", "ana@email.com", pedidos);

        assertEquals("1", c.getId());
        assertEquals("Ana", c.getNome());
        assertEquals("ana@email.com", c.getEmail());
        assertEquals(pedidos, c.getHistoricoPedidos());

        c.setId("2");
        c.setNome("Bruna");
        c.setEmail("bruna@email.com");
        List<PedidoResumo> novosPedidos = new ArrayList<>();
        c.setHistoricoPedidos(novosPedidos);

        assertEquals("2", c.getId());
        assertEquals("Bruna", c.getNome());
        assertEquals("bruna@email.com", c.getEmail());
        assertEquals(novosPedidos, c.getHistoricoPedidos());
    }
}
