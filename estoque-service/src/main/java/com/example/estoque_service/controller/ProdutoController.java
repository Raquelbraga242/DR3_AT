package com.example.estoque_service.controller;

import com.example.estoque_service.model.Produto;
import com.example.estoque_service.service.ProdutoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    private final ProdutoService service;
    public ProdutoController(ProdutoService service){ this.service = service; }

    @GetMapping
    public Flux<Produto> listar(){ return service.listar(); }

    @GetMapping("/{id}")
    public Mono<Produto> buscar(@PathVariable Long id){ return service.buscar(id); }

    @PostMapping
    public Mono<Produto> salvar(@RequestBody Produto produto){ return service.salvar(produto); }

    @GetMapping("/{id}/disponibilidade")
    public Mono<Integer> disponibilidade(@PathVariable Long id){
        return service.buscar(id).map(p -> p.quantidade());
    }
    @PatchMapping("/{id}/baixar")
    public Mono<Produto> baixarEstoque(
            @PathVariable Long id,
            @RequestParam Integer quantidade
    ) {
        return service.buscar(id)
                .flatMap(produto -> {
                    if (produto.quantidade() < quantidade) {
                        return Mono.error(new RuntimeException("Estoque insuficiente"));
                    }
                    Produto atualizado =
                            new Produto(produto.id(), produto.nome(), produto.quantidade() - quantidade);
                    return service.salvar(atualizado);
                });
    }

    @PatchMapping("/{id}/adicionar")
    public Mono<Produto> adicionarEstoque(
            @PathVariable Long id,
            @RequestParam Integer quantidade
    ) {
        return service.buscar(id)
                .flatMap(produto -> {
                    Produto atualizado =
                            new Produto(produto.id(), produto.nome(), produto.quantidade() + quantidade);
                    return service.salvar(atualizado);
                });
    }

}
