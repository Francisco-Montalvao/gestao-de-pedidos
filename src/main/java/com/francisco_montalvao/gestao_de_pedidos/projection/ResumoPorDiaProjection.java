package com.francisco_montalvao.gestao_de_pedidos.projection;

import java.math.BigDecimal;
import java.time.LocalDate;


public interface ResumoPorDiaProjection {
    LocalDate getData();
    Long getQuantidadeDePedidos();
    BigDecimal getReceita();
}
