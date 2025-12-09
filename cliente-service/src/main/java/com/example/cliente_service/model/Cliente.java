package com.example.cliente_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.ArrayList;
import java.util.List;

@Document("clientes")
public class Cliente {

    @Id
    private String id;
    private String nome;
    private String email;
    private List<PedidoResumo> historicoPedidos = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String id, String nome, String email, List<PedidoResumo> historicoPedidos) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.historicoPedidos = historicoPedidos != null ? historicoPedidos : new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<PedidoResumo> getHistoricoPedidos() {
        return historicoPedidos;
    }

    public void setHistoricoPedidos(List<PedidoResumo> historicoPedidos) {
        this.historicoPedidos = historicoPedidos;
    }
}
