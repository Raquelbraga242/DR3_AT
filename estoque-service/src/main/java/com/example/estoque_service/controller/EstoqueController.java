package com.example.estoque_service.controller;

import com.example.estoque_service.model.Produto;
import com.example.estoque_service.service.EstoqueService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/estoque")
public class EstoqueController {

    private final EstoqueService service;

    public EstoqueController(EstoqueService service) {
        this.service = service;
    }

    @GetMapping
    public Flux<Produto> listarTodos() {
        return service.listarTodos();
    }

    @GetMapping("/{produtoId}")
    public Mono<Produto> buscar(@PathVariable String produtoId) {
        return service.buscar(produtoId);
    }

    @PostMapping
    public Mono<Produto> adicionar(@RequestBody Produto produto) {
        return service.adicionar(produto);
    }

    @PatchMapping("/{produtoId}/reservar")
    public Mono<Produto> reservar(@PathVariable String produtoId, @RequestParam int quantidade) {
        return service.reservar(produtoId, quantidade);
    }
}
