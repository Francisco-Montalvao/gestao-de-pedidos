package com.francisco_montalvao.gestao_de_pedidos.model.valueobjects;


public record Email(String email) {
    public Email {
        // Validação (o record já gera o construtor canônico internamente)
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("E-mail inválido: " + email);
        }
    }
}