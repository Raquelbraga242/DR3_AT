package com.example.estoque_service.service;

import com.example.estoque_service.model.Produto;
import com.example.estoque_service.repository.ProdutoRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProdutoService {

    private final ProdutoRepository repo;
    public ProdutoService(ProdutoRepository repo){ this.repo = repo; }

    public Flux<Produto> listar(){ return repo.findAll(); }
    public Mono<Produto> buscar(Long id){ return repo.findById(id); }
    public Mono<Produto> salvar(Produto p){ return repo.save(p); }
}
