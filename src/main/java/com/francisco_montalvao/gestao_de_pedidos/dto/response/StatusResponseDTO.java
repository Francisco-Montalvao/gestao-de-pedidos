package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;

public record StatusResponseDTO(
        @JsonProperty("id")
        Long pedidoId,
        String status,
        @JsonProperty("atualizado_em")
        LocalDateTime atualizadoEm
) {
}
