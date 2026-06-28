package com.francisco_montalvao.gestao_de_pedidos.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record Telefone(String telefone) {
    public Telefone(String telefone) {
        String apenasDigitos = telefone.replaceAll("\\D", "");

        if (!apenasDigitos.matches("\\d{10,11}")) {
            throw new IllegalArgumentException(
                    "O telefone informado é inválido."
            );
        }
        this.telefone = apenasDigitos;
    }
}
