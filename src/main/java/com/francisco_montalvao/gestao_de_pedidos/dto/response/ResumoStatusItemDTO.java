package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.math.BigDecimal;

public record ResumoStatusItemDTO(
        String status,
        Integer quantidade,
        BigDecimal receita
) {
}
