package com.example.cliente_service.service;
import com.example.cliente_service.model.Cliente;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClienteService {
    private final ConcurrentHashMap<Long, Cliente> map = new ConcurrentHashMap<>();
    private final AtomicLong seq = new AtomicLong(1);

    public Flux<Cliente> listar(){ return Flux.fromIterable(map.values()); }
    public Mono<Cliente> buscar(Long id){ return Mono.justOrEmpty(map.get(id)); }
    public Mono<Cliente> salvar(Cliente c){
        long id = seq.getAndIncrement(); c.setId(id); map.put(id,c); return Mono.just(c);
    }
    public Mono<Void> deletar(Long id){
        map.remove(id); return Mono.empty();
    }
}
