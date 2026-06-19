package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.time.LocalDateTime;

public record StatusResponseDTO(
        Long pedidoId,
        String status,
        LocalDateTime atualizadoEm
) {
}
