package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ClienteRequestDTO(

        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 2, message = "O nome deve ter no mínimo 2 caracteres.")
        String nome,

        @NotBlank(message = "O e-mail é obrigatório.")
        @jakarta.validation.constraints.Email(message = "O e-mail informado é inválido.")
        String email,

        @NotBlank(message = "O telefone é obrigatório.")
        @Pattern(regexp = "\\(\\d{2}\\) \\d{5}-\\d{4}", message = "O telefone informado é inválido.")
        String telefone
) {

}
