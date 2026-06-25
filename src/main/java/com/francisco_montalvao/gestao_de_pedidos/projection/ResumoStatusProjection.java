package com.francisco_montalvao.gestao_de_pedidos.projection;

import java.math.BigDecimal;

public interface ResumoStatusProjection {

    String getStatus();

    Integer getQuantidade();

    BigDecimal getReceita();

}
