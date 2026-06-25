package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.math.BigDecimal;

public record RelatorioCategoriaReceita(
        Long posicao,
        Long id,
        String nome,
        Integer quantidadeVendida,
        BigDecimal receita
) {
}
