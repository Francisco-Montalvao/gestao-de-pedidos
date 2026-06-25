package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

public record DiasResponseDTO(
        LocalDate data,
        Integer quantidadePedidos,
        BigDecimal receita
) {
}
