package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import javax.swing.plaf.basic.BasicIconFactory;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public record RelatorioPorCategoriaDTO(
        PeriodoResponseDTO periodo,
        List<RelatorioCategoriaReceita> categorias,
        BigDecimal receitaTotal
){
}
