package com.francisco_montalvao.gestao_de_pedidos.model.valueobjects;


import jakarta.persistence.Embeddable;

@Embeddable
public record NomeComercial(
        String nome
) {

    public NomeComercial(String nome){

        if (nome == null || nome.trim().length() < 2){

            throw new IllegalArgumentException(
                    "O nome comercial '" + nome + "' não é válido. Ele deve ter no mínimo 2 caracteres."
            );
        }
        this.nome = nome;
    }

}
