package com.francisco_montalvao.gestao_de_pedidos.model.valueobjects;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED, force = true)


@Getter
@Embeddable
public class Email {

    @Column(name = "email", unique = true, nullable = false)
    private final String email;

    public Email(String email) {
        if (email == null ||email.trim().length() < 5 || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("O e-mail "+ email +" não é válido.");
        }
        this.email = email;
    }

}
