package com.francisco_montalvao.gestao_de_pedidos.projection;

import java.math.BigDecimal;

public interface RelatorioVendasTicketMedioClienteProjection {
    Long getPosicao();
    Long getId();
    String getNome();
    Integer getTotalPedidos();
    BigDecimal getReceitaTotal();
    BigDecimal getTicketMedio();

}
