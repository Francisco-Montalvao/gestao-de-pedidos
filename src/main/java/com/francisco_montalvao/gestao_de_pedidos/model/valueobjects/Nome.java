package com.francisco_montalvao.gestao_de_pedidos.model.valueobjects;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)

@Embeddable
public class Nome {

    @Column(name = "nome", nullable = false)
    private final String nome;

    public Nome(String nome){
        if (nome == null || nome.trim().length() < 3 || !nome.matches("^[A-Za-zÀ-ÿ ]+$")){

            throw new IllegalArgumentException(
                    "Nome " + nome +
                            "deve conter apenas letras e ter no minimo 3 caracteres"
            );
        }
        this.nome = nome;
    }

}
