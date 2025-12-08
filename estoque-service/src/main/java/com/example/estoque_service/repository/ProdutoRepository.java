package com.example.estoque_service.repository;

import com.example.estoque_service.model.Produto;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends ReactiveCrudRepository<Produto, Long> {}
