package com.francisco_montalvao.gestao_de_pedidos.dto.request;

import jakarta.validation.constraints.NotBlank;

public record StatusRequestDTO(
        @NotBlank(message = "Status nao pode estar vazio")
        String status
) {
}
