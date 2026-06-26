package com.francisco_montalvao.gestao_de_pedidos.dto.response;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record ClienteTicketMedio(
        Long posicao,
        Long id,
        String nome,
        Integer totalPedidos,
        BigDecimal receitaTotal,
        BigDecimal ticketMedio
) {
    public ClienteTicketMedio {
        if (receitaTotal != null) {
            receitaTotal = receitaTotal.setScale(2, RoundingMode.HALF_UP);
        }
        if (ticketMedio != null) {
            ticketMedio = ticketMedio.setScale(2, RoundingMode.HALF_UP);
        }
    }
}
