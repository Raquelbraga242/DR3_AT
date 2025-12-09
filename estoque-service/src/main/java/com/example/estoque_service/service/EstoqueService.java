package com.example.estoque_service.service;

import com.example.estoque_service.model.Produto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class EstoqueService {

    private final Map<String, Produto> store = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        store.put("racao-gato-10kg", new Produto("racao-gato-10kg", "Ração 10kg", 50));
        store.put("racao-cao-5kg", new Produto("racao-cao-5kg", "Ração 5kg", 30));
    }

    public Flux<Produto> listarTodos() {
        return Flux.fromIterable(store.values());
    }

    public Mono<Produto> buscar(String produtoId) {
        return Mono.justOrEmpty(store.get(produtoId));
    }

    public Mono<Produto> adicionar(Produto produto) {
        store.put(produto.produtoId(), produto);
        return Mono.just(produto);
    }

    public Mono<Produto> reservar(String produtoId, int qtd) {
        return Mono.justOrEmpty(store.get(produtoId))
                .flatMap(p -> {
                    if (p.quantidade() < qtd) return Mono.empty();
                    Produto atualizado = new Produto(p.produtoId(), p.nome(), p.quantidade() - qtd);
                    store.put(produtoId, atualizado);
                    return Mono.just(atualizado);
                });
    }
}
