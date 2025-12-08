package com.example.pedido_service.controller;

import com.example.pedido_service.model.Pedido;
import com.example.pedido_service.service.PedidoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoService service;

    public PedidoController(PedidoService service) {
        this.service = service;
    }

    @PostMapping
    public Mono<Pedido> criar(@RequestBody Pedido pedido) {
        return service.criarPedido(pedido);
    }

    @GetMapping
    public Flux<Pedido> listar() {
        return service.listarPedidos();
    }

    @GetMapping("/{id}")
    public Mono<Pedido> buscar(@PathVariable Long id) {
        return service.buscarPedido(id);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletar(@PathVariable Long id) {
        return service.deletarPedido(id);
    }
}
