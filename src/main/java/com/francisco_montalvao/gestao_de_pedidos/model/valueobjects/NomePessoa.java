package com.francisco_montalvao.gestao_de_pedidos.model.valueobjects;


import jakarta.persistence.Embeddable;

@Embeddable
public record NomePessoa(String nome) {

    public NomePessoa {

        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome é obrigatório.");
        }
        if (!nome.matches("^[A-Za-zÀ-ÿ ]{2,}$")) {
            throw new IllegalArgumentException("O nome deve ter no mínimo 2 caracteres.");
        }
        
    }
}
