package com.example.pedido_service.repository;

import com.example.pedido_service.model.Pedido;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PedidoRepository {

    private final Map<Long, Pedido> pedidos = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong();

    public Pedido save(Pedido pedido) {
        long id = idCounter.incrementAndGet();
        pedido.setId(id);
        pedidos.put(id, pedido);
        return pedido;
    }

    public Optional<Pedido> findById(Long id) {
        return Optional.ofNullable(pedidos.get(id));
    }

    public List<Pedido> findAll() {
        return new ArrayList<>(pedidos.values());
    }

    public void deleteById(Long id) {
        pedidos.remove(id);
    }
}
