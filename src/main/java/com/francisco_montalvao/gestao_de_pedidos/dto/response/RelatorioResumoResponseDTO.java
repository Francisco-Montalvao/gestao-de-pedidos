package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import com.francisco_montalvao.gestao_de_pedidos.projection.ResumoStatusProjection;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioResumoResponseDTO(
        PeriodoResponseDTO periodo,
        List<ResumoStatusItemDTO> resumo,
        Integer totalPedidos,
        BigDecimal receitaTotal,
        String taxaCancelamento
) {
}
