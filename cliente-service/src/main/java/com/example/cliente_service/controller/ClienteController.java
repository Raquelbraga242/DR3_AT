package com.example.cliente_service.controller;

import com.example.cliente_service.model.Cliente;
import com.example.cliente_service.model.PedidoResumo;
import com.example.cliente_service.service.ClienteService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteService service;
    public ClienteController(ClienteService service){ this.service = service; }

    @GetMapping
    public Flux<Cliente> listar(){ return service.listar(); }

    @GetMapping("/{id}")
    public Mono<Cliente> buscar(@PathVariable String id){ return service.buscar(id); }

    @PostMapping
    public Mono<Cliente> criar(@RequestBody Cliente cliente){ return service.salvar(cliente); }

    @PostMapping("/{id}/adicionar-pedido")
    public Mono<Void> adicionarPedido(@PathVariable String id, @RequestBody PedidoResumo resumo){
        return service.adicionarPedidoAoCliente(id, resumo);
    }
}
