package com.example.pedido_service.model;

import java.time.Instant;

public class Pedido {
    private String id;
    private String clienteId;
    private String produto;
    private int quantidade;
    private String status;
    private Instant timestamp;

    public Pedido() {
    }

    public Pedido(String id, String clienteId, String produto, int quantidade, String status, Instant timestamp) {
        this.id = id;
        this.clienteId = clienteId;
        this.produto = produto;
        this.quantidade = quantidade;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
