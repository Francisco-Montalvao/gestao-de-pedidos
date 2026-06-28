package com.francisco_montalvao.gestao_de_pedidos.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record NomeProduto(
        String nome) {

    public NomeProduto {
        if (nome == null || nome.isBlank() || nome.trim().length() < 3) {
            throw new IllegalArgumentException("Nome do produto deve ter ao menos 3 caracteres.");
        }
    }

}