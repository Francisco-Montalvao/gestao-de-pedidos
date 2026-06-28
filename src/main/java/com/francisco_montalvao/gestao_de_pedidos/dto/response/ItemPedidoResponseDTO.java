package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long produtoId,
        String nome,
        Integer quantidade,
        BigDecimal precoUnitario,
        @JsonProperty("subtotal")
        BigDecimal subtotal
) {
}
