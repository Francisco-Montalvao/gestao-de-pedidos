package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.math.BigDecimal;

public record ProdutoResponseDTO(
        Long id,
        String nome,
        String descricao,
        BigDecimal preco,
        Integer estoque,
        Long categoriaId,
        Boolean ativo
) {
}
