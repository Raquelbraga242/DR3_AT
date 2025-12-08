package com.example.cliente_service.controller;

import com.example.cliente_service.client.EstoqueClient;
import com.example.cliente_service.model.Cliente;
import com.example.cliente_service.service.ClienteService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteService clienteService;
    private final EstoqueClient estoqueClient;

    public ClienteController(ClienteService clienteService, EstoqueClient estoqueClient) {
        this.clienteService = clienteService;
        this.estoqueClient = estoqueClient;
    }

    // =====================
    // Endpoints CRUD Cliente
    // =====================

    @GetMapping
    public Flux<Cliente> listarClientes() {
        return clienteService.listar();
    }

    @GetMapping("/{id}")
    public Mono<Cliente> buscarCliente(@PathVariable Long id) {
        return clienteService.buscar(id);
    }

    @PostMapping
    public Mono<Cliente> criarCliente(@RequestBody Cliente cliente) {
        return clienteService.salvar(cliente);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deletarCliente(@PathVariable Long id) {
        return clienteService.deletar(id);
    }

    // =====================
    // Endpoint de verificar produto no estoque
    // =====================
    @GetMapping("/{id}/verificar-produto/{idProduto}")
    public Mono<String> verificarProduto(
            @PathVariable Long id,
            @PathVariable Long idProduto
    ) {
        return estoqueClient.consultarDisponibilidade(idProduto)
                .map(qtd -> "Cliente " + id + " consultou o produto " + idProduto +
                        " — Quantidade disponível: " + qtd);
    }
}
