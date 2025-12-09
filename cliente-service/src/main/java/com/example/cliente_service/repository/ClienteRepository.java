package com.example.cliente_service.repository;


import com.example.cliente_service.model.Cliente;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface ClienteRepository extends ReactiveCrudRepository<Cliente, String> {
    Mono<Cliente> findById(String id);
}
