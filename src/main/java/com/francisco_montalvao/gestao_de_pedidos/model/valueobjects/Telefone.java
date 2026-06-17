package com.francisco_montalvao.gestao_de_pedidos.model.valueobjects;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
public record Telefone(
        @Column(name = "telefone",  unique = true, nullable = false)
        String telefone
) {
    public Telefone(String telefone) {
        if (telefone == null || !telefone.matches("\\d{10,11}")) {

            throw new IllegalArgumentException(
                    "Telefone " + telefone +
                            "deve conter 10 ou 11 dígitos."
            );
        }
        this.telefone = telefone;
    }
}
