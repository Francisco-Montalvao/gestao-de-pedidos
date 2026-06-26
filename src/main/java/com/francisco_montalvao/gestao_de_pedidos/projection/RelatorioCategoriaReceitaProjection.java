package com.francisco_montalvao.gestao_de_pedidos.projection;

import java.math.BigDecimal;

public interface RelatorioCategoriaReceitaProjection {
    Long getPosicao();
    Long getId();
    String getNome();
    Integer getQuantidadeVendida();
    BigDecimal getReceita();
}
