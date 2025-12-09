package com.example.cliente_service.service;

import com.example.cliente_service.model.Cliente;
import com.example.cliente_service.model.PedidoResumo;
import com.example.cliente_service.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

@Service
public class ClienteService {
    private final ClienteRepository repo;
    public ClienteService(ClienteRepository repo){ this.repo = repo; }
    public Flux<Cliente> listar(){ return repo.findAll(); }
    public Mono<Cliente> buscar(String id){ return repo.findById(id); }
    public Mono<Cliente> salvar(Cliente c){ return repo.save(c); }
    public Mono<Void> adicionarPedidoAoCliente(String clienteId, PedidoResumo resumo){
        return repo.findById(clienteId)
                .flatMap(cliente -> {
                    cliente.getHistoricoPedidos().add(resumo);
                    return repo.save(cliente);
                }).then();
    }
}
