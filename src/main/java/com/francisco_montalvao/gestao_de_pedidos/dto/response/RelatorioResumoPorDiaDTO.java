package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record RelatorioResumoPorDiaDTO(
        PeriodoResponseDTO periodo,
        List<DiasResponseDTO> dias,
        Integer totalPedidos,
        BigDecimal receitaTotal
) {
}
