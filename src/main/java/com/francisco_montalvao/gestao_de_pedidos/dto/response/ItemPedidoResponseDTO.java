package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.math.BigDecimal;

public record ItemPedidoResponseDTO(
        Long produtoId,
        String nome,
        Integer quantidade,
        BigDecimal precoUnitario,
        BigDecimal subTotal
) {
}
