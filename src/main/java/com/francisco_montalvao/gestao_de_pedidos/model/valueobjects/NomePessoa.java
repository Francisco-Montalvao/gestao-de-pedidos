package com.francisco_montalvao.gestao_de_pedidos.model.valueobjects;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record NomePessoa(
        @Column(name = "nome", nullable = false)
        String nome
) {

    public NomePessoa(String nome){
        if (nome == null || nome.trim().length() < 3 || !nome.matches("^[A-Za-zÀ-ÿ ]+$")){

            throw new IllegalArgumentException(
                    "NomePessoa " + nome +
                            " deve conter apenas letras e ter no minimo 3 caracteres"
            );
        }
        this.nome = nome;
    }

}
