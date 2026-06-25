package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.math.BigDecimal;

public record ProdutosResponseDTO(
        Integer posicao,
        Long id,
        String nome,
        String categoria,
        Integer quantidadeVendida,
        BigDecimal receita
        ) {
}
