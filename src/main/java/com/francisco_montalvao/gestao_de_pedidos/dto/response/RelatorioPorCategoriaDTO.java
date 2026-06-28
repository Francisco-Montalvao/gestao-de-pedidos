package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioPorCategoriaDTO(
        PeriodoResponseDTO periodo,
        List<RelatorioCategoriaReceita> categorias,
        BigDecimal receitaTotal
){
}
