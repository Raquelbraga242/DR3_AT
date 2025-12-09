package com.example.pedido_service.model;

public class PedidoRequest {
    private String clienteId;
    private String produto;
    private int quantidade;

    public PedidoRequest() {
    }

    public PedidoRequest(String clienteId, String produto, int quantidade) {
        this.clienteId = clienteId;
        this.produto = produto;
        this.quantidade = quantidade;
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
}
