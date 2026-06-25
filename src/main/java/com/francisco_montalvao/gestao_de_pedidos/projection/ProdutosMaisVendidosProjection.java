package com.francisco_montalvao.gestao_de_pedidos.projection;

import java.math.BigDecimal;

public interface ProdutosMaisVendidosProjection {
    Long getPosicao();
    Long getId();
    String getNome();
    String getCategoria();
    Long getQuantidadeVendida();
    BigDecimal getReceita();

}
