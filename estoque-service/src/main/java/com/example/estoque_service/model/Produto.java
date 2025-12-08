package com.example.estoque_service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("produto")
public record Produto(@Id Long id, String nome, Integer quantidade) {
    public static Produto of(String nome, Integer quantidade){ return new Produto(null, nome, quantidade); }
}
