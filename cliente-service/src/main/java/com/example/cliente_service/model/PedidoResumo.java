package com.example.cliente_service.model;

public class PedidoResumo {
    private String pedidoId;
    private String produto;
    private String produtoId;
    private int quantidade;

    public PedidoResumo() {
    }

    public PedidoResumo(String pedidoId, String produto, String produtoId, int quantidade) {
        this.pedidoId = pedidoId;
        this.produto = produto;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
    }

    public String getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(String pedidoId) {
        this.pedidoId = pedidoId;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getProdutoId() {
        return produtoId;
    }

    public void setProdutoId(String produtoId) {
        this.produtoId = produtoId;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
