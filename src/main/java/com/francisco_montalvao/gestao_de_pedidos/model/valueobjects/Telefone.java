package com.francisco_montalvao.gestao_de_pedidos.model.valueobjects;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)
@Getter

@Embeddable
public class Telefone {

    @Column(name = "telefone",  unique = true, nullable = false)
    private final String telefone;

    public Telefone(String telefone) {
        if (telefone == null || !telefone.matches("\\d{10,11}")) {

            throw new IllegalArgumentException(
                    "Telefone deve conter 10 ou 11 dígitos."
            );
        }
        this.telefone = telefone;
    }
}
