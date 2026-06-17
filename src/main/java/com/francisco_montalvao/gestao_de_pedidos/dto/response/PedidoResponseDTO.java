package com.francisco_montalvao.gestao_de_pedidos.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PedidoResponseDTO(
        Long id,
        ClienteSimplificadoDTO cliente,
        String status,
        BigDecimal valorTotal,
        List<ItemPedidoResponseDTO> itens,
        LocalDateTime criadoEm,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        LocalDateTime atualizadoEm
) {
}
