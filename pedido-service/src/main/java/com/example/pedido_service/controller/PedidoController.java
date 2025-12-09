package com.example.pedido_service.controller;

import com.example.pedido_service.model.Pedido;
import com.example.pedido_service.model.PedidoRequest;
import com.example.pedido_service.service.PedidoService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {
    private final PedidoService service;
    public PedidoController(PedidoService service){ this.service = service; }

    @PostMapping
    public Mono<Pedido> criar(@RequestBody PedidoRequest req){
        return service.criarPedido(req);
    }
}
